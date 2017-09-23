package com.leap.mars.network.tts;

import com.leap.mars.model.Voice;
import com.leap.mars.model.VoiceParam;
import com.leap.mini.model.network.Response;

import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * @author : ylwei
 * @time : 2017/9/18
 * @description :
 */
public interface TtsServiceApi {

  /**
   * 百度翻译 文字转换为语音
   *
   * @param voice
   *          信息
   * @return String
   */
  @POST("app/tts/tts")
  Observable<Response<Voice>> tts(@Body Voice voice);

  /**
   * 百度翻译 语音转换为文字
   *
   * @param voice
   *          信息
   * @return String
   */
  @POST("app/tts/vop")
  Observable<Response<Voice>> vop(@Body Voice voice);

  /**
   * 百度 参数设置
   *
   * @param param
   *          语音参数
   * @return String
   */
  @POST("app/tts/param/edit")
  Observable<Response<VoiceParam>> edit(@Query("mobile") VoiceParam param);

  /**
   * 百度 获取参数
   *
   * @param id
   *          用户ID
   * @return String
   */
  @POST("app/tts/param/get")
  Observable<Response<VoiceParam>> get(@Query("id") String id);

}
