package com.leap.mars.network.dialogue.usecase;

import com.leap.mars.cmp.SessionMgr;
import com.leap.mars.network.dialogue.DialogueServiceApi;
import com.leap.mini.net.BaseUseCase;

import rx.Observable;

/**
 * @author : ylwei
 * @time : 2017/9/18
 * @description : 查询用户聊天记录
 */
public class DialogueQueryCase extends BaseUseCase<DialogueServiceApi> {

  @Override
  protected Observable buildUseCaseObservable() {
    return platformApiClient().query(SessionMgr.getUser().getId());
  }
}
