package com.leap.mars.network.dialogue.usecase;

import com.leap.mars.model.Chat;
import com.leap.mars.network.dialogue.DialogueServiceApi;
import com.leap.mini.net.BaseUseCase;

import rx.Observable;

/**
 * @author : ylwei
 * @time : 2017/9/18
 * @description : 聊天
 */
public class DialogueChatCase extends BaseUseCase<DialogueServiceApi> {
  private Chat chat;

  public DialogueChatCase(Chat chat) {
    this.chat = chat;
  }

  @Override
  protected Observable buildUseCaseObservable() {
    return platformApiClient().chat(chat);
  }
}
