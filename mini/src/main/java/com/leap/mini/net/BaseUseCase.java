package com.leap.mini.net;

import com.leap.mini.net.network.UpdateClient;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.Subscriptions;

/**
 * 用户级基础请求实例
 * <p>
 * </> Created by weiyaling on 17/3/7.
 */

public abstract class BaseUseCase<T> {
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
    subscription = observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
        .subscribe(useCaseSubscriber);
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
