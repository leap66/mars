package com.leap.mars.presenter.auth;

import android.annotation.SuppressLint;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.leap.mars.BuildConfig;
import com.leap.mars.R;
import com.leap.mars.databinding.ActivityAuthAgreementBinding;
import com.leap.mars.presenter.base.BaseActivity;
import com.leap.mini.widget.NavigationBar;

/**
 * 用户协议查看界面
 * <p>
 * Created by weiyaling on 2016/12/1.
 */
@SuppressLint("SetJavaScriptEnabled")
public class AgreementActivity extends BaseActivity{
  private ActivityAuthAgreementBinding binding;

  /**
   * 初始化控件
   */
  protected void initComponent() {
    binding = DataBindingUtil.setContentView(this, R.layout.activity_auth_agreement);
    binding.setPresenter(new Presenter());
  }

  @Override
  protected View statusBarView() {
    return binding.immersionBar;
  }

  @Override
  protected boolean isDarkFont() {
    return true;
  }

  @Override
  protected void createEventHandlers() {
    binding.navigation.setListener(new NavigationBar.INavigationBarOnClickListener() {
      @Override
      public void onBack() {
        onBackPressed();
      }

      @Override
      public void performAction(View view) {

      }
    });
  }

  /**
   * 加载数据
   */
  protected void loadData(Bundle savedInstanceState) {
    binding.web.getSettings().setJavaScriptEnabled(true);
    binding.web.loadUrl(BuildConfig.AGREEMENT_URL);
    binding.web.setWebChromeClient(new WebChromeClient() {
      @Override
      public void onProgressChanged(WebView view, int newProgress) {
        super.onProgressChanged(view, newProgress);
        if (newProgress == binding.pb.getMax()) {
          binding.pb.setVisibility(View.GONE);
        } else {
          binding.pb.setProgress(newProgress);
        }
      }
    });
  }

  /**
   * 主界面Presenter
   */
  public class Presenter {

    /**
     * 返回注册
     */
    public void onBack() {
      onBackPressed();
    }
  }

}
