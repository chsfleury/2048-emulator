package fr.noxx90.emulator;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.Properties;

/**
 * @author noxx90
 */
public class Controller {

  private Properties properties;
  private Robot bot;
  private Rectangle boardFrame;
  private Point[][] scanPoints;
  private int lostColor;

  public Controller(Properties properties) throws AWTException {
    this.properties = properties;
    this.bot = new Robot();
    this.lostColor = Utils.parseColor(properties.getProperty("lost"));
  }

  public Optional<Rectangle> findBoard() {
    int boardColor = Utils.parseColor(properties.getProperty("board-color"));
    Dimension boardDimension = new Dimension(Integer.parseInt(properties.getProperty("board-width")), Integer.parseInt(properties.getProperty("board-height")));
    computeScanPoints(boardDimension);

    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Rectangle frame = new Rectangle(screenSize);
    BufferedImage screen = bot.createScreenCapture(frame);

    Optional<Point> topLeftCorner = findTopLeftCorner(screen, boardDimension, boardColor);
    if(topLeftCorner.isPresent()) {
      boardFrame = new Rectangle(topLeftCorner.get(), boardDimension);
      return Optional.of(boardFrame);
    } else {
      return Optional.empty();
    }
  }

  public Optional<Board> scanBoard() {
    BufferedImage screenshotBoard = bot.createScreenCapture(boardFrame);
    try {
      ImageIO.write(screenshotBoard, "png", new File("board.png"));
    } catch (IOException e) {
      e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
    }

    Board board = new Board();
    boolean lost = screenshotBoard.getRGB(5, 5) == lostColor;
    board.setLost(lost);

    if(!lost) {
      for(int y = 0;y < 4;y++) {
        for(int x = 0;x < 4;x++) {
          Point scanPoint = scanPoints[y][x];
          Optional<Tile> tile = Tile.getByColor(screenshotBoard.getRGB(scanPoint.x, scanPoint.y));

          if(tile.isPresent()) {
            board.set(y, x, tile.get());
          } else {
            System.out.println("error in scan board: " + scanPoint.x + ", " + scanPoint.y);
            return Optional.empty();
          }
        }
      }
    }

    return Optional.of(board);
  }

  public void move(Move move) {
    switch (move) {
      case TOP:
        bot.keyPress(KeyEvent.VK_UP);
        bot.keyRelease(KeyEvent.VK_UP);
        break;
      case BOTTOM:
        bot.keyPress(KeyEvent.VK_DOWN);
        bot.keyRelease(KeyEvent.VK_DOWN);
        break;
      case LEFT:
        bot.keyPress(KeyEvent.VK_LEFT);
        bot.keyRelease(KeyEvent.VK_LEFT);
        break;
      case RIGHT:
        bot.keyPress(KeyEvent.VK_RIGHT);
        bot.keyRelease(KeyEvent.VK_RIGHT);
        break;
      default:
        throw new IllegalStateException("bad move");
    }

    try {
      Thread.sleep(400);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public void clickOnBoardToFocus() {
    bot.mouseMove(boardFrame.x + 5, boardFrame.y + 5);
    bot.mousePress(MouseEvent.BUTTON1_DOWN_MASK);
    bot.mouseRelease(MouseEvent.BUTTON1_DOWN_MASK);
  }

  private void computeScanPoints(Dimension dim) {
    scanPoints = new Point[4][4];
    int dx = dim.width / 4;
    int dy = dim.height / 4;

    for(int y = 0;y < 4;y++) {
      for(int x = 0;x < 4;x++) {
        scanPoints[y][x] = new Point(x * dx + dx / 2, y * dy + dy / 4);
      }
    }
  }

  private Optional<Point> findTopLeftCorner(BufferedImage screen, Dimension boardDimension, int boardColor) {
    int limitx = screen.getWidth() - boardDimension.width;
    int limity = screen.getHeight() - boardDimension.height;
    for(int y = 0;y < limity;y+= 5) {
      for(int x = 0;x < limitx;x += 5) {
        try {
          int countRow = countRow(screen, x, y, boardDimension.width, boardColor);
          if(countRow > 0.90 * boardDimension.width) {
            int countCol = countCol(screen, x, y, boardDimension.height, boardColor);
            if(countCol > 0.90 * boardDimension.height) {
              Point topLeftCorner = new Point(x, y);
              return Optional.of(topLeftCorner);
            }
          }
        } catch(Exception e) {
          System.out.println("error at : " + x + ", " + y);
          return Optional.empty();
        }
      }
    }
    return Optional.empty();
  }

  private int countRow(BufferedImage screen, int x, int y, int width, int boardColorRGB) {
    int count = 0;
    for(int i = 0;i < width;i++) {
      if(screen.getRGB(x + i, y) == boardColorRGB) {
        count++;
      }
    }
    return count;
  }

  private int countCol(BufferedImage screen, int x, int y, int height, int boardColorRGB) {
    int count = 0;
    for(int i = 0;i < height;i++) {
      if(screen.getRGB(x, y + i) == boardColorRGB) {
        count++;
      }
    }
    return count;
  }

}
