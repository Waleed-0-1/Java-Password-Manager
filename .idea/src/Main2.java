// import java.sql.*;
// import java.util.HashMap;
// import java.util.Scanner;

// public class Main2 {
//     // //adding our databse credentials
//     // static final String URL = "jdbc:mysql://localhost:3306/vault_db"; //at the end is our database name
//     // static final String USER = "root";
//     // static final String PASS = "you databse pass";

//     public static void main(String[] args) {
//         Scanner input = new Scanner(System.in);
//         String MasterPassword = "123456";

//         System.out.println("Please enter Master Passsword");
//         if (!input.nextLine().equals(MasterPassword)) {
//             System.out.println("Incorrect Password");
//             System.exit(0);
//         }
//         System.out.println("Welcome user");

//         HashMap<String, Account> vault = new HashMap<>();

//         //fetting data from mysql databse
//         //try block will help to close the connection after work is done
//         try (Connection conn = DriverManager.getConnection(URL, USER, PASS); //driveer manage is buitlnin class in java that helps to work with drivers which here we connected JAR file
//              //getconnection go for the conneciton with 3 parameters which we gave above
//              Statement stmt = conn.createStatement(); 
//              //object to send queries(stmt) to the mysql databse
//              ResultSet rs = stmt.executeQuery("SELECT * FROM accounts")) {
//              //itll pick all the results recieved from database when it fires the query written along it to mysql
            
//              //now this part is reading each row recied by above part and storing it in vault 
//             while (rs.next()) { //while rs the object made above has next line then execute following block
//                 String plat = rs.getString("platform_name"); //itll read the colomn named "platform_name" and will store the result in plat variable
//                 String pswd = rs.getString("password"); //same for these two
//                 String key = rs.getString("security_key");
//                 vault.put(plat, new Account(plat, pswd, key)); //then we put all the result in the vault
//             }
//             System.out.println("Vault loaded from database successfully");
//         } catch (SQLException e) { //except if any possible error occurs
//             System.out.println("Error loading database: " + e.getMessage());
//         }

//         //now following is our main code
//         while (true) {
//             System.out.println("1-Add Account\n2-View Details\n3-Delete Account\n4-Update Password\n5-Save and Exit");
//             try {
//                 int choice = input.nextInt();
//                 input.nextLine();

//                 if (choice == 1) {
//                     System.out.println("Enter Platform Name:");
//                     String plat = input.nextLine().toLowerCase();
//                     System.out.println("Enter Password:");
//                     String pswd = input.nextLine();
//                     System.out.println("Enter 4-digit Pin:");
//                     String key = input.nextLine();

//                     vault.put(plat, new Account(plat, pswd, key));
//                     System.out.println("Account added to local session.");

//                 } else if (choice == 2) {
//                     if (vault.isEmpty()) {
//                         System.out.println("Vault is empty.");
//                     } else {
//                         for (Account a : vault.values()) a.displayAccount();
//                         System.out.println("View specific password? Yes/No");
//                         if (input.nextLine().equalsIgnoreCase("yes")) {
//                             System.out.print("Enter Platform:");
//                             String target = input.nextLine().toLowerCase();
//                             if (vault.containsKey(target)) {
//                                 System.out.print("Enter Pin:");
//                                 String pin = input.nextLine();
//                                 if (pin.equals(vault.get(target).getSecurityKey())) {
//                                     vault.get(target).show_RealPassword(pin);
//                                 } else {
//                                     System.out.println("Incorrect Pin");
//                                 }
//                             }
//                         }
//                     }
//                 } else if (choice == 3) {
//                     System.out.println("Enter platform to delete:");
//                     String target = input.nextLine().toLowerCase();
//                     if (vault.containsKey(target)) {
//                         System.out.print("Enter Pin:");
//                         if (input.nextLine().equals(vault.get(target).getSecurityKey())) {
//                             vault.remove(target);
//                             System.out.println("Deleted from session.");
//                         }
//                     }
//                 } else if (choice == 4) {
//                     System.out.println("Enter platform to update:");
//                     String target = input.nextLine().toLowerCase();
//                     if (vault.containsKey(target)) {
//                         System.out.print("Enter Pin:");
//                         Account acc = vault.get(target);
//                         if (input.nextLine().equals(acc.getSecurityKey())) {
//                             System.out.print("New Password:");
//                             acc.setPassword(input.nextLine());
//                             System.out.println("Updated in session.");
//                         }
//                     }
//                 } else if (choice == 5) {
//                     DatabaseManager.saveToDatabase(vault); // Sync local HashMap back to MySQL [cite: 2026-03-08]
//                     System.out.println("System exited Successfully");
//                     break;
//                 }
//             } catch (Exception e) {
//                 System.out.println("Input Error.");
//                 input.nextLine();
//             }
//         }
//     }
// }