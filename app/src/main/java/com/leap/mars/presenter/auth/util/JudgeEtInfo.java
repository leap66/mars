package com.leap.mars.presenter.auth.util;

public class JudgeEtInfo {

	public String getBack(String etInputInfo) {
		if(etInputInfo.equals("��Է�����")){
			return "�������ҵ�Ȼ�Թ��ˣ����ɵñ������";
		}else if(etInputInfo.equals("��׿")){
			return "��׿�㶼���ᣬ�������...";
		}else if(etInputInfo.equals("����")){
			return "С���ǣ�һ��һ��������... �����ܵ�Ŷ...";
		}else if(etInputInfo.equals("��QQ")){
			return null;
		}else if(etInputInfo.contains("q")){
			return "������Ϣ����qq";
		}else if(etInputInfo.contains("*qq*")){
			return "������Ϣ����qq";
		}else if(etInputInfo.contains("����")){
			return "������Ϣ��������";
		}else if(etInputInfo.contains("Qq")){
			return "������Ϣ����Qq";
		}else {
			return null;
		}
	}	
}
