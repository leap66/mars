package com.leap.mini.util.listener;

import android.support.v7.widget.RecyclerView;

/**
 * Adapter 改变接口
 * <p>
 * </> Created by weiyaling on 2017/3/7.
 */

public class OnRecycleAdapterChangedListener extends RecyclerView.AdapterDataObserver {
  public void onAnyChanged() {
    // do nothing
  }

  @Override
  public void onChanged() {
    super.onChanged();
    onAnyChanged();
  }

  @Override
  public void onItemRangeChanged(int positionStart, int itemCount) {
    super.onItemRangeChanged(positionStart, itemCount);
    onAnyChanged();
  }

  @Override
  public void onItemRangeInserted(int positionStart, int itemCount) {
    super.onItemRangeInserted(positionStart, itemCount);
    onAnyChanged();
  }

  @Override
  public void onItemRangeRemoved(int positionStart, int itemCount) {
    super.onItemRangeRemoved(positionStart, itemCount);
    onAnyChanged();
  }

  @Override
  public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
    super.onItemRangeMoved(fromPosition, toPosition, itemCount);
    onAnyChanged();
  }
}
