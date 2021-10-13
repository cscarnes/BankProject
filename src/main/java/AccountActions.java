import java.sql.*;

public class AccountActions {

    private static Connection connection = null;
    private static Statement statement = null;
    private static PreparedStatement preparedStmt = null;
    private static ResultSet result = null;

    public static void connect() throws ClassNotFoundException, SQLException {
        // includes mysql driver
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

    //login returns string value if logged in to a checking or savings account
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
    //view balance field in checking_account and savings_account tables
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

    //adds amount to balance field
    public static void makeDeposit(double deposit, String username, String password) throws Exception {
        String account = "";
        String transactionAccount = "";
        if (login(username, password).equals("checking_account"))
        {
            account = "checking_account";
            transactionAccount = "checking_account_transaction";
        }
        if (login(username, password).equals("savings_account"))
        {
            account = "savings_account";
            transactionAccount = "savings_account_transaction";
        }
        int id = AccountActions.findId(username, password);
        String makeDeposit = "UPDATE " + account + " SET balance = balance + '" + deposit +
                "' WHERE username = '" + username + "' AND `id` = '" + id + "'";
        //inserts record into checking_account_transaction or savings_account_transaction
        String transactionRecord = "INSERT INTO " + transactionAccount + " (account_id, transaction_amount, transaction_date) VALUES ("
                + findId(username, password) + ", " + deposit + ", CURRENT_TIMESTAMP)";
        statement = connection.createStatement();
        //verifies account and makes transaction
        if(login(username, password).equals(account)) {
            statement.executeUpdate(makeDeposit);
            statement.executeUpdate(transactionRecord);
        }

    }
    // subtracts amount from balance field
    public static void makeWithdrawal(double withdrawal, String username, String password) throws Exception {
        int id = AccountActions.findId(username, password);
        String account = "";
        String transactionAccount = "";
        if (login(username, password).equals("checking_account"))
        {
            account = "checking_account";
            transactionAccount = "checking_account_transaction";
        }
        if (login(username, password).equals("savings_account"))
        {
            account = "savings_account";
            transactionAccount = "savings_account_transaction";
        }
        String withdraw = "UPDATE " + account + " SET balance = balance - '" + withdrawal +
                "' WHERE username = '" + username + "' AND `id` = '" + id + "'";
        //inserts record into checking_account_transaction or savings_account_transaction
        String transactionRecord = "INSERT INTO " + transactionAccount + " (account_id, transaction_amount, transaction_date) VALUES ("
                + findId(username, password) + ", " + withdrawal + ", CURRENT_TIMESTAMP)";
        statement = connection.createStatement();
        if(login(username, password).equals(account)) {
            statement.executeUpdate(withdraw);
            statement.executeUpdate(transactionRecord);
        }
    }
    //verifies that entered username is unique
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
    // view transaction records
    public static void viewTransactionHistory(String username, String password) throws Exception {
        String account = "";
        if (login(username, password).equals("checking_account"))
        {
            account = "checking_account_transaction";
        }
        if (login(username, password).equals("savings_account"))
        {
            account = "savings_account_transaction";
        }
        int id = findId(username, password);
        String viewQuery = "SELECT transaction_date, transaction_amount FROM " + account + " WHERE account_id = "
                + id;
        statement = connection.createStatement();
        result = statement.executeQuery(viewQuery);
        while (result.next())
        {
            System.out.println(result.getString("transaction_date") + result.getString("transaction_amount")
            + "\n\n");
        }

    }
    // find account id in savings and checking tables from username and password
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

