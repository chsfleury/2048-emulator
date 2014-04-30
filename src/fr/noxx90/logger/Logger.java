package fr.noxx90.logger;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * @author noxx90
 */
public class Logger {

  private static Logger logger;
  private PrintWriter writer;

  static {
    try {
      logger = new Logger();
    } catch (FileNotFoundException e) {

    }
  }

  public Logger() throws FileNotFoundException {
    writer = new PrintWriter("output.log");
  }

  private void writeLog(String log) {
    System.out.println(log);
    writer.println(log);
    writer.flush();
  }

  public static void log(String log) {
    logger.writeLog(log);
  }
}
