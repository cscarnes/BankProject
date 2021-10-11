import java.sql.*;

public class AccountActions {

    private static Connection connection = null;
    private static Statement statement = null;
    private static PreparedStatement preparedStmt = null;
    private static ResultSet result = null;

    public static void connect() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank_db", "root", "root");
    }

    public static int createCheckingAccount(String firstName, String lastName, String email, String username, String password) throws Exception {
        int insertStatus = 0;
        String insertQuery = "INSERT INTO checking_account (`first_name`,`last_name`, `email`, `username`, `password`" +
                ") VALUES (?,?,?,?,?);";
        preparedStmt = connection.prepareStatement(insertQuery);

        preparedStmt.setString(1, firstName);
        preparedStmt.setString(2, lastName);
        preparedStmt.setString(3, email);
        preparedStmt.setString(4, username);
        preparedStmt.setString(5, password);
        insertStatus = preparedStmt.executeUpdate();
        return insertStatus;
    }

    public static int createSavingsAccount(String firstName, String lastName, String email, String username, String password) throws Exception {
        int insertStatus = 0;
        String insertQuery = "INSERT INTO savings_account (`first_name`,`last_name`, `email`, `user_name`, `password`" +
                ") VALUES (?,?,?,?,?);";
        preparedStmt = connection.prepareStatement(insertQuery);

        preparedStmt.setString(1, firstName);
        preparedStmt.setString(2, lastName);
        preparedStmt.setString(3, email);
        preparedStmt.setString(4, username);
        preparedStmt.setString(5, password);
        insertStatus = preparedStmt.executeUpdate();
        return insertStatus;
    }

    public static int login(String username, String password) throws SQLException
    {

        int loginStatus = 0;
        String comparer = username + password;
        String retrievedUsername;
        //String retrievedPassword;

        retrievedUsername = "SELECT username, password FROM checking_account WHERE username = '" + username + "' AND password = '" + password + "'";
        statement = connection.createStatement();
        result = statement.executeQuery(retrievedUsername);
        //result1 = statement.executeQuery(retrievedPassword);
        while(result.next())
        {
            System.out.println(result.getString("username"));
            if((result.getString("username") + result.getString("password")).equals(comparer)) {
                loginStatus = 1;
            }
        }

        return loginStatus;
    }

    public static double viewBalance(String username, String password) throws Exception {
        double balance = 0;
        String retrieveBalance = "SELECT `balance` FROM checking_account WHERE username = '" +
                username + "' AND password = '"  + password + "'";
        statement = connection.createStatement();
        // 4) Storing & Processing the Result (ResultSet[I])
        result = statement.executeQuery(retrieveBalance);
        while (result.next()) {
            balance = result.getDouble("balance");
        }
        return balance;
    }

    public static int makeDeposit(double deposit, String username) throws Exception {
        int id = AccountActions.findId(username);
        String updateQuery = "UPDATE checking_account SET balance = balance + '" + deposit +
                "' WHERE username = '" + username + "' AND `id` = '" + id + "'"; // creating a query
        statement = connection.createStatement(); // creating prepared Statement
        int updateStatus = 0;
        updateStatus = statement.executeUpdate(updateQuery);
        return updateStatus;
    }

    public static int makeWithdrawal(double withdrawal, String username) throws Exception {
        int id = AccountActions.findId(username);
        String updateQuery = "UPDATE checking_account SET balance = balance - '" + withdrawal +
                "' WHERE username = '" + username + "' AND `id` = '" + id + "'"; // creating a query
        statement = connection.createStatement(); // creating prepared Statement
        int updateStatus = 0;
        updateStatus = statement.executeUpdate(updateQuery);
        return updateStatus;
    }

    public static void verifyUsername(String username) throws SQLException {
        String verifyQuery = "SELECT `username` FROM checking_account WHERE `username` = '" + username + "'";
        statement = connection.createStatement();
        result = statement.executeQuery(verifyQuery);
        while(result.next())
        {
            if (result.getString("username").equals(username)) {
                System.out.println("Username already in use");
            }
        }

    }

    public static int update(String username) throws Exception {

        String updateQuery = "UPDATE `employee` SET `name` = ?,`email` = ? WHERE `id` = ?;"; // creating a query
        preparedStmt = connection.prepareStatement(updateQuery); // creating prepared Statement
        preparedStmt.setInt(3, 101);
        preparedStmt.setString(1, "abc123");
        preparedStmt.setString(2, "abc123@gmail.com");

        int updateStatus = 0;
        updateStatus = preparedStmt.executeUpdate();
        return updateStatus;
    }

    public static int delete() throws Exception {
        String deleteQuery = "DELETE FROM `revature`.`employee` WHERE `id`=?;"; // creating a query
        preparedStmt = connection.prepareStatement(deleteQuery); // creating prepared Statement
        preparedStmt.setInt(1, 101);

        int deleteStatus = 0;
        deleteStatus = preparedStmt.executeUpdate();
        return deleteStatus;
    }

    public static void findAll() throws Exception {
        String query = "select * from employee";
        statement = connection.createStatement();
        // 4) Storing & Processing the Result (ResultSet[I])
        result = statement.executeQuery(query);
        System.out.println("ID \t Name \t\t Email");
        while (result.next()) {
            System.out.println(result.getInt("id") + "\t" + result.getString(2) + "\t \t" + result.getString(3));
        }
    }

    public static int findId(String username) throws Exception {
        String query = "select id from checking_account" +  " where username = '" + username + "'";
        int id = 0;
        statement = connection.createStatement();
        // 4) Storing & Processing the Result (ResultSet[I])
        result = statement.executeQuery(query);
       // while (result.next()) {
         //   return result.getInt("id");
        //}
        while(result.next()) {
            id = result.getInt("id");
        }
        return id;
    }

    public static void closeResource() throws Exception {
        if (result != null) {
            result.close();
        }
        if (preparedStmt != null) {
            preparedStmt.close();
        }
        if (statement != null) {
            statement.close();
        }
        if (connection != null) {
            connection.close();
        }

    }



        }

