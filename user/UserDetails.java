package user;

import java.sql.*;
import model.DBConnection;

public class UserDetails {
    public static void main(String[] args) {
        new UserDetails();
    }

    public static String getFullname (String username){
            try{
                Connection con = DBConnection.getConnection();
                String query = "SELECT * FROM users WHERE username = ?";
                PreparedStatement pst = con.prepareStatement(query);
                pst.setString(1, username);
                ResultSet rs = pst.executeQuery();
                
                if (rs.next()) {
                    String fullname = rs.getString("full_name");
                    return fullname;
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
            return username;
        }

    public static int getUserID (String username){
            try{
                Connection con = DBConnection.getConnection();
                String query = "SELECT * FROM users WHERE username = ?";
                PreparedStatement pst = con.prepareStatement(query);
                pst.setString(1, username);
                ResultSet rs = pst.executeQuery();
                
                if (rs.next()) {
                    int user_id = rs.getInt("user_id");
                    return user_id;
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
            return 0;
        }

    public static int getaccID(int user_id){
        try {
                Connection con = DBConnection.getConnection();
                String query = "SELECT * FROM accounts WHERE user_id = ?";
                PreparedStatement pst = con.prepareStatement(query);
                pst.setInt(1, user_id);
                ResultSet rs = pst.executeQuery();
                
                if (rs.next()){
                    int account_id = rs.getInt("account_id");
                    return account_id;
                }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static int getBalance(int account_id){
        try {
            Connection con = DBConnection.getConnection();
            String query = "SELECT * FROM accounts WHERE account_id = ?";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, account_id);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                return rs.getInt("balance");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }


    public static String getCreationTime(int account_id){
        try {
            Connection con = DBConnection.getConnection();
            String query = "SELECT * FROM accounts WHERE account_id = ?";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, account_id);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                return rs.getString("created_at");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Unknown";
    }

    public static String[][] getLastThreeTransactions(int account_id) {
        String[][] transactions = new String[3][3]; // max 3 rows: [type, amount, date]
        try {
            Connection con = DBConnection.getConnection();
            String query = "SELECT type, amount, date FROM transactions WHERE account_id = ? ORDER BY date DESC LIMIT 3";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, account_id);
            ResultSet rs = pst.executeQuery();

            int i = 0;
            while (rs.next() && i < 3) {
                transactions[i][0] = rs.getString("type");
                transactions[i][1] = rs.getString("amount");
                transactions[i][2] = rs.getString("date");
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return transactions;
    }

    public static boolean processTransaction(int account_id, String type, double amount) {
        try {
            Connection con = DBConnection.getConnection();

            // Check balance for withdraw
            if (type.equalsIgnoreCase("Withdraw")) {
                String checkQuery = "SELECT balance FROM accounts WHERE account_id = ?";
                PreparedStatement checkStmt = con.prepareStatement(checkQuery);
                checkStmt.setInt(1, account_id);
                ResultSet rs = checkStmt.executeQuery();
                if (rs.next()) {
                    double currentBalance = rs.getDouble("balance");
                    if (amount > currentBalance) {
                        return false; // Not enough balance
                    }
                }
            }

            // Update balance
            String updateQuery = type.equalsIgnoreCase("Deposit") ?
                "UPDATE accounts SET balance = balance + ? WHERE account_id = ?" :
                "UPDATE accounts SET balance = balance - ? WHERE account_id = ?";
            PreparedStatement updateStmt = con.prepareStatement(updateQuery);
            updateStmt.setDouble(1, amount);
            updateStmt.setInt(2, account_id);
            updateStmt.executeUpdate();

            // Insert transaction
            String insertQuery = "INSERT INTO transactions (account_id, type, amount) VALUES (?, ?, ?)";
            PreparedStatement insertStmt = con.prepareStatement(insertQuery);
            insertStmt.setInt(1, account_id);
            insertStmt.setString(2, type);
            insertStmt.setDouble(3, amount);
            insertStmt.executeUpdate();

            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}