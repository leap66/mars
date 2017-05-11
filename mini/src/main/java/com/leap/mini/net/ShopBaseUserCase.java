package com.leap.mini.net;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * ShopClient 请求实例
 * <p>
 * </>Created by weiyaling on 2017/2/22.
 */

public abstract class ShopBaseUserCase<T> extends BaseUseCase<T> {

  protected Observable buildUseCaseObservable() {
    T serverApi = shopApiClient();
    if (serverApi == null) {
      return null;
    }
    return createObservable(serverApi);
  }

  protected abstract Observable createObservable(T serverApi);

  private T shopApiClient() {
    Retrofit retrofit = ApiClient.shopClient();
    if (retrofit == null) {
      return null;
    }
    return retrofit.create(getType());
  }

  public T shopApiClient(String baseUrl) {
    return ApiClient.shopClient(baseUrl).create(getType());
  }
}
