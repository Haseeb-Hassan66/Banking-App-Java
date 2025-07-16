package controller;
import java.sql.*;
import model.DBConnection;

public class AuthController{
    public static void main(String[] args) {
        new AuthController();
    }

    public static boolean login(String username, String password){

        try{
            Connection con = DBConnection.getConnection();
            String query = "SELECT * FROM users WHERE username = ?";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, username);
            ResultSet rs = pst.executeQuery();
            
            if (rs.next()) {

                String dbPassword = rs.getString("password");

                if(dbPassword.equals(password)){
                    return true;
                }
            } else {
                return false;
            }
        }catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public static boolean signup(String fullname, String username, String password) {
        try {
            Connection con2 = DBConnection.getConnection();
            // Check if username already exists
            String checkQuery = "SELECT * FROM users WHERE username = ?";
            
            try (PreparedStatement checkStmt = con2.prepareStatement(checkQuery)) {
                checkStmt.setString(1, username);
                ResultSet rs = checkStmt.executeQuery();
                if (rs.next()) {
                    return false; // if Username already exists
                }
            }

            // Insert user
            String insertUserQuery = "INSERT INTO users (username, password, full_name) VALUES (?, ?, ?)";
            try (PreparedStatement pst = con2.prepareStatement(insertUserQuery, Statement.RETURN_GENERATED_KEYS)) {
                pst.setString(1, username);
                pst.setString(2, password);
                pst.setString(3, fullname);

                int rowsAffected = pst.executeUpdate();
                if (rowsAffected > 0) {
                    ResultSet generatedKeys = pst.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        int userId = generatedKeys.getInt(1);

                        // Create account with initial balance
                        String insertAccount = "INSERT INTO accounts (user_id, balance) VALUES (?, ?)";
                        try (PreparedStatement accountStmt = con2.prepareStatement(insertAccount)) {
                            accountStmt.setInt(1, userId);
                            accountStmt.setDouble(2, 0.00);
                            accountStmt.executeUpdate();
                        }
                    }
                    return true; // Signup success
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException ex) {
        }

        return false; // Signup failed
    }
}