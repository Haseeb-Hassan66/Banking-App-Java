package GUI;
import java.awt.*;
import javax.swing.*;
public class LoginScreen extends JFrame{
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton signupButton;

    public LoginScreen(){
        setTitle("Login - MyBank");
        setSize(700, 600);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel heading = new JLabel("Login to your account", JLabel.CENTER);
        heading.setBounds(145, 80, 400, 60);
        heading.setFont(new Font("Arial", Font.BOLD, 26));
        add(heading);

        setVisible(true);
    }
}
