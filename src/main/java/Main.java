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
            System.out.println("\t******Welcome to your Personal Banking System******\n");
            System.out.println("\tPress 1 to login to your account");
            System.out.println("\tPress 2 to create an account");
            System.out.println("\tPress 3 to exit");
            option = input.next();
            if(option != "1" && option != "2" && option != "3")
            {
                System.out.println("Invalid choice\n\n\n");
            }

            while (option == "1" || option == "2" || option == "3") {
                switch (option) {
                    case "1":
                        System.out.println("Enter your username");
                        //account = new Account();
                        account.setUserName(input.next());
                        System.out.println("Enter your password");
                        account.setPassword(input.next());
                        if (!pattern.matcher(account.getPassword()).matches()) {
                            System.out.println("Invalid Password");
                        } else {
                            loggedIn = true;
                        }
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
                                        System.out.println("Enter amount to deposit");
                                        AccountActions.makeDeposit(input.nextDouble());
                                        break;
                                    case 2:
                                        System.out.println("Enter amount to withdraw");
                                        AccountActions.makeWithdrawal(input.nextDouble());
                                        break;
                                    case 3:
                                        AccountActions.viewBalance(account.getUserName(), account.getPassword());
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
                                System.out.println("Set your username");
                                account.setUserName(input.next());
                                System.out.println("Set your password");
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

