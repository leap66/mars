package com.leap.mars.presenter.scan;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;

import com.google.zxing.Result;
import com.leap.mars.R;
import com.leap.mars.databinding.ActivityScannerBinding;

import me.dm7.barcodescanner.core.IViewFinder;
import me.dm7.barcodescanner.core.ViewFinderView;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * 页面描述：二维码及条码扫描 return ：字符串
 * <p>
 * Created by ditclear on 2016/12/7.
 */

public class QRScannerActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {
  public static final String KEY_RESULT = "result";
  public static final int RC_CAMERA = 123;
  private ZXingScannerView mScannerView;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ActivityScannerBinding binding = DataBindingUtil.setContentView(this,
        R.layout.activity_scanner);
    binding.setPresenter(new Presenter());
    mScannerView = new ZXingScannerView(this) {
      @Override
      protected IViewFinder createViewFinderView(Context context) {
        return new CustomViewFinderView(context);
      }
    };
    binding.contentFrame.addView(mScannerView);
  }

  public class Presenter {
    /**
     * 返回
     */
    public void onBack() {
      finish();
    }

    /**
     * 闪关灯
     */
    public void onToggleFlash() {
      mScannerView.toggleFlash();
    }
  }

  @Override
  protected void onResume() {
    super.onResume();
    mScannerView.setResultHandler(this);
    mScannerView.startCamera();
  }

  @Override
  protected void onPause() {
    super.onPause();
    mScannerView.stopCamera();
  }

  @Override
  public void handleResult(Result rawResult) {
    String barcode = rawResult.getText();
    Intent intent = new Intent();
    intent.putExtra(KEY_RESULT, barcode);
    setResult(RESULT_OK, intent);
    finish();
  }

  private static class CustomViewFinderView extends ViewFinderView {
    public static final String TRADE_MARK_TEXT = "ZXing";
    public final Paint PAINT = new Paint();

    public CustomViewFinderView(Context context) {
      super(context);
      init();
    }

    public CustomViewFinderView(Context context, AttributeSet attrs) {
      super(context, attrs);
      init();
    }

    private void init() {
      setSquareViewFinder(true);
    }

    @Override
    public void onDraw(Canvas canvas) {
      super.onDraw(canvas);
      drawTradeMark(canvas);
    }

    private void drawTradeMark(Canvas canvas) {
      Rect framingRect = getFramingRect();
      float tradeMarkTop;
      float tradeMarkLeft;
      if (framingRect != null) {
        tradeMarkTop = framingRect.bottom + PAINT.getTextSize() + 10;
        tradeMarkLeft = framingRect.left;
      } else {
        tradeMarkTop = 10;
        tradeMarkLeft = canvas.getHeight() - PAINT.getTextSize() - 10;
      }
      canvas.drawText(TRADE_MARK_TEXT, tradeMarkLeft, tradeMarkTop, PAINT);
    }
  }
}