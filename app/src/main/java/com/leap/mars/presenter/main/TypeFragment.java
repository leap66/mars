package com.leap.mars.presenter.main;

import com.leap.mars.R;
import com.leap.mars.databinding.FragmentTypeBinding;
import com.leap.mars.presenter.base.BaseFragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * TypeFragment
 * <p>
 * </> Created by weiyaling on 2017/5/11.
 */

public class TypeFragment extends BaseFragment {
  private FragmentTypeBinding binding;

  @Override
  protected View initComponent(LayoutInflater inflater, ViewGroup container) {
    binding = DataBindingUtil.inflate(inflater, R.layout.fragment_type, container, false);
    return binding.getRoot();
  }

  @Override
  protected void loadData(Bundle savedInstanceState) {

  }
}
