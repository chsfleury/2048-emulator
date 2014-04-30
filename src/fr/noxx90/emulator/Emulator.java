package fr.noxx90.emulator;

import fr.noxx90.bots.Bot;
import fr.noxx90.bots.helper.Helper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import static fr.noxx90.logger.Logger.log;

/**
 * @author noxx90
 */
public class Emulator extends JFrame implements ActionListener {

  private Properties properties;
  private Controller controller;
  private Helper helper;
  private Bot bot;

  private JPanel container;
  private JTextField classSelector;
  private JButton startButton;

  private boolean running;

  public Emulator()  {
    super("2048-Emulator");
    setSize(250, 100);
    setLocation(50, 50);
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    properties = new Properties();

    try {
      properties.load(new FileReader("emulator.properties"));
    } catch (IOException e) {
      dialogError("unable to find emulator.properties");
    }

    for(Object o : properties.keySet()) {
      log(o + "=" + properties.get(o));
    }

    log("properties OK\n\n");

    Tile.init(properties);

    try {
      controller = new Controller(properties);
    } catch (AWTException e) {
      dialogError("unable to get screen and keyboard access");
    }

    helper = new Helper();

    running = false;


    build();
    setVisible(true);
  }

  private Bot createBot(String classname) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
    Class<?> clazz = Class.forName(classname);
    Bot bot = (Bot) clazz.newInstance();
    return bot;
  }

  private void build() {
    container = new JPanel(new GridLayout(0, 1));
    setContentPane(container);

    classSelector = new JTextField("fr.noxx90.bots.impl.NaiveBot");
    container.add(classSelector);

    startButton = new JButton("Start");
    startButton.addActionListener(this);
    container.add(startButton, BorderLayout.NORTH);
  }

  private void start() {
    if(!running) {
      new Thread(new Worker()).start();
    }
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if(e.getSource() == startButton) {
      start();
    }
  }

  private void dialogError(String text) {
    JOptionPane.showMessageDialog(this, text);
    log(text);
  }

  public static void main(String[] args) throws Exception {

    for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
      if ("Nimbus".equals(info.getName())) {
        UIManager.setLookAndFeel(info.getClassName());
        break;
      }
    }

    new Emulator();
  }

  class Worker implements Runnable {

    @Override
    public void run() {
      try {
        bot = createBot(classSelector.getText());
      } catch (Exception e) {
        dialogError("unable to instantiate bot");
      }

      running = true;
      startButton.setText("running...");

      Optional<Rectangle> boardFrame = controller.findBoard();
      if(boardFrame.isPresent()) {
        System.out.println(boardFrame);
        controller.clickOnBoardToFocus();

        Optional<Board> board;
        do {
          board = controller.scanBoard();
          if(board.isPresent()) {
            log(board.get().toString());

            List<Move> possibleMoves = helper.computePossibleMoves(board.get());
            if(possibleMoves.isEmpty()) {
              dialogError("Game Over");
              return;
            }

            Move move = bot.move(board.get(), possibleMoves);
            log("chosen move: " + move.name());

            controller.move(move);
          }
        } while(board.isPresent() && !board.get().isLost());
      } else {
        dialogError("board not found");
      }

      running = false;
      startButton.setText("Start");
    }
  }
}
