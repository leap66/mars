package com.leap.mars.widget.audio;

import java.io.IOException;

/**
 * @author : ylwei
 * @time : 2017/9/22
 * @description :
 */
public interface AudioListener {
  void onFinish(int seconds, String filePath) throws IOException;
}
