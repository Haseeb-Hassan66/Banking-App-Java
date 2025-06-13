package GUI;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MainScreen extends JFrame{
    public MainScreen() {
        setTitle("MyBank");
        setSize(700, 600);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel heading = new JLabel("MyBank - Secure Banking App", JLabel.CENTER);
        heading.setBounds(100, 200, 500, 50);
        heading.setFont(new Font("Arial", Font.BOLD, 30));
        add(heading);

        JButton ProceedButton = new JButton("Proceed to Login page");
        ProceedButton.setBounds(215, 290, 250, 40);
        ProceedButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                dispose();
                new LoginScreen();
            }
        });
        add(ProceedButton);
        setVisible(true);
    }
}