package com.leap.mars.presenter.chat;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import com.github.markzhai.recyclerview.BaseViewAdapter;
import com.github.markzhai.recyclerview.SingleTypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.leap.mars.R;
import com.leap.mars.cmp.SessionMgr;
import com.leap.mars.config.AppConfig;
import com.leap.mars.databinding.ActivityChatTypeBinding;
import com.leap.mars.model.chat.Chat;
import com.leap.mars.model.chat.Dialogue;
import com.leap.mars.network.dialogue.usecase.DialogueChatCase;
import com.leap.mars.network.dialogue.usecase.DialogueQueryCase;
import com.leap.mars.presenter.base.BaseActivity;
import com.leap.mars.presenter.chat.widget.ChatUtil;
import com.leap.mars.widget.audio.AudioListener;
import com.leap.mini.mgr.StorageMgr;
import com.leap.mini.model.network.Response;
import com.leap.mini.net.HttpSubscriber;
import com.leap.mini.util.DialogUtil;
import com.leap.mini.util.GsonUtil;
import com.leap.mini.util.IsEmpty;
import com.leap.mini.util.KeyBoardUtil;
import com.leap.mini.util.ToastUtil;
import com.leap.mini.widget.NavigationBar;
import com.leap.mini.widget.pullrefresh.base.layout.BaseHeaderView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author : ylwei
 * @time : 2017/9/22
 * @description :
 */
public class ChatTypeActivity extends BaseActivity {
  private ActivityChatTypeBinding binding;
  private List<Dialogue> dialogueList;
  private SingleTypeAdapter<Dialogue> adapter;

  @Override
  protected void initComponent() {
    binding = DataBindingUtil.setContentView(this, R.layout.activity_chat_type);
    binding.setPresenter(new Presenter());
    binding.setNormal(true);
    dialogueList = new ArrayList<>();
    loadRcy();
  }

  @Override
  protected boolean useStatusBar() {
    return false;
  }

  @Override
  protected void loadData(Bundle savedInstanceState) {
    List<Dialogue> temp = GsonUtil.parseList(
        StorageMgr.get(AppConfig.LOCAL_CHAT_TRACE, StorageMgr.LEVEL_USER),
        new TypeToken<List<Dialogue>>() {
        }.getType());
    if (IsEmpty.list(temp)) {
      dialogueList = new ArrayList<>();
      Dialogue dialogue = new Dialogue();
      dialogue.setUserId(SessionMgr.getUser().getId());
      dialogue.setInfo(getString(R.string.chat_type_info_default));
      dialogue.setTime(new Date());
      dialogueList.add(dialogue);
    } else {
      dialogueList.addAll(temp);
    }
    adapter.clear();
    adapter.addAll(dialogueList);
    binding.homeRcy.smoothScrollToPosition(dialogueList.size());
  }

  @Override
  protected void createEventHandlers() {
    binding.btnRecorder.setAudioListener(new AudioListener() {
      @Override
      public void onFinish(int seconds, String filePath) throws IOException {
        record(ChatUtil.fromVoice(filePath));
      }
    });
    binding.refreshLayout.setOnRefreshListener(new BaseHeaderView.OnRefreshListener() {
      @Override
      public void onRefresh(BaseHeaderView baseHeaderView) {
        new DialogueQueryCase().execute(new HttpSubscriber<List<Dialogue>>(null) {
          @Override
          public void onFailure(String errorMsg, Response response) {
            DialogUtil.getErrorDialog(context, errorMsg).show();
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
    binding.navigation.setListener(new NavigationBar.INavigationBarOnClickListener() {
      @Override
      public void onBack() {
        onBackPressed();
      }

      @Override
      public void performAction(View view) {

      }
    });
  }

  private void record(Chat chat) {
    Dialogue dialogue = ChatUtil.VoiceToB(chat);
    dialogueList.add(dialogue);
    adapter.add(dialogue);
    binding.etInfo.setText("");
    binding.homeRcy.smoothScrollToPosition(dialogueList.size());
    new DialogueChatCase(chat).execute(new HttpSubscriber<Dialogue>(null) {
      @Override
      public void onFailure(String errorMsg, Response response) {
        DialogUtil.getErrorDialog(context, errorMsg).show();
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

  @Override
  protected void onDestroy() {
    StorageMgr.set(AppConfig.LOCAL_CHAT_TRACE, dialogueList, StorageMgr.LEVEL_USER);
    super.onDestroy();
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
      record(ChatUtil.fromInfo(etInfo));
      KeyBoardUtil.keyShow(binding.etInfo, false);
    }

    public void onPlay(String fileName) {
      ToastUtil.showSuccess(context, fileName);
    }
  }
}
