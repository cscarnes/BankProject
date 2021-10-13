import java.io.Console;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {
    //Enter this value at username prompt to exit login screen
    static final String CANCEL = "EXIT";

    public static void main(String[] args) throws Exception {
        AccountActions.connect();
        Console console = System.console();
        int flag = 0;
        Scanner input = new Scanner(System.in);
        //option variable for selecting option to login or create account
        String option;
        //loggedIn checks whether user is logged in or not, false exits switch and returns to main screen
        boolean loggedIn = false;
        //accountType returns whether account is a checking or savings account
        int accountType;
        Account account = new Account();
        // regexp for verifying password input
        final Pattern pattern = Pattern.compile("^[A-Za-z,.<>?!@#$%^&*()_+={}-]++$");

        while (flag == 0) {
            System.out.println("******Welcome to your Personal Banking System******\n");
            System.out.println("Press 1 to login to your account");
            System.out.println("Press 2 to create an account");
            System.out.println("Press 3 to exit");
            option = input.next();

            if(!option.equals("1") && !option.equals("2") && !option.equals("3"))
            {
                System.out.println("Please enter a valid selection\n\n\n");
            }


            while (option.equals("1") || option.equals("2") || option.equals("3")) {
                switch (option) {
                    case "1":
                        System.out.println("Enter your username: ");
                        System.out.println("Enter EXIT to return to the main menu: \n");
                        account.setUserName(input.next());
                        if (account.getUserName().equals(CANCEL))
                        {
                            option = "0";
                            break;
                        }
                        String enteredPassword = new String(console.readPassword("Enter password: "));
                        account.setPassword(enteredPassword);
                        if (AccountActions.login(account.getUserName(), account.getPassword()).equals("checking_account")
                                || AccountActions.login(account.getUserName(), account.getPassword()).equals("savings_account")){
                            loggedIn = true;
                        }
                        else{
                            System.out.println("Account does not exist");
                        }

                        if(loggedIn)
                        {
                            while(loggedIn)
                            {
                                String choice = "";
                                System.out.println("Press 1 to make a deposit");
                                System.out.println("Press 2 to make a withdrawal");
                                System.out.println("Press 3 to view balance");
                                System.out.println("Press 4 to view transaction history");
                                System.out.println("Press 5 to logout");
                                choice = input.next();
                                switch (choice) {
                                    case "1":
                                        System.out.println("Enter amount to deposit");
                                        double depositAmount = input.nextDouble();
                                        // deposit amount cannot be negative or exceed 10000
                                        while (depositAmount < 0 || depositAmount > 10000)
                                        {
                                            System.out.println("Invalid amount");
                                            depositAmount = input.nextDouble();
                                        }
                                        AccountActions.makeDeposit(depositAmount, account.getUserName(), account.getPassword());
                                        break;
                                    case "2":
                                        System.out.println("Enter amount to withdraw");
                                        double withdrawalAmount = input.nextDouble();
                                        // withdrawal amount cannot be negative or exceed account balance
                                        while(withdrawalAmount < 0 || withdrawalAmount > AccountActions.viewBalance(account.getUserName(), account.getPassword()))
                                        {
                                            System.out.println("Invalid amount");
                                            withdrawalAmount = input.nextDouble();
                                        }
                                        AccountActions.makeWithdrawal(withdrawalAmount, account.getUserName(), account.getPassword());
                                        break;
                                    case "3":
                                        System.out.println("Balance");
                                        System.out.println(AccountActions.viewBalance(account.getUserName(), account.getPassword()));
                                        System.out.print("\n\n\n");
                                        break;
                                    case "4":
                                        System.out.println("Transaction History");
                                        AccountActions.viewTransactionHistory(account.getUserName(), account.getPassword());
                                        break;
                                    case "5":
                                        loggedIn = false;
                                        option = "0";
                                        break;
                                    default:
                                        break;
                                }
                            }
                        }

                        break;
                        //
                    case "2": {
                        System.out.println("Select account type: ");
                        System.out.println("Press 1 to create a checking account");
                        System.out.println("Press 2 to create a savings account");
                        accountType = input.nextInt();
                        switch (accountType) {
                            case 1:
                                System.out.println("Enter your first name");
                                account.setFirstName(input.next());
                                System.out.println("Enter your last name");
                                account.setLastName(input.next());
                                System.out.println("Enter your email");
                                account.setEmail(input.next());
                                System.out.println("Create your username");
                                account.setUserName(input.next());
                                while(!AccountActions.verifyUsername(account.getUserName()) ||
                                        account.getUserName().length() < 5 || account.getUserName().equals(CANCEL))
                                {
                                    System.out.println("Invalid username");
                                    account.setUserName(input.next());
                                }
                                // password must match regexp pattern
                                System.out.println("Create your password (must be at least 8 characters long)");
                                System.out.println("Passwords can contain be any alphanumeric character or the special characters" +
                                        ",.<>?!@#$%^&*()_+=-{}");
                                String checkingPassword = new String(console.readPassword("Enter password: "));
                                account.setPassword(checkingPassword);
                                while (!pattern.matcher(account.getPassword()).matches() || account.getPassword().length() < 8) {
                                    System.out.println("Invalid Password");
                                    account.setPassword(input.next());
                                }
                                AccountActions.createCheckingAccount(account.getFirstName(), account.getLastName(),
                                        account.getEmail(), account.getUserName(), account.getPassword());
                                break;
                            case 2:
                                System.out.println("Enter your first name");
                                account.setFirstName(input.next());
                                System.out.println("Enter your last name");
                                account.setLastName(input.next());
                                System.out.println("Enter your email");
                                account.setEmail(input.next());
                                System.out.println("Create your username");
                                System.out.println("Username must be 5 characters in length");
                                account.setUserName(input.next());
                                while (!AccountActions.verifyUsername(account.getUserName()) ||
                                        account.getUserName().length() < 5 || account.getUserName().equals(CANCEL))
                                {
                                    System.out.println("Invalid username");
                                    account.setUserName(input.next());
                                }
                                // password must match regexp
                                System.out.println("Create your password (must be at least 8 characters long)");
                                System.out.println("Passwords can contain be any alphanumeric character or the special characters" +
                                        ",.<>?!@#$%^&*()_+=-{}");
                                String savingsPassword = new String(console.readPassword("Enter password: "));
                                account.setPassword(savingsPassword);
                                while (!pattern.matcher(account.getPassword()).matches() || account.getPassword().length() < 8) {
                                    System.out.println("Invalid Password");
                                    account.setPassword(input.next());
                                }
                                AccountActions.createSavingsAccount(account.getFirstName(), account.getLastName(),
                                        account.getEmail(), account.getUserName(), account.getPassword());
                                break;
                        }
                        option = "0";
                        break;
                    }
                    case "3":
                        // entering 3 exits program
                        System.exit(0);
                    default:
                        System.out.println("\tPress 1 to enter your login credentials");
                        System.out.println("\tPress 2 to login in to an existing account");
                        option = input.next();
                        break;
                }

            }
        }
    }


    }

