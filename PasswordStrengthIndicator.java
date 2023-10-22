import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class PasswordStrengthIndicator extends JFrame {

    private JPasswordField passwordField;
    private JTextField usernameField; // Added a JTextField for the username
    private JLabel strengthLabel;
    private JLabel infoLabel;

    public PasswordStrengthIndicator() {
        setTitle("Login Page");
        setSize(300, 250); // Reduced the height
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create a panel for the login components
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new GridLayout(5, 1, 5, 5)); // 5 rows, 1 column

        // Create a label for the heading
        JLabel headingLabel = new JLabel("LogIn Page");
        headingLabel.setFont(new Font("Arial", Font.BOLD, 24)); // Large font size
        headingLabel.setHorizontalAlignment(JLabel.CENTER); // Center the heading
        headingLabel.setVerticalAlignment(JLabel.CENTER);
        headingLabel.setForeground(Color.BLUE); // Change the font color

        // Create a panel for the username and password fields
        JPanel fieldPanel = new JPanel();
        fieldPanel.setLayout(new GridLayout(2, 2, 5, 5)); // 2 rows, 2 columns
        fieldPanel.setBorder(new CompoundBorder(
                new LineBorder(Color.LIGHT_GRAY, 2, true),
                new EmptyBorder(10, 10, 10, 10)
        ));

        JLabel userLabel = new JLabel("Username:"); // Label for the username
        usernameField = new JTextField(10); // Text field for the username
        usernameField.setBorder(new LineBorder(Color.LIGHT_GRAY, 1, true));
        JLabel titleLabel = new JLabel("Enter Password:");
        passwordField = new JPasswordField(10);
        passwordField.setBorder(new LineBorder(Color.LIGHT_GRAY, 1, true));
        strengthLabel = new JLabel("Password Strength: ");
        infoLabel = new JLabel("");
        infoLabel.setBorder(new LineBorder(Color.LIGHT_GRAY, 1, true));

        // Create a "Submit" button
        JButton submitButton = new JButton("Submit");
        submitButton.setFont(new Font("Arial", Font.PLAIN, 20)); // Smaller font size
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (passwordField.getPassword().length == 0) {
                    JOptionPane.showMessageDialog(PasswordStrengthIndicator.this, "Please enter a password.");
                } else {
                    calculateAndUpdatePasswordStrength();
                }
            }
        });

        passwordField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (passwordField.getPassword().length == 0) {
                    JOptionPane.showMessageDialog(PasswordStrengthIndicator.this, "Please enter a password.");
                } else {
                    calculateAndUpdatePasswordStrength();
                }
            }
        });

        fieldPanel.add(userLabel);
        fieldPanel.add(usernameField); // Add the username field
        fieldPanel.add(titleLabel);
        fieldPanel.add(passwordField);

        loginPanel.add(headingLabel); // Add the heading
        loginPanel.add(fieldPanel);
        loginPanel.add(strengthLabel);
        loginPanel.add(infoLabel);

        // Add the "Submit" button to the panel and center it
        JPanel submitPanel = new JPanel();
        submitPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        submitPanel.add(submitButton);
        loginPanel.add(submitPanel);

        add(loginPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    private void calculateAndUpdatePasswordStrength() {
        String username = usernameField.getText(); // Get the username
        String password = new String(passwordField.getPassword());
        String strength = calculatePasswordStrength(password);
        String info = getPasswordInfo(password);

        strengthLabel.setText("Password Strength: " + strength);
        infoLabel.setText(info);

        if (strength.equals("Strong")) {
            strengthLabel.setForeground(Color.GREEN);
            // Show a "Thank you" message if the password is strong
            JOptionPane.showMessageDialog(this, "Your password is strong. Thank you, " + username + "!");
        } else if (strength.equals("Medium")) {
            strengthLabel.setForeground(Color.ORANGE);
        } else {
            strengthLabel.setForeground(Color.RED);
        }
    }

    private String calculatePasswordStrength(String password) {
        int length = password.length();
        boolean hasUppercase = false;
        boolean hasLowercase = false;
        boolean hasDigit = false;
        boolean hasSpecialCharacter = false;

        for (int i = 0; i < length; i++) {
            char c = password.charAt(i);
            if (Character.isUpperCase(c)) {
                hasUppercase = true;
            } else if (Character.isLowerCase(c)) {
                hasLowercase = true;
            } else if (Character.isDigit(c)) {
                hasDigit = true;
            } else {
                hasSpecialCharacter = true;
            }
        }

        if (length >= 8 && hasUppercase && hasLowercase && hasDigit && hasSpecialCharacter) {
            return "Strong";
        } else if (length >= 6 && hasUppercase && hasLowercase && (hasDigit || hasSpecialCharacter)) {
            return "Medium";
        } else {
            return "Weak";
        }
    }

    private String getPasswordInfo(String password) {
        StringBuilder info = new StringBuilder("Password should contain: ");
        if (!password.isEmpty()) {
            if (!password.matches(".*[A-Z].*")) {
                info.append("Uppercase letters, ");
            }
            if (!password.matches(".*[a-z].*")) {
                info.append("Lowercase letters, ");
            }
            if (!password.matches(".*\\d.*")) {
                info.append("Digits, ");
            }
            if (!password.matches(".*[^A-Za-z0-9].*")) {
                info.append("Special characters, ");
            }

            if (info.toString().endsWith(", ")) {
                info.setLength(info.length() - 2); // Remove the trailing ", "
            } else {
                info.setLength(info.length() - 1); // Remove the trailing " "
            }
        }

        return info.toString();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new PasswordStrengthIndicator();
            }
        });
    }
}
