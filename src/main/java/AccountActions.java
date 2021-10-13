import java.sql.*;

public class AccountActions {

    private static Connection connection = null;
    private static Statement statement = null;
    private static PreparedStatement preparedStmt = null;
    private static ResultSet result = null;

    public static void connect() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank_db", "root", "root");
    }

    public static int createCheckingAccount(String firstName, String lastName, String email,
                                            String username, String password) throws SQLException {
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

    public static int createSavingsAccount(String firstName, String lastName, String email,
                                           String username, String password) throws SQLException {
        int insertStatus = 0;
        String insertQuery = "INSERT INTO savings_account (`first_name`,`last_name`, `email`, `username`, `password`" +
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

    public static String login(String username, String password) throws Exception
    {
        statement = connection.createStatement();
        String loginCredentials = username + password;
        String accountType = "";
        String checkingAccountUsername = "SELECT username, password FROM checking_account WHERE username = '" + username + "' AND password = '" + password + "'";
        String savingsAccountUsername = "SELECT username, password FROM savings_account WHERE username = '" + username + "' AND password = '" + password + "'";
        result = statement.executeQuery(checkingAccountUsername);
        while(result.next())
        {
            if((result.getString("username") + result.getString("password")).equals(loginCredentials)) {
                return accountType = "checking_account";
            }
        }

        result = statement.executeQuery(savingsAccountUsername);
        while(result.next())
        {
            if((result.getString("username") + result.getString("password")).equals(loginCredentials)) {
                return accountType = "savings_account";
            }
        }

        return accountType;
    }

    public static double viewBalance(String username, String password) throws Exception {
        double balance = 0;
        String account = "";
        if (login(username, password).equals("checking_account"))
        {
            account = "checking_account";
        }
        if (login(username, password).equals("savings_account"))
        {
            account = "savings_account";
        }
        String retrieveBalance = "SELECT balance FROM " + account + " WHERE username = '" +
                username + "' AND password = '"  + password + "'";
        statement = connection.createStatement();
        result = statement.executeQuery(retrieveBalance);
        while (result.next()) {
            balance = result.getDouble("balance");
        }
        return balance;
    }

    public static void makeDeposit(double deposit, String username, String password) throws Exception {
        String account = "";
        if (login(username, password).equals("checking_account"))
        {
            account = "checking_account";
        }
        if (login(username, password).equals("savings_account"))
        {
            account = "savings_account";
        }
        int id = AccountActions.findId(username, password);
        String makeDeposit = "UPDATE " + account + " SET balance = balance + '" + deposit +
                "' WHERE username = '" + username + "' AND `id` = '" + id + "'";

        //String transactionRecord = "INSERT INTO checking_account_transaction ("
        statement = connection.createStatement();
        if(login(username, password).equals(account)) {
            statement.executeUpdate(makeDeposit);
        }

    }

    public static void makeWithdrawal(double withdrawal, String username, String password) throws Exception {
        int id = AccountActions.findId(username, password);
        String account = "";
        if (login(username, password).equals("checking_account"))
        {
            account = "checking_account";
        }
        if (login(username, password).equals("savings_account"))
        {
            account = "savings_account";
        }
        String withdraw = "UPDATE " + account + " SET balance = balance - '" + withdrawal +
                "' WHERE username = '" + username + "' AND `id` = '" + id + "'"; // creating a query
        statement = connection.createStatement();
        if(login(username, password).equals(account)) {
            statement.executeUpdate(withdraw);
        }
    }

    public static boolean verifyUsername(String username) throws SQLException {
        boolean usernameIsUnique = true;
        String checkingUsername = "SELECT `username` FROM checking_account WHERE `username` = '" + username + "'";
        String savingsUsername = "SELECT `username` FROM savings_account WHERE `username` = '" + username + "'";

        statement = connection.createStatement();
        result = statement.executeQuery(checkingUsername);
        while(result.next())
        {
            if (result.getString("username").equals(username)) {
                usernameIsUnique = false;
            }
        }
        result = statement.executeQuery(savingsUsername);
        while (result.next())
        {
            if (result.getString("username").equals(username))
            {
                usernameIsUnique = false;
            }
        }
        return usernameIsUnique;
    }

    public static void viewTransactionHistory()
    {
        String viewQuery = "SELECT ";
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

    public static int findId(String username, String password) throws Exception {
        String account = "";
        if (login(username, password).equals("checking_account"))
        {
            account = "checking_account";
        }
        if (login(username, password).equals("savings_account"))
        {
            account = "savings_account";
        }
        String query = "select id from " + account +  " where username = '" + username + "'";
        int id = 0;
        statement = connection.createStatement();
        // 4) Storing & Processing the Result (ResultSet[I])
        result = statement.executeQuery(query);
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

