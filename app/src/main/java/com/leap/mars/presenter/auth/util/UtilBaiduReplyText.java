package com.leap.mars.presenter.auth.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

import com.leap.mars.util.Base64Util;

import android.os.AsyncTask;

public class UtilBaiduReplyText {

  /**
   * �첽���ط�����Ϣ �ӿ�
   */
  public interface LoadCallBack {
    /**
     * ���ݼ�����ɺ�ִ��
     */
    void onDateLoaded(Object obj);
  }

  /**
   * �첽���ط�����Ϣ
   * 
   * @param content
   * @param loadCallBack
   */
  public void LoadTextReply(final String content, final BaiDuCallback.LoadCallBack loadCallBack) {

    AsyncTask<String, String, String> task = new AsyncTask<String, String, String>() {

      @Override
      protected String doInBackground(String... params) {
        String respText = null;
        try {
          respText = loadTextCallback(content);
        } catch (Exception e) {
          e.printStackTrace();
        }
        return respText;
      }

      @Override
      protected void onPostExecute(String result) {
        super.onPostExecute(result);
        loadCallBack.onDateLoaded(result);
      }

    };
    task.execute();
  }

  String token = "24.abbb9d77c68b74767a70ac9e62d7e4e2.2592000.1472980961.282335-8437596";
  String cuid = "3PwrU3Cu83cKz3CRyWjON2vRCGs4egwA";
  String serverURL = "http://vop.baidu.com/server_api";
  String testFileName = null;

  // put your own params here
  // private static final String apiKey = "";
  // private static final String secretKey = "";

  // String testFileName = FilePath; // �ٶ������ṩ����֧��
  /**
   * ���ݴ����content ��ȡ�ظ���Ϣ
   * 
   * @return
   */
  protected String loadTextCallback(String FilePath) throws Exception {
    testFileName = FilePath;
    // getToken();
    method1();
    return method2();
  }

  // private void getToken() throws Exception {
  // String getTokenURL =
  // "https://openapi.baidu.com/oauth/2.0/token?grant_type=client_credentials"
  // + "&client_id=" + apiKey + "&client_secret=" + secretKey;
  // HttpURLConnection conn = (HttpURLConnection) new URL(getTokenURL)
  // .openConnection();
  // token = new JSONObject(printResponse(conn)).getString("access_token");
  // }

  private void method1() throws Exception {
    File pcmFile = new File(testFileName);
    HttpURLConnection conn = (HttpURLConnection) new URL(serverURL).openConnection();

    // construct params
    JSONObject params = new JSONObject();
    params.put("format", "amr");
    params.put("rate", 8000);
    params.put("channel", "1");
    params.put("token", token);
    params.put("cuid", cuid);
    params.put("len", pcmFile.length());
    params.put("speech", Base64Util.F2B(pcmFile));

    // add request header
    conn.setRequestMethod("POST");
    conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");

    conn.setDoInput(true);
    conn.setDoOutput(true);

    // send request
    DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
    wr.writeBytes(params.toString());
    wr.flush();
    wr.close();

    printResponse(conn);
  }

  private String method2() throws Exception {
    File pcmFile = new File(testFileName);
    HttpURLConnection conn = (HttpURLConnection) new URL(
        serverURL + "?cuid=" + cuid + "&token=" + token).openConnection();

    // add request header
    conn.setRequestMethod("POST");
    conn.setRequestProperty("Content-Type", "audio/amr; rate=8000");

    conn.setDoInput(true);
    conn.setDoOutput(true);

    // send request
    DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
    wr.write(loadFile(pcmFile));
    wr.flush();
    wr.close();

    return printResponse(conn);
  }

  private String printResponse(HttpURLConnection conn) throws Exception {
    if (conn.getResponseCode() != 200) {
      // request error
      return "";
    }
    InputStream is = conn.getInputStream();
    BufferedReader rd = new BufferedReader(new InputStreamReader(is));
    String line;
    StringBuffer response = new StringBuffer();
    while ((line = rd.readLine()) != null) {
      response.append(line);
    }
    rd.close();
    System.out.println(new JSONObject(response.toString()).toString(4));
    return response.toString();
  }

  private static byte[] loadFile(File file) throws IOException {
    InputStream is = new FileInputStream(file);

    long length = file.length();
    byte[] bytes = new byte[(int) length];

    int offset = 0;
    int numRead = 0;
    while (offset < bytes.length
        && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
      offset += numRead;
    }

    if (offset < bytes.length) {
      is.close();
      throw new IOException("Could not completely read file " + file.getName());
    }

    is.close();
    return bytes;
  }

}
