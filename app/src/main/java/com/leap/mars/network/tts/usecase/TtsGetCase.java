package com.leap.mars.network.tts.usecase;

import com.leap.mars.network.tts.TtsServiceApi;
import com.leap.mini.net.BaseUseCase;

import rx.Observable;

/**
 * @author : ylwei
 * @time : 2017/9/18
 * @description : 百度 获取参数
 */
public class TtsGetCase extends BaseUseCase<TtsServiceApi> {
  private String id;

  public TtsGetCase(String id) {
    this.id = id;
  }

  @Override
  protected Observable buildUseCaseObservable() {
    return platformApiClient().get(id);
  }
}
