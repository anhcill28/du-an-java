import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserManagementApp extends JFrame {
    private final LoginService loginService;
    private final UserActionService userActionService;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField emailField;
    private JComboBox<String> roleComboBox;
    private JButton logoutButton;
    private JButton deleteUserButton;
    private UserData loggedInUser;

    public UserManagementApp() {
        loginService = new LoginService();
        userActionService = new UserActionService();

        setTitle("User Management System");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Title Panel
        JPanel titlePanel = new JPanel();
        titlePanel.add(new JLabel("User Management System"));
        titlePanel.setBackground(Color.LIGHT_GRAY);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(titlePanel, BorderLayout.NORTH);

        // Main Panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(6, 2, 10, 10));
        add(mainPanel, BorderLayout.CENTER);

        // Username
        mainPanel.add(new JLabel("Username:"));
        usernameField = new JTextField();
        mainPanel.add(usernameField);

        // Password
        mainPanel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        mainPanel.add(passwordField);

        // Email
        mainPanel.add(new JLabel("Email:"));
        emailField = new JTextField();
        mainPanel.add(emailField);

        // Role Selection
        mainPanel.add(new JLabel("Role:"));
        roleComboBox = new JComboBox<>(new String[]{"Admin", "Moderator", "Regular User"});
        mainPanel.add(roleComboBox);

        // Button Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        add(buttonPanel, BorderLayout.SOUTH);

        // Login Button
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new LoginButtonAction());
        buttonPanel.add(loginButton);

        // Register Button
        JButton registerButton = new JButton("Register");
        registerButton.addActionListener(new RegisterButtonAction());
        buttonPanel.add(registerButton);

        // Logout Button (initially disabled)
        logoutButton = new JButton("Logout");
        logoutButton.addActionListener(new LogoutButtonAction());
        logoutButton.setEnabled(false);
        buttonPanel.add(logoutButton);

        // Delete User Button (initially disabled)
        deleteUserButton = new JButton("Delete User");
        deleteUserButton.addActionListener(new DeleteUserButtonAction());
        deleteUserButton.setEnabled(false);
        buttonPanel.add(deleteUserButton);

        // Add padding
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Change background color
        getContentPane().setBackground(Color.WHITE);
    }

    private class LoginButtonAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            try {
                loggedInUser = loginService.login(username, password);
                JOptionPane.showMessageDialog(null, "Login successful as " + loggedInUser.username());
                userActionService.handleUserAccess(loggedInUser);
                showUserInfo(); // Hiển thị thông tin người dùng
                toggleUserManagementButtons(true);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private class RegisterButtonAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = usernameField.getText();
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());
            String selectedRole = (String) roleComboBox.getSelectedItem();

            User userType;
            switch (selectedRole) {
                case "Admin" -> userType = new Admin(username, email, password);
                case "Moderator" -> userType = new Moderator(username, email, password);
                case "Regular User" -> userType = new RegularUser(username, email, password);
                default -> throw new IllegalArgumentException("Invalid role selection");
            }

            try {
                loginService.register(username, email, password, userType);
                JOptionPane.showMessageDialog(null, "User " + username + " registered successfully.");
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private class LogoutButtonAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            loggedInUser = null;
            JOptionPane.showMessageDialog(null, "Logged out successfully.");
            toggleUserManagementButtons(false);
        }
    }

    private class DeleteUserButtonAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (loggedInUser.userType() instanceof Admin || loggedInUser.userType() instanceof Moderator) {
                String usernameToDelete = JOptionPane.showInputDialog("Enter username to delete:");
                if (usernameToDelete != null) {
                    try {
                        loginService.deleteUser(usernameToDelete);
                        JOptionPane.showMessageDialog(null, "User " + usernameToDelete + " deleted successfully.");
                    } catch (IllegalArgumentException ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "You do not have permission to delete users.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void toggleUserManagementButtons(boolean isLoggedIn) {
        logoutButton.setEnabled(isLoggedIn);
        deleteUserButton.setEnabled(isLoggedIn && (loggedInUser.userType() instanceof Admin || loggedInUser.userType() instanceof Moderator));
    }

    private void showUserInfo() {
        String userInfo = "User Info:\n" +
                "Username: " + loggedInUser.username() + "\n" +
                "Email: " + loggedInUser.email() + "\n" +
                "Role: " + loggedInUser.userType().getUserRole();
        JOptionPane.showMessageDialog(null, userInfo, "User Info", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            UserManagementApp app = new UserManagementApp();
            app.setVisible(true);
        });
    }
}
