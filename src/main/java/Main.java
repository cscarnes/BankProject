import javax.swing.*;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {


    public static void main(String[] args) throws Exception {
        AccountActions.connect();

        int flag = 0;
        Scanner input = new Scanner(System.in);
        String option = "";
        boolean loggedIn = false;
        int accountType = 0;
        Account account = new Account();
        final Pattern pattern = Pattern.compile("^[A-Za-z,]++$");

        while (flag == 0) {
            System.out.println("******Welcome to your Personal Banking System******\n");
            System.out.println("Press 1 to login to your account");
            System.out.println("Press 2 to create an account");
            System.out.println("Press 3 to exit");
            option = input.next();

            if(!option.equals("1") && !option.equals("2") && !option.equals("3"))
            {
                System.out.println("Invalid choice\n\n\n");
            }


            while (option.equals("1") || option.equals("2") || option.equals("3")) {
                switch (option) {
                    case "1":
                        System.out.println("Enter your username");
                        account.setUserName(input.next());
                        System.out.println("Enter your password");
                        account.setPassword(input.next());
                        if (AccountActions.login(account.getUserName(), account.getPassword()) == 1){
                            loggedIn = true;
                        }
                        /*
                        if (!pattern.matcher(account.getPassword()).matches()) {
                            System.out.println("Invalid Password");
                        }
                         */
                        if(loggedIn)
                        {
                            while(loggedIn)
                            {
                                int choice = 0;
                                System.out.println("Press 1 to make a deposit");
                                System.out.println("Press 2 to make a withdrawal");
                                System.out.println("Press 3 to view balance");
                                System.out.println("Press 4 to logout");
                                choice = input.nextInt();
                                switch (choice) {
                                    case 1:
                                        System.out.println(AccountActions.findId(account.getUserName()));
                                        System.out.println("Enter amount to deposit");
                                        double depositAmount = input.nextDouble();
                                        while (depositAmount < 0 || depositAmount > 10000)
                                        {
                                            System.out.println("Invalid amount");
                                            depositAmount = input.nextDouble();
                                        }
                                        AccountActions.makeDeposit(depositAmount, account.getUserName());
                                        break;
                                    case 2:
                                        System.out.println("Enter amount to withdraw");
                                        double withdrawalAmount = input.nextDouble();
                                        while(withdrawalAmount < 0 || withdrawalAmount > AccountActions.viewBalance(account.getUserName(), account.getPassword()))
                                        {
                                            System.out.println("Invalid amount");
                                            withdrawalAmount = input.nextDouble();
                                        }
                                        AccountActions.makeWithdrawal(input.nextDouble(), account.getUserName());
                                        break;
                                    case 3:
                                        System.out.println("Balance");
                                        System.out.println(AccountActions.viewBalance(account.getUserName(), account.getPassword()));
                                        System.out.print("\n\n\n");
                                        break;
                                    case 4:
                                        loggedIn = false;
                                        option = "0";
                                        break;
                                    default:
                                        break;
                                }
                            }
                        }

                        break;
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
                                AccountActions.verifyUsername(account.getUserName());
                                System.out.println("Create your password");
                                account.setPassword(input.next());
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
                                System.out.println("Set your username");
                                account.setUserName(input.next());
                                System.out.println("Set your password");
                                account.setPassword(input.next());
                                AccountActions.createSavingsAccount(account.getFirstName(), account.getLastName(),
                                        account.getEmail(), account.getUserName(), account.getPassword());
                                break;
                        }
                        option = "0";
                        break;
                    }
                    case "3":
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

