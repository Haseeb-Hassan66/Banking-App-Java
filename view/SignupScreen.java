package view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import controller.AuthController;

public class SignupScreen extends JFrame{
    private JTextField FullnameField;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField passwordField2;

    public SignupScreen(){

        //Main Frame
        setTitle("Create account");
        setSize(700, 600);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Labels
        JLabel heading = new JLabel("Create your account");
        heading.setBounds(145, 80, 400, 60); // x, y, width, height
        heading.setFont(new Font("Arial", Font.BOLD, 26)); // Big bold font
        heading.setHorizontalAlignment(SwingConstants.CENTER);
        heading.setForeground(Color.BLUE);
        add(heading);
        JLabel L1 = new JLabel("Full name:");
        L1.setBounds(215, 150, 300, 50);
        add(L1);
        JLabel L2 = new JLabel("Username:");
        L2.setBounds(215, 220, 300, 50);
        add(L2);
        JLabel L3 = new JLabel("Password:");
        L3.setBounds(215, 290, 300, 50);
        add(L3);
        JLabel L4 = new JLabel("Verify Password:");
        L4.setBounds(215, 360, 300, 50);
        add(L4);

        //Text fields
        FullnameField = new JTextField(15);
        FullnameField.setBounds(215, 190, 250, 40);
        FullnameField.setFont(new Font("Arial", Font.PLAIN, 13));
        add(FullnameField);

        usernameField = new JTextField(15);
        usernameField.setBounds(215, 260, 250, 40);
        usernameField.setFont(new Font("Arial", Font.PLAIN, 13));
        add(usernameField);

        passwordField = new JPasswordField(15);
        passwordField.setBounds(215, 330, 250, 40);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 13));
        add(passwordField);

        passwordField2 = new JPasswordField(15);
        passwordField2.setBounds(215, 400, 250, 40);
        passwordField2.setFont(new Font("Arial", Font.PLAIN, 13));
        add(passwordField2);

        //Button
        JButton createButton = new JButton("Create account");
        createButton.setBounds(215, 470, 250, 40);
        createButton.setFont(new Font("Arial", Font.PLAIN, 13));
        createButton.setBackground(Color.BLUE);
        createButton.setForeground(Color.WHITE);
        add(createButton);
        createButton.addActionListener(new createHandler());

        setVisible(true);
    }

    //(inner class) for Signup functionality 
    class createHandler implements  ActionListener{
        public void actionPerformed(ActionEvent b){
            String full_name = FullnameField.getText().trim();
            String username = usernameField.getText().trim();
            String p1 = new String(passwordField.getPassword());
            String p2 = new String(passwordField2.getPassword());
            String password;

            if (full_name.isEmpty() || username.isEmpty() || p1.isEmpty() || p2.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill in all fields.");
                return;
            } else if (!p1.equals(p2)) {
                JOptionPane.showMessageDialog(null, "Passwords do not match.");
                return;
            } else {
                password = p1;
                boolean success = AuthController.signup(full_name,username,password);
                if(success){
                    JOptionPane.showMessageDialog(null, "Account Created!");
                    dispose();
                    new LoginScreen();
                }
            }
        }
    }
}