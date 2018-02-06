package com.leap.mars.presenter.chat.widget;

import com.leap.mars.cmp.SessionMgr;
import com.leap.mars.model.chat.Chat;
import com.leap.mars.model.chat.Dialogue;
import com.leap.mars.model.Voice;
import com.leap.mars.util.FileUtil;
import com.leap.mini.util.IsEmpty;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

/**
 * @author : ylwei
 * @time : 2017/9/20
 * @description :
 */
public class ChatUtil {

  public static Chat fromInfo(String info) {
    Chat chat = new Chat();
    chat.setId(UUID.randomUUID().toString());
    chat.setInfo(info);
    chat.setTime(new Date());
    chat.setLoc("");
    chat.setUserid(SessionMgr.getUser().getId());
    return chat;
  }

  public static Chat fromVoice(String filePath) throws IOException {
    Voice voice = new Voice();
    voice.setId(UUID.randomUUID().toString());
    voice.setUserId(SessionMgr.getUser().getId());
    voice.setAsk(true);
    File file = new File(filePath);
    voice.setCode(FileUtil.loadFile(file));
    voice.setLen(file.length());
    voice.setName(filePath);
    voice.setFormat("amr");
    Chat chat = new Chat();
    chat.setUserid(SessionMgr.getUser().getId());
    chat.setTime(new Date());
    chat.setVoice(voice);
    chat.setInfo("语音文件请求");
    return chat;
  }

  public static Dialogue VoiceToB(Chat chat) {
    Dialogue dialogue = new Dialogue();
    if (IsEmpty.object(chat))
      return dialogue;
    dialogue.setId(IsEmpty.string(chat.getId()) ? UUID.randomUUID().toString() : chat.getId());
    if (IsEmpty.object(chat.getVoice())) {
      dialogue.setAsk(true);
      dialogue.setTime(chat.getTime());
      dialogue.setInfo(chat.getInfo());
      dialogue.setUserId(chat.getUserid());
      dialogue.setLoc(chat.getLoc());
    } else {
      Voice voice = chat.getVoice();
      dialogue.setAsk(voice.isAsk());
      dialogue.setInfo(voice.getInfo());
      dialogue.setVoiceLen(voice.getLen());
      dialogue.setVoiceName(voice.getName());
      dialogue.setId(voice.getId());
      dialogue.setUserId(voice.getUserId());
      dialogue.setTime(new Date());
    }
    return dialogue;
  }
}
