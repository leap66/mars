package com.leap.mini.widget.cleartextfield;

import com.leap.mini.R;
import com.leap.mini.widget.cleartextfield.validator.DefaultTextValidator;
import com.leap.mini.widget.cleartextfield.validator.EmptyValidator;
import com.leap.mini.widget.cleartextfield.validator.FieldValidateError;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.EditText;

/**
 * <p>
 * </> Created by weiyaling on 2017/3/7.
 */

public class ClearEditText extends EditText {

  protected Drawable rightImg;
  protected Drawable defRightRes;

  private boolean isShow = true;

  private DefaultTextValidator defaultTextValidator;

  private RightDrawableClickListener rightDrawableClickListener;

  public ClearEditText(Context context) {
    this(context, null);
    init(context, null);
  }

  public ClearEditText(Context context, AttributeSet attrs) {
    this(context, attrs, android.R.attr.editTextStyle);
    init(context, null);
  }

  public ClearEditText(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    init(context, null);
  }

  public Drawable getRightImg() {
    return rightImg;
  }

  public void setRightImg(Drawable rightImg) {
    this.rightImg = rightImg;
  }

  public void setRightDrawableClickListener(RightDrawableClickListener rightDrawableClickListener) {
    this.rightDrawableClickListener = rightDrawableClickListener;
  }

  protected void init(Context context, AttributeSet attrs) {
    defRightRes = ContextCompat.getDrawable(context, R.mipmap.ic_clear);
    if (getCompoundDrawables()[2] == null && rightImg == null) {
      rightImg = defRightRes;
    }
    rightImg.setBounds(0, 0, rightImg.getIntrinsicWidth(), rightImg.getIntrinsicHeight());
    defaultTextValidator = new DefaultTextValidator(this);
    String errMsg = getContext().obtainStyledAttributes(attrs, R.styleable.ClearEditText)
        .getString(R.styleable.ClearEditText_errMsg);
    defaultTextValidator.addValidator(new EmptyValidator(errMsg));
    setHintTextColor(ContextCompat.getColor(getContext(), R.color.text_grey));
  }

  @Override
  public boolean onTouchEvent(MotionEvent event) {
    if (event.getAction() == MotionEvent.ACTION_UP) {
      if (getCompoundDrawables()[2] != null) {
        if (event.getX() > (getWidth() - getTotalPaddingRight())
            && (event.getX() < ((getWidth() - getPaddingRight())))) {
          if (rightDrawableClickListener == null)
            this.setText(null);
          else
            rightDrawableClickListener.onRightClick(this);
        }
      }
    }
    return super.onTouchEvent(event);
  }

  @Override
  protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
    super.onFocusChanged(focused, direction, previouslyFocusedRect);
    updateClearIcon(focused ? getText().length() > 0 : focused, getRightImg());
  }

  public FieldValidateError validateEditText() {
    return defaultTextValidator.isValid() ? null : defaultTextValidator.getError();
  }

  public void updateClearIcon(boolean visible, Drawable img) {
    setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1],
        isShow ? (visible ? img : null) : null, getCompoundDrawables()[3]);
    setCompoundDrawablePadding(dip2px(getContext(), 5));
  }

  @Override
  protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
    super.onTextChanged(text, start, lengthBefore, lengthAfter);
    updateClearIcon(text.length() > 0, getRightImg());
  }

  @Override
  protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
    super.onLayout(changed, left, top, right, bottom);
    updateClearIcon(hasFocus() ? getText().length() > 0 : hasFocus(), getRightImg());
  }

  public interface RightDrawableClickListener {
    void onRightClick(ClearEditText view);
  }

  private int dip2px(Context context, float dpValue) {
    final float scale = context.getResources().getDisplayMetrics().density;
    return (int) (dpValue * scale + 0.5f);
  }
}
