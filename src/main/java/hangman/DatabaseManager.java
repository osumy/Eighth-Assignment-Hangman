package hangman;

import org.apache.commons.codec.digest.DigestUtils;

import java.sql.*;

public class DatabaseManager {
    public static void SignUp(String name, String username, String password) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:src/main/resources/hangman/database.db");
        String sql = "INSERT INTO UserInfo (Name, Username, Password) VALUES (?, ?, ?)";

        PreparedStatement preparedStmt = connection.prepareStatement(sql);
        preparedStmt.setString (1, name);
        preparedStmt.setString (2, username);
        preparedStmt.setString (3, password);
        preparedStmt.execute();
        connection.close();
    }
    public static Account login(String username) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:src/main/resources/hangman/database.db");
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM UserInfo");

        while (resultSet.next()){
            if (resultSet.getString("Username").equals(username)){
                String name = resultSet.getString("Name");
                String password = resultSet.getString("Password");
                connection.close();
                return new Account(username, password, name);
            }
        }

        connection.close();
        return null;
    }
    public static boolean usernameIsUnique(String username) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:src/main/resources/hangman/database.db");
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM UserInfo");

        while (resultSet.next()){
            if (resultSet.getString("Username").equals(username)) {
                connection.close();
                return false;
            }
        }

        connection.close();
        return true;
    }
    public static boolean passMatch(String password) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:src/main/resources/hangman/database.db");
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM UserInfo");

        while (resultSet.next()){
            if (resultSet.getString("Password").equals(password)) {
                connection.close();
                return true;
            }
        }

        connection.close();
        return false;
    }
}