package com.leap.mini.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.leap.mini.R;
import com.leap.mini.util.DensityUtil;

import java.util.LinkedList;

public class NavigationBar extends RelativeLayout {
  private Context mContext;
  private int background;
  private boolean showNavigationIcon;
  private int navigationIcon;
  private String title;
  private int titleSize = 18;
  private int titleColor;
  private String subTitle;
  private boolean showSubTitle;
  private int subTitleSize = 12;
  private int subTitleColor;
  private int titleType;
  private int actionType;
  private int actionSize = 16;
  private int actionColor;
  private int actionPadding = 11;
  private CharSequence[] actionList;

  private RelativeLayout rootView;
  private ImageView mBackIndicator;
  private TextView mTitleView;
  private TextView mSubTitleView;
  private LinearLayout mActionsView;
  private LinearLayout mActionTitleLayout;

  private INavigationBarOnClickListener listener;

  public NavigationBar(Context context, AttributeSet attrs) {
    super(context, attrs);
    this.mContext = context;
    initAttributeSet(attrs);
    initViews();
    initParams();
    registerListener();
  }

  private void initAttributeSet(AttributeSet attrs) {
    TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.NavigationBar);
    if (typedArray != null) {
      background = typedArray.getResourceId(R.styleable.NavigationBar_bgColor, R.color.white);
      showNavigationIcon = typedArray.getBoolean(R.styleable.NavigationBar_showNavigationIcon,
          true);
      navigationIcon = typedArray.getResourceId(R.styleable.NavigationBar_navigationIcon,
          R.mipmap.ic_title_back);
      title = typedArray.getString(R.styleable.NavigationBar_titleText);
      titleColor = typedArray.getColor(R.styleable.NavigationBar_titleTextColor,
          getResources().getColor(R.color.white));
      showSubTitle = typedArray.getBoolean(R.styleable.NavigationBar_showSubTitle, false);
      subTitle = typedArray.getString(R.styleable.NavigationBar_subTitleText);
      subTitleColor = typedArray.getColor(R.styleable.NavigationBar_subTitleColor,
          getResources().getColor(R.color.white));
      actionType = typedArray.getInt(R.styleable.NavigationBar_actionType, 0);
      titleType = typedArray.getInt(R.styleable.NavigationBar_titleType, 0);
      actionColor = typedArray.getColor(R.styleable.NavigationBar_actionTextColor,
          getResources().getColor(R.color.text_black));

      DisplayMetrics metrics = getContext().getResources().getDisplayMetrics();
      titleSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, titleSize, metrics);
      titleSize = (int) typedArray.getDimension(R.styleable.NavigationBar_titleSize, titleSize);

      subTitleSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, subTitleSize,
          metrics);
      subTitleSize = (int) typedArray.getDimension(R.styleable.NavigationBar_subTitleSize,
          subTitleSize);

      actionSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, actionSize,
          metrics);
      actionSize = (int) typedArray.getDimension(R.styleable.NavigationBar_actionTextSize,
          actionSize);

      actionPadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, actionPadding,
          metrics);
      actionPadding = (int) typedArray.getDimension(R.styleable.NavigationBar_actionTextSize,
          actionPadding);
      actionList = typedArray.getTextArray(R.styleable.NavigationBar_actionList);

      typedArray.recycle();
    }
  }

  private void initViews() {
    LayoutInflater.from(mContext).inflate(R.layout.title_layout, this);
    rootView = (RelativeLayout) findViewById(R.id.action_content);
    mBackIndicator = (ImageView) findViewById(R.id.iv_back);
    mTitleView = (TextView) findViewById(R.id.actionbar_title);
    mSubTitleView = (TextView) findViewById(R.id.actionbar_subTitle);
    mActionsView = (LinearLayout) findViewById(R.id.actionbar_actions);
    mActionTitleLayout = (LinearLayout) findViewById(R.id.action_title_layout);
  }

  private void initParams() {
    rootView.setBackgroundResource(background);
    if (showNavigationIcon) {
      mBackIndicator.setImageResource(navigationIcon);
    } else {
      mBackIndicator.setVisibility(View.GONE);
    }

    if (titleType == 0) {
      mActionTitleLayout.setVisibility(View.VISIBLE);
      mTitleView.setText(title);
      mTitleView.setTextSize(TypedValue.COMPLEX_UNIT_DIP,
          DensityUtil.px2dip(getContext(), titleSize));
      mTitleView.setTextColor(titleColor);

      if (showSubTitle) {
        rootView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
            DensityUtil.dip2px(getContext(), 48)));
        mSubTitleView.setVisibility(VISIBLE);
        mSubTitleView.setText(subTitle);
        mSubTitleView.setTextSize(TypedValue.COMPLEX_UNIT_DIP,
            DensityUtil.px2dip(getContext(), subTitleSize));
        mSubTitleView.setTextColor(subTitleColor);
      } else {
        mSubTitleView.setVisibility(View.GONE);
      }
    } else {
      mActionTitleLayout.setVisibility(View.GONE);
    }

    initActions();
  }

  private void initActions() {
    if (actionList != null && actionList.length > 0) {
      final ActionList list = new ActionList();

      for (int i = 0; i < actionList.length; i++) {
        Action action;
        if (actionType == 0) {

          action = new TextAction(actionList[i].toString(), i);
        } else {

          action = new ImageAction(getImageByIdentifier(actionList[i].toString()), i);
        }
        list.add(action);
      }

      addActions(list);
    }

  }

  private int getImageByIdentifier(String imageName) {
    Resources res = mContext.getResources();
    int resId = res.getIdentifier(imageName.toLowerCase(), "mipmap", mContext.getPackageName());
    return resId > 0 ? resId : R.mipmap.ic_clear;
  }

  private void registerListener() {
    mBackIndicator.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        if (listener != null) {
          listener.onBack();
        }
      }
    });
  }

  public TextView getTitleText() {
    return mTitleView;
  }

  public TextView getSubTitleView() {
    return mSubTitleView;
  }

  public void addActions(ActionList actionList) {
    int actions = actionList.size();
    for (int i = 0; i < actions; i++) {
      addAction(actionList.get(i));
    }
  }

  public View addAction(Action action) {
    final int index = getActionCount();
    return addAction(action, index);
  }

  public View addAction(Action action, int index) {
    View view = inflateAction(action);
    mActionsView.addView(view, index);
    return view;
  }

  public void removeAllActions() {
    mActionsView.removeAllViews();
  }

  public void removeActionAt(int index) {
    mActionsView.removeViewAt(index);
  }

  public void setActionVisible(int index, int visible) {
    mActionsView.getChildAt(index).setVisibility(visible);
  }

  public void removeAction(Action action) {
    int childCount = mActionsView.getChildCount();
    for (int i = 0; i < childCount; i++) {
      View view = mActionsView.getChildAt(i);
      if (view != null) {
        final Object tag = view.getTag();
        if (tag instanceof Action && tag.equals(action)) {
          mActionsView.removeView(view);
        }
      }
    }
  }

  private int getActionCount() {
    return mActionsView.getChildCount();
  }

  private View inflateAction(Action action) {
    if (action == null)
      return null;
    View view = null;
    LinearLayout.LayoutParams params = null;
    if (action instanceof ImageAction) {
      ImageView imageView = new ImageView(getContext());
      params = new LinearLayout.LayoutParams(DensityUtil.dip2px(getContext(), 44),
          DensityUtil.dip2px(getContext(), 44));
      imageView.setScaleType(ImageView.ScaleType.FIT_XY);
      imageView.setImageResource(action.getDrawable());
      imageView.setPadding(actionPadding, actionPadding, actionPadding, actionPadding);
      view = imageView;
      // view.setBackgroundResource(R.drawable.title_click_bg);
    } else if (action instanceof TextAction) {
      TextView textView = new TextView(getContext());
      params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
      textView.setText(action.getText());
      textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP,
          DensityUtil.px2dip(getContext(), actionSize));
      textView.setTextColor(actionColor);
      textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
      textView.setMinWidth(DensityUtil.dip2px(getContext(), 50));
      textView.setPadding(DensityUtil.dip2px(getContext(), 4), 0,
          DensityUtil.dip2px(getContext(), 12), 0);
      view = textView;
      // view.setBackgroundResource(R.drawable.title_click_bg);
    } else if (action instanceof TextImageAction) {
      TextView textView = new TextView(getContext());
      params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
      textView.setText(action.getText());
      textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP,
          DensityUtil.px2dip(getContext(), actionSize));
      textView.setTextColor(actionColor);
      textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
      // textView.setMinWidth(DensityUtil.dip2px(getContext(), 50));
      textView.setPadding(DensityUtil.dip2px(getContext(), 4), 0,
          DensityUtil.dip2px(getContext(), 12), 0);
      textView.setCompoundDrawablePadding(DensityUtil.dip2px(getContext(), 2));

      Drawable leftDrawable = ContextCompat.getDrawable(getContext(), action.getDrawable());
      if (leftDrawable != null) {
        leftDrawable.setBounds(0, 0, leftDrawable.getIntrinsicWidth(),
            leftDrawable.getIntrinsicHeight());
        textView.setCompoundDrawables(leftDrawable, null, null, null);
      }
      view = textView;
    } else if (action instanceof CheckAction) {
      CheckBox checkBox = new CheckBox(getContext());
      params = new LinearLayout.LayoutParams(DensityUtil.dip2px(getContext(), 30),
          DensityUtil.dip2px(getContext(), 30));
      params.rightMargin = DensityUtil.dip2px(getContext(), 12);
      checkBox.setBackgroundDrawable(null);
      checkBox.setButtonDrawable(null);
      checkBox.setChecked(false);
      checkBox.setGravity(Gravity.CENTER);
      checkBox.setButtonDrawable(action.getDrawable());
      view = checkBox;
    }

    view.setLayoutParams(params);
    view.setTag(action);
    view.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        if (listener != null) {
          listener.performAction(v);
        }
      }
    });

    return view;
  }

  public View getViewByAction(Action action) {
    View view = findViewWithTag(action);
    return view;
  }

  public static class ActionList extends LinkedList<Action> {
  }

  public void setListener(INavigationBarOnClickListener listener) {
    this.listener = listener;
  }

  public interface INavigationBarOnClickListener {
    void onBack();

    void performAction(View view);
  }

  public interface Action {
    String getText();

    int getDrawable();

    int getTag();
  }

  public static class ImageAction implements Action {
    final private int mDrawable;
    final private int tag;

    public ImageAction(int drawable, int tag) {
      mDrawable = drawable;
      this.tag = tag;
    }

    @Override
    public int getDrawable() {
      return mDrawable;
    }

    @Override
    public String getText() {
      return null;
    }

    @Override
    public int getTag() {
      return tag;
    }
  }

  public static class CheckAction implements Action {
    final private int mDrawable;
    final private int tag;

    public CheckAction(int drawable, int tag) {
      mDrawable = drawable;
      this.tag = tag;
    }

    @Override
    public int getDrawable() {
      return mDrawable;
    }

    @Override
    public String getText() {
      return null;
    }

    @Override
    public int getTag() {
      return tag;
    }
  }

  public static class TextAction implements Action {
    final private String mText;
    final private int tag;

    public TextAction(String text, int tag) {
      mText = text;
      this.tag = tag;
    }

    @Override
    public int getDrawable() {
      return 0;
    }

    @Override
    public String getText() {
      return mText;
    }

    @Override
    public int getTag() {
      return tag;
    }
  }

  public static class TextImageAction implements Action {
    final private int mDrawable;
    final private String mText;
    final private int tag;

    public TextImageAction(String text, int drawable, int tag) {
      mDrawable = drawable;
      mText = text;
      this.tag = tag;
    }

    @Override
    public int getDrawable() {
      return mDrawable;
    }

    @Override
    public String getText() {
      return mText;
    }

    @Override
    public int getTag() {
      return tag;
    }
  }

}
