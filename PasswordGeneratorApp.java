import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public class PasswordGeneratorApp extends JFrame {
    private JTextField lengthField;
    private JCheckBox includeUppercase;
    private JCheckBox includeLowercase;
    private JCheckBox includeDigits;
    private JCheckBox includeSpecialChars;
    private JButton generateButton;
    private JTextArea passwordArea;

    public PasswordGeneratorApp() {
        setTitle("Password Generator");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(7, 1));

        JLabel lengthLabel = new JLabel("Password Length:");
        lengthField = new JTextField(10);
        JPanel lengthPanel = new JPanel();
        lengthPanel.add(lengthLabel);
        lengthPanel.add(lengthField);

        includeUppercase = new JCheckBox("Include Uppercase Letters");
        includeLowercase = new JCheckBox("Include Lowercase Letters");
        includeDigits = new JCheckBox("Include Digits");
        includeSpecialChars = new JCheckBox("Include Special Characters");

        generateButton = new JButton("Generate Password");
        passwordArea = new JTextArea(3, 20);
        passwordArea.setEditable(false);

        panel.add(lengthPanel);
        panel.add(includeUppercase);
        panel.add(includeLowercase);
        panel.add(includeDigits);
        panel.add(includeSpecialChars);
        panel.add(generateButton);
        panel.add(new JScrollPane(passwordArea));

        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generatePassword();
            }
        });

        add(panel);
    }

    private void generatePassword() {
        int length;
        try {
            length = Integer.parseInt(lengthField.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid password length.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean useUppercase = includeUppercase.isSelected();
        boolean useLowercase = includeLowercase.isSelected();
        boolean useDigits = includeDigits.isSelected();
        boolean useSpecialChars = includeSpecialChars.isSelected();

        if (!(useUppercase || useLowercase || useDigits || useSpecialChars)) {
            JOptionPane.showMessageDialog(this, "Please select at least one character type.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String password = generateRandomPassword(length, useUppercase, useLowercase, useDigits, useSpecialChars);
        passwordArea.setText(password);
    }

    private String generateRandomPassword(int length, boolean useUppercase, boolean useLowercase, boolean useDigits, boolean useSpecialChars) {
        String uppercaseChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowercaseChars = "abcdefghijklmnopqrstuvwxyz";
        String digitChars = "0123456789";
        String specialChars = "!@#$%^&*()_+-=[]{}|;:,.<>?";

        String validChars = "";
        if (useUppercase) validChars += uppercaseChars;
        if (useLowercase) validChars += lowercaseChars;
        if (useDigits) validChars += digitChars;
        if (useSpecialChars) validChars += specialChars;

        if (validChars.isEmpty()) return "No character types selected.";

        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(validChars.length());
            char randomChar = validChars.charAt(randomIndex);
            password.append(randomChar);
        }

        return password.toString();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                PasswordGeneratorApp app = new PasswordGeneratorApp();
                app.setVisible(true);
            }
        });
    }
}
