package hangman;

import org.apache.commons.codec.digest.DigestUtils;

import java.sql.*;
import java.util.*;

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
    public static String randomWord() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:src/main/resources/hangman/database.db");
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM words");

        Random rand = new Random();
        int random = rand.nextInt(50);

        while (resultSet.next()){
            if (resultSet.getInt("index") == random) {
                String word = resultSet.getString("word");
                connection.close();
                return word;
            }
        }

        connection.close();
        return "";
    }

    public static void saveGame(String username, String word, int wrongGuesses, int time, int win) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:src/main/resources/hangman/database.db");
        String sql = "INSERT INTO GameInfo (GameID, Username, Word, WrongGuesses, Time, Win) VALUES (?, ?, ?, ?, ?, ?)";

        PreparedStatement preparedStmt = connection.prepareStatement(sql);
        preparedStmt.setString (2, username);
        preparedStmt.setString (3, word);
        preparedStmt.setInt (4, wrongGuesses);
        preparedStmt.setInt (5, time);
        preparedStmt.setInt (6, win);
        preparedStmt.execute();
        connection.close();
    }

    private static void bubbleSort(List<String> users, List<Integer> wins, int n)
    {
        int i, j, temp;
        String tempStr;
        boolean swapped;
        for (i = 0; i < n - 1; i++) {
            swapped = false;
            for (j = 0; j < n - i - 1; j++) {
                if (wins.get(j) < wins.get(j+1)) {

                    // Swap arr[j] and arr[j+1]
                    temp = wins.get(j);
                    tempStr = users.get(j);
                    wins.set(j, wins.get(j+1));
                    users.set(j, users.get(j+1));
                    wins.set(j+1, temp);
                    users.set(j+1, tempStr);
                    swapped = true;
                }
            }

            // If no two elements were
            // swapped by inner loop, then break
            if (swapped == false)
                break;
        }
    }

    public static List<String> leaderboard() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:src/main/resources/hangman/database.db");
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM UserInfo");

        List<String> users = new ArrayList<>();
        List<Integer> wins = new ArrayList<>();

        while (resultSet.next()){
            users.add(resultSet.getString("Username"));
            wins.add(0);
        }

        connection.close();

        Connection connection2 = DriverManager.getConnection("jdbc:sqlite:src/main/resources/hangman/database.db");
        Statement statement2 = connection2.createStatement();
        ResultSet resultSet2 = statement2.executeQuery("SELECT * FROM GameInfo");

        while (resultSet2.next()){
            for (int i = 0; i < users.size(); i++){
                if (resultSet2.getString("Username").equals(users.get(i))){
                    int X = wins.get(i) + 1;
                    wins.set(i, X);
                }
            }
        }

        bubbleSort(users, wins, users.size());

        List<String> records = new ArrayList<>();
        for (int i = 0; i < users.size(); i++) {
            records.add(users.get(i) + ": " + wins.get(i));
        }
        connection2.close();
        return records;
    }

    public static List<String> myGames(String username) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:src/main/resources/hangman/database.db");
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM GameInfo");

        List<String> records = new ArrayList<>();

        while (resultSet.next()){
            if (resultSet.getString("Username").equals(username)) {
                int win = resultSet.getInt("Win");
                boolean winBool;
                if (win == 1){
                    winBool = true;
                }
                else {
                    winBool = false;
                }
                records.add(resultSet.getString("Word: " + resultSet.getString("Word") + "      " +
                                                            "WrongGuesses: " + resultSet.getInt("WrongGuesses") + "      " +
                                                            "Time: " + resultSet.getInt("Time") + "     " +
                                                            "Win: " + Boolean.toString(winBool)));
            }
        }
        connection.close();
        return records;
    }
}