package com.leap.mini.mgr.logger;

/**
 * Created by neil on 2017/4/15.
 */

public interface BaseDestination {
  void send(LogLevel level, String msg, String thread, String file, String function, int line);
}
