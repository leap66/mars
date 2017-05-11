package com.leap.mini.mgr.logger;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

import android.util.Log;

/**
 * Created by neil on 2017/4/17.
 */

public class Logger {
  private static HashMap<BaseDestination, BlockingQueue<LogModel>> destinations = new HashMap<>();

  public static void addDestination(BaseDestination destination) {
    BlockingQueue<LogModel> queue = new LinkedBlockingDeque<>();
    destinations.put(destination, queue);
    Logger.Worker worker = new Logger.Worker(destination, queue);
    worker.start();
  }

  public static void info(String message) {
    Logger.sendData(LogLevel.INFO, message, false);
  }

  public static void debug(String message) {
    Logger.sendData(LogLevel.DEBUG, message, false);
  }

  public static void warn(String message) {
    Logger.sendData(LogLevel.WARN, message, false);
  }

  public static void error(String message) {
    Logger.sendData(LogLevel.ERROR, message, false);
  }

  public static void error(String message, Throwable throwable) {
    Logger.sendError(Thread.currentThread(), message, throwable, false);
  }

  public static void error(Thread thread, Throwable message, boolean sync) {
    Logger.sendError(thread, null, message, sync);
  }

  public static void sendError(Thread thread, String message, Throwable throwable, boolean sync) {
    LogModel model = new LogModel();
    model.setLevel(LogLevel.ERROR);

    StringBuilder sb = new StringBuilder();
    if (message != null) {
      sb.append(message + ": ");
    }
    Writer result = new StringWriter();
    PrintWriter printWriter = new PrintWriter(result);
    throwable.printStackTrace(printWriter);
    sb.append(result.toString());
    model.setMessage(sb.toString());
    model.setThread(thread.getName());
    int i = 0;
    for (StackTraceElement element : thread.getStackTrace()) {
      if (!element.isNativeMethod()) {
        i++;
        if (i == 4) {
          model.setFileName(element.getClassName());
          model.setLine(element.getLineNumber());
          model.setFunction(element.getMethodName());
          break;
        }
      }
    }
    send(model, sync);
  }

  public static void sendData(LogLevel level, String message, boolean sync) {
    LogModel model = new LogModel();
    model.setLevel(level);
    model.setMessage(message);
    model.setThread(Thread.currentThread().getName());
    int i = 0;
    for (StackTraceElement element : Thread.currentThread().getStackTrace()) {
      if (!element.isNativeMethod()) {
        i++;
        if (i == 4) {
          model.setFileName(element.getClassName());
          model.setLine(element.getLineNumber());
          model.setFunction(element.getMethodName());
          break;
        }
      }
    }
    send(model, sync);
  }

  public static void send(LogModel model, boolean sync) {
    for (BaseDestination destination : destinations.keySet()) {
      if (sync) {
        destination.send(model.getLevel(), model.getMessage(), model.getThread(),
            model.getFileName(), model.getFunction(), model.getLine());
      } else {
        destinations.get(destination).add(model);
      }
    }
  }

  static class Worker extends Thread {
    BlockingQueue<LogModel> queue;
    private BaseDestination destination;

    public Worker(BaseDestination destination, BlockingQueue<LogModel> queue) {
      this.destination = destination;
      this.queue = queue;
    }

    public void run() {
      while (true) {
        try {
          LogModel model = queue.take();
          System.out.println("------------" + model.getMessage());
          destination.send(model.getLevel(), model.getMessage(), model.getThread(),
              model.getFileName(), model.getFunction(), model.getLine());
        } catch (InterruptedException e) {
          Log.i("进程异常", e.getMessage());
          return;
        }
      }
    }
  }
}
