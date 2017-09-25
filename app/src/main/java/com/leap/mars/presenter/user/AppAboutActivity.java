package com.leap.mars.presenter.user;

import com.leap.mars.R;
import com.leap.mars.databinding.ActivityAboutBinding;
import com.leap.mars.presenter.auth.AgreementActivity;
import com.leap.mars.presenter.base.BaseActivity;
import com.leap.mini.util.DialogUtil;
import com.leap.mini.widget.NavigationBar;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

/**
 * 关于界面
 * <p>
 * Created by weiyaling on 16/12/1.
 */
public class AppAboutActivity extends BaseActivity {
  private ActivityAboutBinding binding;
  private String versionName;

  // 初始化布局
  protected void initComponent() {
    binding = DataBindingUtil.setContentView(AppAboutActivity.this, R.layout.activity_about);
    binding.setPresenter(new Presenter());
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

  @Override
  protected View statusBarView() {
    return binding.immersionBar;
  }

  @Override
  protected boolean isDarkFont() {
    return true;
  }

  @Override
  protected void loadData(Bundle savedInstanceState) {
    try {
      PackageInfo pi = getPackageManager().getPackageInfo(this.getPackageName(), 0);
      versionName = pi.versionName;
    } catch (PackageManager.NameNotFoundException e) {
      DialogUtil.showError(this, e.getMessage()).show();
    }
    binding.codeTv.append(versionName);
    binding.qrCodeIv.setImageBitmap(null);
  }

  public class Presenter {
    public void onBack() {
      onBackPressed();
    }

    /**
     * 用户协议
     */
    public void onReadAgreement() {
      Intent intent = new Intent(AppAboutActivity.this, AgreementActivity.class);
      startActivity(intent);
    }
  }

}
