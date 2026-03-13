import java.util.HashMap;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String MasterPassword = "123456";

        System.out.println("Please enter Master Password");
        if (!input.nextLine().equals(MasterPassword)) {
            System.out.println("Incorrect Password");
            System.exit(0);
        }
        System.out.println("Welcome user");

        HashMap<String, Account> vault = DatabaseManager.loadAccounts();
        //now following is our main code
        while (true) {
            System.out.println("1-Add Account\n2-View Details\n3-Delete Account\n4-Update Password\n5-Save and Exit\n6-Quit without saving");
            try {
                int choice = input.nextInt();
                input.nextLine();

                if (choice == 1) {
                    System.out.println("Enter Platform Name:");
                    String plat = input.nextLine().toLowerCase();
                    System.out.println("Enter Password:");
                    String pswd = input.nextLine();
                    System.out.println("Enter 4-digit Pin:");
                    String key = input.nextLine();

                    vault.put(plat, new Account(plat, pswd, key));
                    System.out.println("Account added to local session.");

                } else if (choice == 2) {
                    if (vault.isEmpty()) {
                        System.out.println("Vault is empty.");
                    } else {
                        for (Account a : vault.values()) a.displayAccount();
                        System.out.println("View specific password? Yes/No");
                        if (input.nextLine().equalsIgnoreCase("yes")) {
                            System.out.print("Enter Platform:");
                            String target = input.nextLine().toLowerCase();
                            if (vault.containsKey(target)) {
                                System.out.print("Enter Pin:");
                                String pin = input.nextLine();
                                if (pin.equals(vault.get(target).getSecurityKey())) {
                                    vault.get(target).show_RealPassword(pin);
                                } else {
                                    System.out.println("Incorrect Pin");
                                }
                            }
                        }
                    }
                } else if (choice == 3) {
                    System.out.println("Enter platform to delete:");
                    String target = input.nextLine().toLowerCase();
                    if (vault.containsKey(target)) {
                        System.out.print("Enter Pin:");
                        if (input.nextLine().equals(vault.get(target).getSecurityKey())) {
                            vault.remove(target);
                            System.out.println("Deleted from session.");
                        }
                    }
                } else if (choice == 4) {
                    System.out.println("Enter platform to update:");
                    String target = input.nextLine().toLowerCase();
                    if (vault.containsKey(target)) {
                        System.out.print("Enter Pin:");
                        Account acc = vault.get(target);
                        if (input.nextLine().equals(acc.getSecurityKey())) {
                            System.out.print("New Password:");
                            acc.setPassword(input.nextLine());
                            System.out.println("Updated in session.");
                        }
                    }
                } else if (choice == 5) {
                    DatabaseManager.saveToDatabase(vault); //
                    System.out.println("System exited Successfully");
                    break;
                } else if(choice==6){
                    System.out.print("exited without saving");
                    break;
                }
            } catch (Exception e) {
                System.out.println("Input Error.");
                input.nextLine();
            }
        }
    }
}