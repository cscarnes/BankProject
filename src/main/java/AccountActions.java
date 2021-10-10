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

    public static void viewBalance(String username, String password) throws Exception {

        String retrieveBalance = "SELECT `balance` FROM checking_account WHERE username=" + username + " AND password ="  + password;
        //String query = "select * from employee where id=" + id;
        statement = connection.createStatement();
        // 4) Storing & Processing the Result (ResultSet[I])
        result = statement.executeQuery(retrieveBalance);
        System.out.println("ID \t Name \t\t Email");
        while (result.next()) {
            System.out.println(result.getInt("id") + "\t" + result.getString(2) + "\t \t" + result.getString(3));
        }
    }

    public static int makeDeposit(double deposit) throws Exception {

        String updateQuery = "UPDATE `employee` SET `name` = ?,`email` = ? WHERE `id` = ?;"; // creating a query
        preparedStmt = connection.prepareStatement(updateQuery); // creating prepared Statement
        preparedStmt.setInt(3, 101);
        preparedStmt.setString(1, "abc123");
        preparedStmt.setString(2, "abc123@gmail.com");

        int updateStatus = 0;
        updateStatus = preparedStmt.executeUpdate();
        return updateStatus;
    }

    public static int makeWithdrawal(double withdrawal) throws Exception {

        String updateQuery = "UPDATE `employee` SET `name` = ?,`email` = ? WHERE `id` = ?;"; // creating a query
        preparedStmt = connection.prepareStatement(updateQuery); // creating prepared Statement
        preparedStmt.setInt(3, 101);
        preparedStmt.setString(1, "abc123");
        preparedStmt.setString(2, "abc123@gmail.com");

        int updateStatus = 0;
        updateStatus = preparedStmt.executeUpdate();
        return updateStatus;
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

    public static void findById(int id) throws Exception {
        String query = "select * from employee where id=" + id;
        statement = connection.createStatement();
        // 4) Storing & Processing the Result (ResultSet[I])
        result = statement.executeQuery(query);
        System.out.println("ID \t Name \t\t Email");
        while (result.next()) {
            System.out.println(result.getInt("id") + "\t" + result.getString(2) + "\t \t" + result.getString(3));
        }
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

