import java.util.Scanner;

public class atm {

    public static void main(String[] args){

        Scanner sc= new Scanner(System.in);

        Bank theBank= new Bank("Bank of Baroda");

        //add a user, also creates a savings account

        User aUser= theBank.addUser("Mirakhur", "Tanish", "1234");

        Account newAccount= new Account("Checking", aUser, theBank);
        aUser.addAccount(newAccount);
        theBank.addAccount(newAccount);

        User curUser;
        while(true){

            //stay in login prompt untill successfull login
            curUser= atm.mainMenuPrompt(theBank, sc);

            //stay in the main menu until user quits
            atm.printUserMenu(curUser, sc);

        }
    }

        public static User mainMenuPrompt(Bank theBank, Scanner sc){

            String userID;
            String pin;
            User authUser;

            //prompt user for ID/Pin until a correct one is reached.
            do{

                System.out.printf("\n\nWelcome to %s\n\n", theBank.getName());
                System.out.print("Enter user ID: ");
                userID= sc.nextLine();
                System.out.print("Enter pin: ");
                pin= sc.nextLine();

                authUser=theBank.userLogin(userID, pin);
                if(authUser==null){
                    System.out.println("incorrect user ID/pin combo. "+"please try again.");

                }

            }while(authUser==null); //loop untill successfull login

            return authUser;
        }

        public static void printUserMenu(User theUser, Scanner sc){
            theUser.printAccountsSummary();

            int choice;

            //user menu
            do{
                System.out.printf("Welcome %s, what would you like to do?", theUser.getFirstName());
                System.out.println(" 1) Show account transaction history");
                System.out.println(" 2) Withdraw");
                System.out.println(" 3) Deposity");
                System.out.println(" 4) transfer");
                System.out.println(" 5) Quit");
                System.out.println();
                System.out.println(" Enter Choice: ");
                choice = sc.nextInt();

                if(choice<1 || choice>5){
                    System.out.println("Invalid choice");
                }

            }while(choice<1|| choice>5);
            switch(choice){

                case 1:
                    atm.showTransHistory(theUser, sc);
                    break;
                case 2:
                    atm.withdrawFunds(theUser, sc);
                    break;
                case 3:
                    atm.depositFunds(theUser, sc);
                    break;
                case 4:
                    atm.transferFunds(theUser, sc);
                    break;
                case 5:
                sc.nextLine();
                break;
            }
            if(choice!=5){
                atm.printUserMenu(theUser, sc);
            }

        }

        public static void showTransHistory(User theUser, Scanner sc){

            int theAcct;

            do{
                System.out.printf("enter the number (1-%d) of the account\n"+" whose transactions you want to see: ", theUser.numAccounts());

                theAcct= sc.nextInt()-1;
                if(theAcct<0|| theAcct>= theUser.numAccounts()){
                    System.out.println("Invalid account. please try again");
                }
            }while(theAcct<0|| theAcct>= theUser.numAccounts());

            //printing transaction history
            theUser.printAcctTransHistory(theAcct);

        }

        public static void transferFunds(User theUser, Scanner sc){

            int fromAcct;
            int toAcct;
            double amount;
            double acctBal;

            //get the account to transfer from
            do{
                System.out.printf("enter the number (1-%d) of the account\n"+" to transfer from: ", theUser.numAccounts() );
                fromAcct= sc.nextInt()-1;
                if(fromAcct<0 || fromAcct>= theUser.numAccounts()){
                    System.out.println("Invalid account. please try again. ");
                }

            }while(fromAcct<0 || fromAcct>= theUser.numAccounts());
            acctBal= theUser.getAcctBalance(fromAcct);

            //get the account to transfer to
            do{
                System.out.printf("enter the number (1-%d) of the account\n"+" to transfer to: ", theUser.numAccounts() );
                toAcct= sc.nextInt()-1;
                if(toAcct<0 || toAcct>= theUser.numAccounts()){
                    System.out.println("Invalid account. please try again. ");
                }

            }while(toAcct<0 || toAcct>= theUser.numAccounts());

            //get the amount ot transfer
            do{
                System.out.printf("Enter the amount to transfer (max $0.2f): $", acctBal);
                amount= sc.nextDouble();
                if(amount<0){
                    System.out.println("Amount must be greater thsn zero");
                }else if(amount> acctBal){
                    System.out.printf("Amount must not be greater than\n"+ "balance of $%0.2f\n", acctBal);
                }
            }while(amount<0||amount>acctBal);

            //do the transfer
            theUser.addAcctTransaction(fromAcct, -1*amount, String.format("Transfer to account %s", theUser.getAcctUUID(toAcct)));
            theUser.addAcctTransaction(toAcct, amount, String.format("Transfer to account %s", theUser.getAcctUUID(fromAcct)));


        }

        public static void withdrawFunds(User theUser, Scanner sc){

            int fromAcct;
            double amount;
            double acctBal;
            String memo;

            //get the account to transfer from
            do{
                System.out.printf("enter the number (1-%d) of the account\n"+" to withdraw from: ", theUser.numAccounts());
                fromAcct= sc.nextInt()-1;
                if(fromAcct<0 || fromAcct>= theUser.numAccounts()){
                    System.out.println("Invalid account. please try again. ");
                }

            }while(fromAcct<0 || fromAcct>= theUser.numAccounts());
            acctBal= theUser.getAcctBalance(fromAcct);

                    //get the amount ot transfer
            do{
                System.out.printf("Enter the amount to transfer (max $0.2f): $", acctBal);
                amount= sc.nextDouble();
                if(amount<0){
                    System.out.println("Amount must be greater thsn zero");
                }else if(amount> acctBal){
                    System.out.printf("Amount must not be greater than\n"+ "balance of $%0.2f\n", acctBal);
                }
            }while(amount<0||amount>acctBal);

                    sc.nextLine();

                    //get a memo
                    System.out.print("enter a memo: ");
                    memo=sc.nextLine();

                    //do the withdraw
                    theUser.addAcctTransaction(fromAcct, -1*amount, memo);

        }

        public static void depositFunds(User theUser, Scanner sc){

            int toAcct;
            double amount;
            double acctBal;
            String memo;

            //get the account to transfer from
            do{
                System.out.printf("enter the number (1-%d) of the account\n"+" to deposit to ", theUser.numAccounts() );
                toAcct= sc.nextInt()-1;
                if(toAcct<0 || toAcct>= theUser.numAccounts()){
                    System.out.println("Invalid account. please try again. ");
                }

            }while(toAcct<0 || toAcct>= theUser.numAccounts());
            acctBal= theUser.getAcctBalance(toAcct);

                    //get the amount ot transfer
            do{
                System.out.printf("Enter the amount to transfer (max $0.2f): $", acctBal);
                amount= sc.nextDouble();
                if(amount<0){
                    System.out.println("Amount must be greater thsn zero");
                }
            }while(amount<0);

                    sc.nextLine();

                    //get a memo
                    System.out.print("enter a memo: ");
                    memo=sc.nextLine();

                    //do the withdraw
                    theUser.addAcctTransaction(toAcct, amount, memo);
        }


}