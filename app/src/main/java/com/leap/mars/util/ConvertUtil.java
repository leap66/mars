package com.leap.mars.util;

import com.leap.mars.model.Chat;
import com.leap.mars.model.Dialogue;
import com.leap.mars.model.Voice;
import com.leap.mini.util.IsEmpty;

import java.util.Date;
import java.util.UUID;

/**
 * @author : ylwei
 * @time : 2017/9/20
 * @description :
 */
public class ConvertUtil {

  public static Dialogue VoiceToB(Chat chat) {
    Dialogue dialogue = new Dialogue();
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
