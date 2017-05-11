package com.leap.mini.mgr.logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import okhttp3.Authenticator;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Credentials;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.Route;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by neil on 2017/4/15.
 */

public class LogStashDescription implements BaseDestination {
  private String appId;
  private String appSecret;
  private String serverUrl;
  private Context context;

  private static int THRESHOLD = 10;
  private static int MAX_THRESHOLD = 50;
  private static int MIN_THRESHOLD = 1;
  private File entriesFile;
  private File sendingFile;
  private PackageManager packageManager;
  private PackageInfo packageInfo;
  private Gson gson;
  private int points;
  private boolean initialSending = true;
  private boolean isSending = false;
  private OkHttpClient client;

  public LogStashDescription(Context context, String appId, String appSecret, String serverUrl) {
    this.appId = appId;
    this.appSecret = appSecret;
    this.serverUrl = serverUrl;
    this.context = context;

    entriesFile = new File(context.getFilesDir().getAbsolutePath(), "logger.json");
    sendingFile = new File(context.getFilesDir().getAbsolutePath(), "logger.sending.json");

    packageManager = context.getPackageManager();
    try {
      packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
    } catch (PackageManager.NameNotFoundException e) {
      packageInfo = new PackageInfo();
    }

    gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss SSS").create();
  }

  public void send(LogLevel level, String msg, String thread, String file, String function,
      int line) {
    LogModel logModel = createLogModel();
    logModel.setLevel(level);
    logModel.setMessage(msg);
    logModel.setThread(thread);
    logModel.setFileName(file);
    logModel.setFunction(function);
    logModel.setLine(line);
    logModel.setTimestamp(new Date());

    try {
      FileWriter writer = new FileWriter(entriesFile, true);
      try {
        writer.write(gson.toJson(logModel) + "\n");
        writer.flush();
      } finally {
        writer.close();
      }

      points += level.point;
      if (points >= THRESHOLD && points >= MIN_THRESHOLD || points > MAX_THRESHOLD) {
        sendNow();
      } else if (initialSending) {
        initialSending = false;
        List<LogModel> logModels = readFromFile(entriesFile);
        if (logModels.size() > 1) {
          sendNow();
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void sendNow() {
    if (sendingFile.exists()) {
      points = 0;
    } else {
      try {
        sendingFile.createNewFile();
        FileInputStream fis = new FileInputStream(entriesFile);
        FileOutputStream fos = new FileOutputStream(sendingFile);
        try {
          int n = 0;
          byte[] buffer = new byte[1024 * 4];
          while (-1 != (n = fis.read(buffer))) {
            fos.write(buffer, 0, n);
          }
        } finally {
          fis.close();
          fos.close();
        }

        boolean deleted = entriesFile.delete();
        Log.i("delete entriesFile", "" + deleted);
        if (deleted) {
          entriesFile.createNewFile();
        }
      } catch (IOException e) {
        return;
      }
    }

    if (!isSending) {
      isSending = true;
      List<LogModel> logModels = readFromFile(sendingFile);
      if (logModels.size() <= 0) {
        boolean deleted = sendingFile.delete();
        Log.i("delete sendingFile", "empty " + deleted);
        isSending = false;
        return;
      } else {
        try {
          Collections.reverse(logModels);

          // HttpURLConnection conn = (HttpURLConnection) new
          // URL(serverUrl).openConnection();
          // conn.setDoInput(true);
          // conn.setDoOutput(true);
          // conn.setRequestMethod("POST");
          // conn.setUseCaches(false);
          // conn.setRequestProperty("Content-Type", "application/json");
          // // conn.setRequestProperty("Authorization", "Basic
          // // bG9nc3Rhc2g6YW9ESjBKVmdrZk5QamFybg==");
          // DataOutputStream dos = new
          // DataOutputStream(conn.getOutputStream());
          // dos.writeBytes(gson.toJson(logModels));
          // dos.flush();
          // dos.close();
          //
          // if (conn.getResponseCode() == 200) {
          // boolean deleted = sendingFile.delete();
          // System.out.println("deleted:" + deleted);
          // isSending = false;
          // points = 0;
          // }

          OkHttpClient client = getClient();
          RequestBody body = RequestBody.create(MediaType.parse("application/json"),
              gson.toJson(logModels));
          Request request = new Request.Builder().url(serverUrl).post(body).build();
          client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
              isSending = false;
              Log.i("上传日志到服务器的Request请求失败", e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
              if (response.isSuccessful()) {
                boolean deleted = sendingFile.delete();
                Log.i("delete sendingFile", "" + deleted);
                isSending = false;
                points = 0;
              } else {
                Log.i("上传日志到服务器的Request请求失败", response.message());
              }
            }
          });

        } catch (Exception e) {
          e.printStackTrace();
          Log.i("上传日志错误", e.getMessage());
        }
      }

    }
  }

  private OkHttpClient getClient() {
    if (client == null) {
      HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
      logging.setLevel(HttpLoggingInterceptor.Level.BODY);

      client = new OkHttpClient.Builder().retryOnConnectionFailure(false)
          .authenticator(new Authenticator() {
            @Override
            public Request authenticate(Route route, Response response) throws IOException {
              String credential = Credentials.basic(appId, appSecret);
              if (credential.equals(response.request().header("Authorization"))) {
                return null;
              }
              return response.request().newBuilder().addHeader("Authorization", credential)
                  .header("Content-Type", "application/json").build();
            }
          }).addInterceptor(logging).build();
    }
    return client;
  }

  private List<LogModel> readFromFile(File file) {
    List<LogModel> result = new LinkedList<>();
    try {
      FileReader reader = new FileReader(file);
      BufferedReader bufferedReader = new BufferedReader(reader);

      try {
        String line = null;
        while ((line = bufferedReader.readLine()) != null) {
          result.add(gson.fromJson(line, LogModel.class));
        }
      } finally {
        bufferedReader.close();
        reader.close();
      }
    } catch (Exception e) {
      System.out.println("---read------" + e.getMessage());
      e.printStackTrace();
    }
    return result;
  }

  private LogModel createLogModel() {
    LogModel model = new LogModel();
    model.setAppBuild(packageInfo.versionCode);
    model.setAppVersion(packageInfo.versionName);
    model.setAppId(context.getPackageName());
    model.setDeviceModel(Build.MODEL);
    model.setDeviceName(Build.DEVICE);
    model.setOsVersion(Build.VERSION.RELEASE);
    model.setHostName(Build.HOST);
    return model;
  }
}
