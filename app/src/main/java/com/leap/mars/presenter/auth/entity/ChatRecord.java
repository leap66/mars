package com.leap.mars.presenter.auth.entity;

import com.leap.mars.presenter.auth.app.MyApplication;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@SuppressWarnings("serial")
public class ChatRecord implements Serializable {

	private List<ItemInfo> itemInfos = new ArrayList<ItemInfo>();

	public List<ItemInfo> getItemInfos() {
		return this.itemInfos;
	}

	/**
	 * ɾ�����һ�������¼
	 */
	public void removeItem(){
		itemInfos.remove(itemInfos.size()-2);
		saveChatRecord(itemInfos);
	}
	/**
	 * �־û����ļ��� �´δ�Ӧ�������¼��Ȼ����
	 */
	public void saveChatRecord(List<ItemInfo> itemInfos) {
		try {
			this.itemInfos = itemInfos;
			File file = new File(MyApplication.getApp().getCacheDir(),
					"itemInfos");
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			ObjectOutputStream oos = new ObjectOutputStream(
					new FileOutputStream(file));
			oos.writeObject(this);
			oos.flush();
			oos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * �����л����ļ��ж�ȡ������Ϣ
	 * @return chatRecord
	 */
	public ChatRecord readChatRecord() {
		try {
			File file = new File(MyApplication.getApp().getCacheDir(),
					"itemInfos");
			FileInputStream fis = new FileInputStream(file);
			ObjectInputStream ois = new ObjectInputStream(fis);
			ChatRecord chatRecord = (ChatRecord) ois.readObject();
			fis.close();
			ois.close();
			if (chatRecord == null) {
				return new ChatRecord();
			}
			return chatRecord;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new ChatRecord();
	}

	@Override
	public String toString() {
		return "ChatRecord [itemInfos=" + itemInfos + "]";
	}
}
