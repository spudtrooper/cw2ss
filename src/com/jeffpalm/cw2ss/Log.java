package com.jeffpalm.cw2ss;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Log {

  private final static Logger LOGGER = Logger.getLogger("cw2ss");
  static {
    LOGGER.setLevel(Level.INFO);
  }

  public static void info(String msg) {
    LOGGER.info(msg);
  }

  public static void handle(Throwable t) {
    t.printStackTrace();
    LOGGER.severe(t.getMessage());
  }

  public static void fine(String msg) {
    LOGGER.fine(msg);
  }

  public static void finer(String msg) {
    LOGGER.finer(msg);
  }

  public static void finest(String msg) {
    LOGGER.finest(msg);
  }
}
