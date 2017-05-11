package com.leap.mini.mgr.updata;

import com.leap.mini.R;
import com.leap.mini.SystemUtils;
import com.leap.mini.model.UpdateModel;
import com.leap.mini.util.ToastUtil;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import pub.devrel.easypermissions.AfterPermissionGranted;

class ProgressDialog extends Dialog {
  private Context mContext;
  private ProgressBar progressBar;
  private TextView tvProgress;

  ProgressDialog(Context context) {
    super(context, R.style.alert_dialog);
    this.mContext = context;
    initViews();
  }

  private void initViews() {
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    View rootView = LayoutInflater.from(mContext).inflate(R.layout.dialog_update_progress, null,
        false);
    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
        SystemUtils.getScreenWidth() * 11 / 15, RelativeLayout.LayoutParams.WRAP_CONTENT);
    setContentView(rootView, params);
    progressBar = (ProgressBar) rootView.findViewById(R.id.progress_bar);
    tvProgress = (TextView) rootView.findViewById(R.id.tv_progress);
  }

  @AfterPermissionGranted(11)
  void downloadApk(UpdateModel model, String filePath) {
    setCancelable(UpdataUtil.UPDATE_MODE_SILENT.equals(model.updateMode));
    UpdateTask updateTask = new UpdateTask(mContext, model.position, progressBar, tvProgress,
        filePath);
    updateTask.setSuccessListener(new UpdateTask.OnSuccessListener<String>() {
      @Override
      public void onSuccess(String data) {
        UpdataUtil.installApk(getContext(), data);
        dismiss();
      }
    });
    updateTask.setErrorListener(new UpdateTask.OnErrorListener() {
      @Override
      public void onError(Object data) {
        ToastUtil.showFailure(mContext, mContext.getString(R.string.update_failure));
        dismiss();
      }
    });
    updateTask.execute(model.downloadUrl);
  }
}
