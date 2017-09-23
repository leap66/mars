package com.leap.mars.network.dialogue.usecase;

import com.leap.mars.network.dialogue.DialogueServiceApi;
import com.leap.mini.net.BaseUseCase;

import rx.Observable;

/**
 * @author : ylwei
 * @time : 2017/9/18
 * @description : 删除
 */
public class DialogueDeleteCase extends BaseUseCase<DialogueServiceApi> {
  private String id;

  public DialogueDeleteCase(String id) {
    this.id = id;
  }

  @Override
  protected Observable buildUseCaseObservable() {
    return platformApiClient().delete(id);
  }
}
