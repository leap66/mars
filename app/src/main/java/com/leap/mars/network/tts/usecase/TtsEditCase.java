package com.leap.mars.network.tts.usecase;

import com.leap.mars.model.VoiceParam;
import com.leap.mars.network.tts.TtsServiceApi;
import com.leap.mini.net.BaseUseCase;

import rx.Observable;

/**
 * @author : ylwei
 * @time : 2017/9/18
 * @description : 百度 参数设置
 */
public class TtsEditCase extends BaseUseCase<TtsServiceApi> {
  private VoiceParam param;

  public TtsEditCase(VoiceParam param) {
    this.param = param;
  }

  @Override
  protected Observable buildUseCaseObservable() {
    return platformApiClient().edit(param);
  }
}
