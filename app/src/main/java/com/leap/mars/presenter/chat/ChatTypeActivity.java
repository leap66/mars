package com.leap.mars.presenter.chat;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import com.github.markzhai.recyclerview.BaseViewAdapter;
import com.github.markzhai.recyclerview.SingleTypeAdapter;
import com.leap.mars.R;
import com.leap.mars.cmp.SessionMgr;
import com.leap.mars.databinding.ActivityChatTypeBinding;
import com.leap.mars.model.Chat;
import com.leap.mars.model.Dialogue;
import com.leap.mars.model.Voice;
import com.leap.mars.network.dialogue.usecase.DialogueChatCase;
import com.leap.mars.network.dialogue.usecase.DialogueQueryCase;
import com.leap.mars.presenter.base.BaseActivity;
import com.leap.mars.util.ConvertUtil;
import com.leap.mars.util.FileUtil;
import com.leap.mars.widget.audio.AudioListener;
import com.leap.mini.model.network.Response;
import com.leap.mini.net.HttpSubscriber;
import com.leap.mini.util.DialogUtil;
import com.leap.mini.util.IsEmpty;
import com.leap.mini.util.ToastUtil;
import com.leap.mini.widget.pullrefresh.base.layout.BaseHeaderView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author : ylwei
 * @time : 2017/9/22
 * @description :
 */
public class ChatTypeActivity extends BaseActivity {
  private ActivityChatTypeBinding binding;
  private Context context;
  private List<Dialogue> dialogueList;
  private SingleTypeAdapter<Dialogue> adapter;

  @Override
  protected void initComponent() {
    binding = DataBindingUtil.setContentView(this, R.layout.activity_chat_type);
    binding.setPresenter(new Presenter());
    binding.setNormal(true);
    context = this;
    dialogueList = new ArrayList<>();
    loadRcy();
  }

  @Override
  protected View statusBarView() {
    return binding.immersionBar;
  }

  @Override
  protected boolean isDarkFont() {
    return true;
  }

  @Override
  protected void loadData(Bundle savedInstanceState) {
    Dialogue dialogue = new Dialogue();
    dialogue.setInfo(getString(R.string.chat_type_info_default));
    dialogue.setTime(new Date());
    dialogueList.add(dialogue);
    adapter.clear();
    adapter.addAll(dialogueList);
  }

  @Override
  protected void createEventHandlers() {
    binding.btnRecorder.setAudioListener(new AudioListener() {
      @Override
      public void onFinish(int seconds, String filePath) throws IOException {
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
        record(chat);
      }
    });
    binding.refreshLayout.setOnRefreshListener(new BaseHeaderView.OnRefreshListener() {
      @Override
      public void onRefresh(BaseHeaderView baseHeaderView) {
        new DialogueQueryCase().execute(new HttpSubscriber<List<Dialogue>>(null) {
          @Override
          public void onFailure(String errorMsg, Response response) {
            DialogUtil.showError(context, errorMsg).show();
            binding.refreshLayout.finishLoad(false);
          }

          @Override
          public void onSuccess(Response<List<Dialogue>> response) {
            if (IsEmpty.list(response.getData())) {
              binding.refreshLayout.finishLoad(false);
              return;
            }
            dialogueList = response.getData();
            adapter.clear();
            adapter.addAll(dialogueList);
            binding.refreshLayout.finishLoad(false);
          }
        });
      }
    });
    binding.refreshLayout.finishLoad(false);
    binding.etInfo.setOnKeyListener(new View.OnKeyListener() {
      @Override
      public boolean onKey(View v, int keyCode, KeyEvent keyEvent) {
        if (keyCode == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_UP) {
          new Presenter().onSend();
          return true;
        }
        return false;
      }
    });
    binding.etInfo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
      @Override
      public void onFocusChange(View v, boolean hasFocus) {
        binding.setNormal(hasFocus);
      }
    });
  }

  private void record(Chat chat) {
    Dialogue dialogue = ConvertUtil.VoiceToB(chat);
    dialogueList.add(dialogue);
    adapter.add(dialogue);
    binding.etInfo.setText("");
    binding.homeRcy.smoothScrollToPosition(dialogueList.size());
    new DialogueChatCase(chat).execute(new HttpSubscriber<Dialogue>(null) {
      @Override
      public void onFailure(String errorMsg, Response response) {
        DialogUtil.showError(context, errorMsg).show();
      }

      @Override
      public void onSuccess(Response<Dialogue> response) {
        Dialogue temp = response.getData();
        dialogueList.add(temp);
        adapter.add(temp);
        binding.homeRcy.smoothScrollToPosition(dialogueList.size());
      }
    });
  }

  private void loadRcy() {
    adapter = new SingleTypeAdapter<>(context, R.layout.item_chat_type);
    adapter.setPresenter(new Presenter());
    binding.setAdapter(adapter);
  }

  public class Presenter implements BaseViewAdapter.Presenter {

    public void onBack() {
      onBackPressed();
    }

    public void onVoice() {
      binding.setNormal(false);
      binding.etInfo.clearFocus();
    }

    public void onSend() {
      String etInfo = binding.etInfo.getText().toString().trim();
      if (IsEmpty.string(etInfo))
        return;
      Chat chat = new Chat();
      chat.setId(UUID.randomUUID().toString());
      chat.setInfo(etInfo);
      chat.setTime(new Date());
      chat.setLoc("");
      chat.setUserid(SessionMgr.getUser().getId());
      record(chat);
    }

    public void onPlay(String fileName) {
      ToastUtil.showSuccess(context, fileName);
    }
  }
}
