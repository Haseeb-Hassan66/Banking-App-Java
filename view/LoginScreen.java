package view;
import controller.AuthController;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class LoginScreen extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton signupButton;

    public LoginScreen(){

        //Main Frame
        setTitle("Bank Login");
        setSize(700, 600);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Labels
        JLabel heading = new JLabel("Login to your account");
        heading.setBounds(145, 80, 400, 60); // x, y, width, height
        heading.setFont(new Font("Arial", Font.BOLD, 26)); // Big bold font
        heading.setHorizontalAlignment(SwingConstants.CENTER);
        heading.setForeground(Color.BLUE);
        add(heading);
        JLabel L1 = new JLabel("Username:");
        L1.setBounds(215, 150, 300, 50);
        add(L1);
        JLabel L2 = new JLabel("Password:");
        L2.setBounds(215, 220, 300, 50);
        add(L2);

        //Text fields
        usernameField = new JTextField(15);
        usernameField.setBounds(215, 190, 250, 40);
        usernameField.setFont(new Font("Arial", Font.PLAIN, 13));
        add(usernameField);
        passwordField = new JPasswordField(15);
        passwordField.setBounds(215, 260, 250, 40);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 13));
        add(passwordField);
        
        //Buttons
        loginButton = new JButton("Login");
        loginButton.setBounds(215, 330, 250, 40);
        loginButton.setFont(new Font("Arial", Font.PLAIN, 13));
        loginButton.setBackground(Color.BLUE);
        loginButton.setForeground(Color.WHITE);
        add(loginButton);

        signupButton = new JButton("Don't have an account?");
        signupButton.setBounds(215, 380, 250, 40);
        signupButton.setFont(new Font("Arial", Font.PLAIN, 12));
        signupButton.setForeground(Color.BLUE);
        signupButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        signupButton.setBorderPainted(false);
        signupButton.setContentAreaFilled(false);
        add(signupButton);

        loginButton.addActionListener(new loginHandler());
        signupButton.addActionListener(new SignupHandler());
        setVisible(true);
    }


    class loginHandler implements  ActionListener{

        public void actionPerformed(ActionEvent e){
            
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            
            if (username.isEmpty() && password.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter both username and password.");
            } else if (username.isEmpty()) { 
                JOptionPane.showMessageDialog(null, "Please enter your username.");
            } else if (password.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter your password.");
                return;
            } else{
                boolean success = AuthController.login(username, password);
                if (success) {
                    JOptionPane.showMessageDialog(null, "Login successful!");
                    dispose();
                    new DashboardScreen(username); // Opening dashboard here
                } else {
                    JOptionPane.showMessageDialog(null, "Login failed. Try again.");
                }
            }
        }
    }

    class SignupHandler implements ActionListener{
        public void actionPerformed(ActionEvent e){
            JOptionPane.showMessageDialog(null, "Redirecting to Signup page...");
            dispose();
            new SignupScreen();
        }
    }
    
}