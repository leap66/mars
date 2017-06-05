package com.leap.mars.presenter.auth.model;

public interface IModel {
	
	public void LoadRobotReply(String content, LoadCallBack callback);
	
	/**
	 * ͳ�õĻص��ӿ�
	 * @author Administrator
	 *
	 */
	public interface LoadCallBack{
		/**
		 * ���ݼ�����ɺ�ִ��
		 */
		void onDateLoaded(Object obj);
	}
}
