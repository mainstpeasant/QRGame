import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class ButtonGame {
    private static final int GRID_SIZE = 3;
    private static final int BUTTON_COUNT = GRID_SIZE * GRID_SIZE;

    private JFrame frame;
    private JButton[] buttons;
    private JLabel scoreLabel;
    private int score = 0;
    private int activeButton = -1;
    private Timer timer;

    public ButtonGame() {
        frame = new JFrame("Button Game");
        frame.setSize(400, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(GRID_SIZE, GRID_SIZE));
        buttons = new JButton[BUTTON_COUNT];

        for(int i = 0; i < BUTTON_COUNT; i++) {
            JButton button = new JButton();
            button.setBackground(Color.LIGHT_GRAY);
            button.setOpaque(true);
            button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

            final int index = i;
            button.addActionListener(e -> handleButtonPress(index));

            buttons[i] = button;
            buttonPanel.add(button);

        }

        scoreLabel = new JLabel("Score: 0", SwingConstants.CENTER);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 20));

        frame.add(scoreLabel, BorderLayout.NORTH);
        frame.add(buttonPanel, BorderLayout.CENTER);

        frame.setVisible(true);

        startGame();
    }

    private void startGame() {
        timer = new Timer();
        Random random = new Random();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                SwingUtilities.invokeLater(() -> {
                    if (activeButton != -1) {
                        buttons[activeButton].setBackground(Color.LIGHT_GRAY);
                        gameOver();
                        return;
                    }

                    activeButton = random.nextInt(BUTTON_COUNT);
                    buttons[activeButton].setBackground(Color.GREEN);

                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            SwingUtilities.invokeLater(() -> {
                                if (activeButton != -1) {
                                    buttons[activeButton].setBackground(Color.LIGHT_GRAY);
                                    gameOver();
                                }
                            });
                        }
                    }, 3000);
                });
            }
        }, 0, 2000 + random.nextInt(3000));
    }

    private void handleButtonPress(int index) {
        if (index == activeButton) {
            score++;
            scoreLabel.setText("Score: "  + score);

            buttons[activeButton].setBackground(Color.LIGHT_GRAY);
            activeButton = -1;

        } else {

            gameOver();
        }
    }

    private void gameOver() {
        timer.cancel();
        JOptionPane.showMessageDialog(frame, "GameOver! Your final score is: " + score);
        frame.dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ButtonGame::new);
    }
}
