package com.leap.mars.network.tts.usecase;

import com.leap.mars.model.Voice;
import com.leap.mars.network.tts.TtsServiceApi;
import com.leap.mini.net.BaseUseCase;

import rx.Observable;

/**
 * @author : ylwei
 * @time : 2017/9/18
 * @description : 百度翻译 文字转换为语音
 */
public class TtsCase extends BaseUseCase<TtsServiceApi> {
  private Voice voice;

  public TtsCase(Voice voice) {
    this.voice = voice;
  }

  @Override
  protected Observable buildUseCaseObservable() {
    return platformApiClient().tts(voice);
  }
}
