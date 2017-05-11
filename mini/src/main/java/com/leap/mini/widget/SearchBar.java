package com.leap.mini.widget;

import java.util.concurrent.TimeUnit;

import com.jakewharton.rxbinding.widget.RxTextView;
import com.leap.mini.R;
import com.leap.mini.widget.cleartextfield.ClearEditText;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.InputFilter;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * 搜索输入框
 * <p>
 * </> Created by weiyaling on 2017/4/26.
 */

public class SearchBar extends LinearLayout {
  private OnSearchListener<View, String> searchListener;
  private int limit;
  private ClearEditText clearEditText;
  private AttributeSet attrs;
  private String hint;
  private boolean firstSearch;
  private boolean autoSearch; // 自动延迟搜索

  public SearchBar(Context context) {
    super(context);
    initView();
  }

  public SearchBar(Context context, AttributeSet attrs) {
    super(context, attrs);
    this.attrs = attrs;
    initView();
  }

  public SearchBar(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    this.attrs = attrs;
    initView();
  }

  private void initView() {
    View view = LayoutInflater.from(getContext()).inflate(R.layout.item_search, null);
    LinearLayout.LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.MATCH_PARENT);
    addView(view, lp);
    clearEditText = (ClearEditText) findViewById(R.id.select_et);
    if (attrs != null) {
      TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.SearchBar);
      hint = array.getString(R.styleable.SearchBar_hint);
      limit = array.getInteger(R.styleable.SearchBar_limit, 20);
      autoSearch = array.getBoolean(R.styleable.SearchBar_auto_search, false);
      array.recycle();
    }
    setHint(hint);
    setLimit(limit);
    setAutoSearch(autoSearch);
  }

  @Override
  protected void onAttachedToWindow() {
    super.onAttachedToWindow();
    createEventHandlers();
  }

  private void createEventHandlers() {
    clearEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
      @Override
      public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        keyBoardHide();
        if (searchListener != null && !autoSearch)
          searchListener.onSearch(clearEditText, getText());
        return false;
      }
    });
  }

  public void setOnSearchListener(OnSearchListener<View, String> confirmListener) {
    this.searchListener = confirmListener;
  }

  public void setHint(String hint) {
    this.hint = hint;
    clearEditText.setHint(hint);
  }

  public void setText(String text) {
    clearEditText.setText(text);
    clearEditText.setSelection(text.length());
  }

  public void setLimit(int limit) {
    this.limit = limit;
    clearEditText.setFilters(new InputFilter[] {
        new InputFilter.LengthFilter(limit) });
  }

  public String getText() {
    return clearEditText.getText().toString().trim();
  }

  public ClearEditText getClearEditText() {
    return clearEditText;
  }

  public void setAutoSearch(boolean autoSearch) {
    this.autoSearch = autoSearch;
    if (autoSearch)
      textChanges(clearEditText).subscribe(new Action1<CharSequence>() {
        @Override
        public void call(CharSequence charSequence) {
          if (!firstSearch) {
            firstSearch = true;
            return;
          }
          if (searchListener != null)
            searchListener.onSearch(clearEditText, getText());
        }
      });
  }

  public interface OnSearchListener<V, T> {
    void onSearch(V view, T data);
  }

  /**
   * 延迟搜索
   */
  private Observable<CharSequence> textChanges(TextView view) {
    return RxTextView.textChanges(view).debounce(500, TimeUnit.MILLISECONDS)
        .subscribeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread());
  }

  /**
   * 强制取消软键盘
   */
  private void keyBoardHide() {
    InputMethodManager imm = (InputMethodManager) clearEditText.getContext()
        .getSystemService(Context.INPUT_METHOD_SERVICE);
    imm.hideSoftInputFromWindow(clearEditText.getWindowToken(), 0);
  }
}
