package com.leap.mars.presenter.auth.presenter;

import com.leap.mars.presenter.auth.model.IModel;
import com.leap.mars.presenter.auth.model.RobotModel;
import com.leap.mars.presenter.auth.view.IView;

public class RobotPresenter implements IPresenter {

  private IModel model;
  private IView view;

  public RobotPresenter(IView view) {
    model = new RobotModel();
    this.view = view;
  }

  @Override
  public void getRobotReply(String content) {
    model.LoadRobotReply(content, new IModel.LoadCallBack() {

      @Override
      public void onDateLoaded(Object obj) {
        String replyText = (String) obj;
        view.showList(replyText);
      }
    });
  }

}
