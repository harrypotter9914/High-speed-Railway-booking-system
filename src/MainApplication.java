import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class MainApplication {
    private static JFrame mainFrame;
    private static UserService userService = new UserService(); // 假设 UserService 已实现

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainApplication::createAndShowLoginGUI);
    }

    private static void createAndShowLoginGUI() {
        // 初始化登录界面
        mainFrame = new JFrame("Main Application");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(300, 200);
        mainFrame.setLayout(new GridLayout(3, 2));

        // 添加登录表单元素
        mainFrame.add(new JLabel("Username:"));
        JTextField usernameField = new JTextField();
        mainFrame.add(usernameField);

        mainFrame.add(new JLabel("Password:"));
        JPasswordField passwordField = new JPasswordField();
        mainFrame.add(passwordField);

        JButton loginButton = new JButton("Login");
        mainFrame.add(loginButton);

        JButton registerButton = new JButton("Register");
        mainFrame.add(registerButton);

        // 设置按钮动作
        loginButton.addActionListener(e -> performLogin(usernameField.getText(), new String(passwordField.getPassword())));
        registerButton.addActionListener(e -> performRegister(usernameField.getText(), new String(passwordField.getPassword())));

        mainFrame.setVisible(true);
    }

    private static void performLogin(String username, String password) {
        // 登录逻辑
        try {
            if (userService.authenticateUser(username, password)) {
                // 登录成功
                showManagementOptions();
            } else {
                JOptionPane.showMessageDialog(mainFrame, "Invalid credentials.", "Login Failed", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void performRegister(String username, String password) {
        // 注册逻辑
        try {
            if (userService.createUser(username, password)) {
                JOptionPane.showMessageDialog(mainFrame, "Registration successful.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(mainFrame, "Username already exists.", "Registration Failed", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void showManagementOptions() {
        // 清除登录界面
        mainFrame.getContentPane().removeAll();
        mainFrame.setLayout(new GridLayout(4, 1));

        JButton ticketManagementButton = new JButton("Ticket Management");
        JButton passengerManagementButton = new JButton("Passenger Management");
        JButton hswManagementButton = new JButton("HSW Management");
        JButton logoutButton = new JButton("Logout");

        ticketManagementButton.addActionListener(e -> TicketManagementGUI.createAndShowGUI());
        passengerManagementButton.addActionListener(e -> PassengerManagementGUI.createAndShowGUI());
        hswManagementButton.addActionListener(e -> HSWManagementGUI.createAndShowGUI());
        logoutButton.addActionListener(e -> mainFrame.dispose());

        mainFrame.add(ticketManagementButton);
        mainFrame.add(passengerManagementButton);
        mainFrame.add(hswManagementButton);
        mainFrame.add(logoutButton);

        mainFrame.revalidate();
        mainFrame.repaint();
    }
}
