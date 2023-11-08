import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class DiceRollerApp extends JFrame {
    private JLabel resultLabel;
    private JButton rollButton;

    public DiceRollerApp() {
        setTitle("Dice Roller");
        setSize(500, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        resultLabel = new JLabel("Roll the dice!");
        resultLabel.setFont(new Font("Arial", Font.BOLD, 20));
        resultLabel.setHorizontalAlignment(SwingConstants.CENTER);

        rollButton = new JButton("Roll");
        rollButton.setFont(new Font("Arial", Font.PLAIN, 18));
        rollButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rollDice();
            }
        });

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(resultLabel, BorderLayout.CENTER);
        panel.add(rollButton, BorderLayout.SOUTH);

        add(panel);
    }

    private void rollDice() {
        Random random = new Random();
        int die1 = random.nextInt(6) + 1; // Roll the first die
        int die2 = random.nextInt(6) + 1; // Roll the second die
        int total = die1 + die2; // Calculate the total result

        resultLabel.setText("Die 1: " + die1 + "   Die 2: " + die2 + "   Total: " + total);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                DiceRollerApp app = new DiceRollerApp();
                app.setVisible(true);
            }
        });
    }
}

