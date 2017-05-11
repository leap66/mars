package com.leap.mini.net;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.greenrobot.eventbus.EventBus;

import com.leap.mini.net.network.UpdateClient;
import com.leap.mini.net.network.event.AuthEvent;
import com.leap.mini.net.network.subscriber.TokenExpiredException;
import com.leap.mini.util.DialogUtil;
import com.leap.mini.widget.sweetAlert.SweetAlertDialog;

import android.content.Context;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.Subscriptions;

/**
 * 用户级基础请求实例
 * <p>
 * </> Created by weiyaling on 17/3/7.
 */

public abstract class BaseUseCase<T> {
  protected Context context;
  private SweetAlertDialog dialog;
  private Subscription subscription = Subscriptions.empty();

  protected BaseUseCase() {
  }

  /**
   * 构建UseCase
   */
  protected abstract Observable buildUseCaseObservable();

  public <T> void execute(Subscriber useCaseSubscriber) {
    Observable observable = this.buildUseCaseObservable();
    if (observable == null) {
      useCaseSubscriber.unsubscribe();
      return;
    }
    if (context != null) {
      dialog = DialogUtil.getProgressDialog(context);
      dialog.show();
    }
    subscription = observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
        .doOnNext(new Action1() {
          @Override
          public void call(Object o) {
            if (dialog != null && dialog.isShowing()) {
              dialog.dismiss();
            }
          }
        }).onErrorResumeNext(new Func1<Throwable, Observable>() {
          @Override
          public Observable call(Throwable throwable) {
            if (dialog != null && dialog.isShowing()) {
              dialog.dismiss();
            }
            if (throwable instanceof TokenExpiredException) {
              EventBus.getDefault().post(new AuthEvent(AuthEvent.TOKEN_EXPIRED));
              return Observable.empty();
            } else {
              return Observable.error(throwable);
            }
          }
        }).subscribe(useCaseSubscriber);
  }

  /**
   * 取消订阅
   */
  public void unSubscribe() {
    if (!subscription.isUnsubscribed()) {
      subscription.unsubscribe();
    }
  }

  protected T platformApiClient() {
    return ApiClient.platformClient().create(getType());
  }

  public T updateClient() {
    return UpdateClient.updateClient().create(getType());
  }

  protected Class<T> getType() {
    Class<T> entityClass = null;
    Type t = getClass().getGenericSuperclass();
    Type[] p = ((ParameterizedType) t).getActualTypeArguments();
    entityClass = (Class<T>) p[0];
    return entityClass;
  }
}
