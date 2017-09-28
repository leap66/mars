package com.leap.mini.net;

import android.content.Context;

import com.leap.mini.model.network.Response;
import com.leap.mini.net.network.event.AuthEvent;
import com.leap.mini.net.network.subscriber.ApiException;
import com.leap.mini.net.network.subscriber.TokenExpiredException;
import com.leap.mini.util.DialogUtil;
import com.leap.mini.util.IsEmpty;
import com.leap.mini.widget.LoadingDialog;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;

import java.net.SocketException;
import java.net.SocketTimeoutException;

/**
 * 网络返回数据解析
 * <p>
 * </> Created by weiyaling on 2017/3/7.
 */

public abstract class HttpSubscriber<T> extends rx.Subscriber<Response<T>> {
  private Context context;
  private LoadingDialog dialog;

  public HttpSubscriber(Context context) {
    this.context = context;
  }

  @Override
  public void onStart() {
    if (!IsEmpty.object(context)) {
      dialog = DialogUtil.getProgressDialog(context);
      dialog.show();
    }
    super.onStart();
  }

  @Override
  public void onCompleted() {
    if (isUnsubscribed()) {
      unsubscribe();
    }
  }

  @Override
  public void onError(Throwable throwable) {
    if (!IsEmpty.object(dialog) && dialog.isShowing()) {
      dialog.dismiss();
    }
    onFailure(parseException(throwable), null);
  }

  @Override
  public void onNext(Response<T> t) {
    if (!IsEmpty.object(dialog) && dialog.isShowing()) {
      dialog.dismiss();
    }
    if (IsEmpty.object(t)) {
      // 短信或阅读消息时response可能为null
      onSuccess(null);
      return;
    }
    if (t.isSuccess()) {
      onSuccess(t);
    } else {
      if (!IsEmpty.list(t.getMessage())) {
        onFailure(t.getMessage().get(0), t);
      } else {
        onFailure(null, t);
      }
    }

  }

  /**
   * 失败
   *
   * @param errorMsg
   *          错误信息
   * @param response
   *          response为null代表是网络错误
   */
  public abstract void onFailure(String errorMsg, Response<T> response);

  /**
   * 成功
   */
  public abstract void onSuccess(Response<T> response);

  private String parseException(Throwable throwable) {
    String errorMessage;
    if (throwable instanceof TokenExpiredException) {
      // 使用EventBus通知跳转到登陆页面
      EventBus.getDefault().post(new AuthEvent(AuthEvent.TOKEN_EXPIRED));
      errorMessage = throwable.getMessage();
    } else if (throwable instanceof SocketTimeoutException) {
      errorMessage = "网络链接超时,请稍后重试!";
    } else if (throwable instanceof SocketException) {
      errorMessage = "网络链接失败,请检查网络设置!";
    } else if (throwable instanceof JSONException) {
      errorMessage = "数据解析失败,请稍候重试!";
    } else if (throwable instanceof ApiException) {
      errorMessage = throwable.getMessage();
    } else {
      errorMessage = "未知错误!";
    }
    return errorMessage;
  }

}
