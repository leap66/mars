package com.leap.mars.network.dialogue;

import com.leap.mars.model.Chat;
import com.leap.mars.model.Dialogue;
import com.leap.mini.model.network.Response;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * @author : ylwei
 * @time : 2017/9/18
 * @description :
 */
public interface DialogueServiceApi {

  /**
   * 聊天
   *
   * @param chat
   *          聊天信息
   * @return String
   */
  @POST("app/chat/chat")
  Observable<Response<Dialogue>> chat(@Body Chat chat);

  /**
   * 查询用户聊天记录
   *
   * @param id
   *          用户ID
   * @return String
   */
  @POST("app/chat/query")
  Observable<Response<List<Dialogue>>> query(@Query("id") String id);

  /**
   * 删除
   *
   * @param id
   *          聊天信息ID
   * @return String
   */
  @POST("app/chat/delete")
  Observable<Response<Boolean>> delete(@Query("id") String id);
}
