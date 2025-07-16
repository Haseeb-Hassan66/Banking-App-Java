package view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import user.UserDetails;

public class DashboardScreen extends JFrame {
    String username;

    public DashboardScreen(String username){

        this.username = username;
        String fullname = UserDetails.getFullname(username);
        int user_id = UserDetails.getUserID(username);
        int account_id = UserDetails.getaccID(user_id);
        double balance = UserDetails.getBalance(account_id);
        String creationTime = UserDetails.getCreationTime(account_id);

        //Main Frame
        setTitle("Main Menu");
        setSize(700, 600);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Labels
        JLabel heading = new JLabel("Welcome " + fullname + "!");
        heading.setBounds(145, 80, 400, 60);
        heading.setFont(new Font("Arial", Font.BOLD, 26));
        heading.setHorizontalAlignment(SwingConstants.CENTER);
        heading.setForeground(Color.BLUE);
        add(heading);

        //Buttons
        JButton balanceButton = new JButton("Account information");
        balanceButton.setBounds(215, 190, 250, 40);
        balanceButton.setBackground(Color.BLUE);
        balanceButton.setForeground(Color.WHITE);
        add(balanceButton);
        JButton DorWButton = new JButton("Transaction");
        DorWButton.setBounds(215, 260, 250, 40);
        DorWButton.setBackground(Color.BLUE);
        DorWButton.setForeground(Color.WHITE);
        add(DorWButton);
        JButton transactionHButton = new JButton("View transaction history");
        transactionHButton.setBounds(215, 330, 250, 40);
        transactionHButton.setBackground(Color.BLUE);
        transactionHButton.setForeground(Color.WHITE);
        add(transactionHButton);
        JButton logoutButton = new JButton("Logout");
        logoutButton.setBounds(215, 400, 250, 40);
        logoutButton.setBackground(Color.BLUE);
        logoutButton.setForeground(Color.WHITE);
        add(logoutButton);

        balanceButton.addActionListener(new eventHandler());
        DorWButton.addActionListener(new eventHandler());
        transactionHButton.addActionListener(new eventHandler());
        logoutButton.addActionListener(new eventHandler());
        setVisible(true);
    }

    class eventHandler implements ActionListener{
        public void actionPerformed(ActionEvent c){

            String op = ((JButton)(c.getSource())).getText();

            if(op.equalsIgnoreCase("logout")){
                JOptionPane.showMessageDialog(null, "Loging out.");
                dispose(); // Close dashboard window
                new LoginScreen();
                // Redirect to login
            } else if (op.equalsIgnoreCase("Account information")){

                int user_id = UserDetails.getUserID(username);
                int account_id = UserDetails.getaccID(user_id);
                double balance = UserDetails.getBalance(account_id);
                String creationTime = UserDetails.getCreationTime(account_id);

                JFrame accInfoFrame = new JFrame("Account Information");
                accInfoFrame.setSize(400, 300);
                accInfoFrame.setLayout(null);
                accInfoFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                JLabel accNumber = new JLabel("Account Number: " + account_id);
                accNumber.setBounds(60, 30, 320, 50);
                accInfoFrame.add(accNumber);

                JLabel balanceLabel = new JLabel("Balance: " + balance + " Rupees.");
                balanceLabel.setBounds(60, 70, 320, 50);
                accInfoFrame.add(balanceLabel);

                JLabel creationLabel = new JLabel("Account Created On: " + creationTime);
                creationLabel.setBounds(60, 110, 320, 50);
                accInfoFrame.add(creationLabel);

                accInfoFrame.setVisible(true);
            }else if(op.equalsIgnoreCase("View transaction history")){

                int user_id = UserDetails.getUserID(username);
                int account_id = UserDetails.getaccID(user_id);
                String[][] transactions = UserDetails.getLastThreeTransactions(account_id);

                JFrame txnFrame = new JFrame("Last 3 Transactions");
                txnFrame.setSize(400, 300);
                txnFrame.setLayout(null);
                txnFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                int y = 30;
                for (int i = 0; i < 3; i++) {
                    if (transactions[i][0] == null) break; // no more data
                    String line = (i + 1) + ". Type: " + transactions[i][0] + " | Amount: " + transactions[i][1] + " | Date: " + transactions[i][2];

                    JLabel txnLabel = new JLabel(line);
                    txnLabel.setBounds(30, y, 340, 30);
                    txnFrame.add(txnLabel);
                    y += 40;
                }

                txnFrame.setVisible(true);
            }else if (op.equalsIgnoreCase("Transaction")) {
                int user_id = UserDetails.getUserID(username);
                int account_id = UserDetails.getaccID(user_id);

                JFrame txnTypeFrame = new JFrame("Select Transaction Type");
                txnTypeFrame.setSize(350, 200);
                txnTypeFrame.setLayout(null);
                txnTypeFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                JButton depositButton = new JButton("Deposit");
                depositButton.setBounds(50, 50, 100, 40);
                txnTypeFrame.add(depositButton);

                JButton withdrawButton = new JButton("Withdraw");
                withdrawButton.setBounds(180, 50, 100, 40);
                txnTypeFrame.add(withdrawButton);

                depositButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        txnTypeFrame.dispose();
                        showTransactionForm(account_id, "Deposit");
                    }
                });


                withdrawButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        txnTypeFrame.dispose();
                        showTransactionForm(account_id, "Withdraw");
                    }
                });

                txnTypeFrame.setVisible(true);
            }

        }
    }

    public void showTransactionForm(int account_id, String type) {
        JFrame txnFrame = new JFrame(type + " Amount");
        txnFrame.setSize(350, 200);
        txnFrame.setLayout(null);
        txnFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel label = new JLabel("Enter amount to " + type + ":");
        label.setBounds(50, 30, 250, 30);
        txnFrame.add(label);

        JTextField amountField = new JTextField();
        amountField.setBounds(50, 70, 220, 30);
        txnFrame.add(amountField);

        JButton processBtn = new JButton("Process");
        processBtn.setBounds(100, 120, 100, 30);
        txnFrame.add(processBtn);

        processBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    double amount = Double.parseDouble(amountField.getText());
                    if (amount <= 0) {
                        JOptionPane.showMessageDialog(null, "Amount must be greater than 0.");
                        return;
                    }

                    boolean success = UserDetails.processTransaction(account_id, type, amount);
                    if (success) {
                        JOptionPane.showMessageDialog(null, type + " successful!");
                        txnFrame.dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "Transaction failed. Insufficient balance!");
                    }

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid amount.");
                }
            }
        });

        txnFrame.setVisible(true);
    }

}