package com.leap.mars.presenter.auth.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import com.leap.mars.util.Base64Util;

import android.os.AsyncTask;
import android.util.Log;

public class BaiDuCallback {

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
   * @param callback
   */
  public void LoadTextReply(final String content, final LoadCallBack callback) {

    AsyncTask<String, String, String> task = new AsyncTask<String, String, String>() {

      @Override
      protected String doInBackground(String... params) {
        String respText = loadTextCallback(content);
        return respText;
      }

      @Override
      protected void onPostExecute(String result) {
        super.onPostExecute(result);
        callback.onDateLoaded(result);
      }

    };
    task.execute();
  }

  String token = "24.abbb9d77c68b74767a70ac9e62d7e4e2.2592000.1472980961.282335-8437596";
  String cuid = "3PwrU3Cu83cKz3CRyWjON2vRCGs4egwA";
  String serverURL = "http://vop.baidu.com/server_api";

  // String testFileName = FilePath; // �ٶ������ṩ����֧��
  /**
   * ���ݴ����content ��ȡ�ظ���Ϣ
   * 
   * @return
   */
  protected String loadTextCallback(String FilePath) {
    String content = null;
    content = method1(FilePath);
    return content;
  }

  private String method1(String FilePath) {
    String replyContent = null;
    try {
      File musicFile = new File(FilePath);
      Log.i("info", FilePath.toString() + "888888888888");
      Log.i("info", musicFile.getAbsolutePath() + "888888888888");
      HttpURLConnection conn = (HttpURLConnection) new URL(serverURL).openConnection();
      // �������
      JSONObject params = new JSONObject();
      params.put("format", "amr");
      params.put("rate", 8000);
      params.put("channel", 1);
      params.put("token", token);
      params.put("cuid", cuid);
      params.put("len", musicFile.length());
      params.put("speech", Base64Util.F2B(musicFile));
      // �����Ϣͷ
      conn.setRequestMethod("POST");
      conn.setRequestProperty("Content-Type", "audio/amr; rate=8000");

      conn.setDoInput(true);
      conn.setDoOutput(true);

      // ��������
      DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
      wr.writeBytes(params.toString());
      wr.flush();
      wr.close();

      // ��ȡ������
      if (conn.getResponseCode() != 200) {
        // request error
        return "";
      }
      InputStream is = conn.getInputStream();
      BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
      StringBuilder sb = new StringBuilder();
      String line = "";
      while ((line = reader.readLine()) != null) {
        sb.append(line);
      }
      reader.close();

      Log.i("info", conn.getResponseCode() + "          000");
      Log.i("info", sb.toString() + "          111");
      replyContent = sb.toString();
    } catch (MalformedURLException e) {
      e.printStackTrace();
    } catch (ProtocolException e) {
      e.printStackTrace();
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (JSONException e) {
      e.printStackTrace();
    }
    return replyContent;
  }

  /**
   * ��ȡ�ļ��ַ���
   */
  private byte[] loadFile(File file) throws IOException {
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
    if (is != null) {
      is.close();
    }

    return bytes;
  }

}
