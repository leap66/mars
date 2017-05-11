package com.leap.mini.mgr.updata;

import java.io.File;

import com.leap.mini.R;
import com.leap.mini.SystemUtils;
import com.leap.mini.databinding.DialogUpdateBinding;
import com.leap.mini.model.UpdateModel;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

public class UpdateDialog extends Dialog {
  private UpdateModel model;
  private Context mContext;
  private String filePath;

  public UpdateDialog(Context context, UpdateModel model) {
    super(context, R.style.alert_dialog);
    this.mContext = context;
    this.model = model;
    initView();
  }

  private void initView() {
    DialogUpdateBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext),
        R.layout.dialog_update, null, true);
    binding.setPresenter(new Presenter());
    binding.setItem(model);
    setCancelable(!Util.type(model));
    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
        SystemUtils.getScreenWidth() * 11 / 15, SystemUtils.getScreenHeight() / 2);
    setContentView(binding.getRoot(), params);
  }

  public void setFilePath(String filePath) {
    this.filePath = filePath;
  }

  public class Presenter {
    /**
     * 下次更新
     */
    public void onCancel() {
      dismiss();
    }

    /**
     * 立即更新
     */
    public void onUpData() {
      String currentMd5 = "-1";
      try {
        currentMd5 = UpdataUtil.calculateMD5ofFile(filePath);
      } catch (Exception e) {
        e.printStackTrace();
      }
      if (currentMd5.equals(model.md5)) {
        UpdataUtil.installApk(mContext, filePath);
        dismiss();
      } else {
        if (new File(filePath).exists())
          model.position = new File(filePath).length();
        ProgressDialog progressDialog = new ProgressDialog(mContext);
        progressDialog.downloadApk(model, filePath);
        progressDialog.show();
        dismiss();
      }
    }
  }

  public static class Util {
    public static boolean type(UpdateModel model) {
      return UpdataUtil.UPDATE_MODE_BLOCK.equals(model.updateMode);
    }
  }
}
