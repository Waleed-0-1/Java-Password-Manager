import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

public class DatabaseManager {

    private static final String URL = "jdbc:mysql://localhost:3306/vault_db";
    private static final String USER = "Your username here";
    private static final String PASS = "Your databse pass here";

    public static HashMap<String, Account> loadAccounts() {

        HashMap<String, Account> vault = new HashMap<>();

        //getting data from mysql databse
        //try block will help to close the connection after work is done
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS); //driveer manage is buitlnin class in java that helps to work with drivers which here we connected JAR file
             //getconnection go for the conneciton with 3 parameters which we gave above
             Statement stmt = conn.createStatement(); 
             //object to send queries(stmt) to the mysql databse
             ResultSet rs = stmt.executeQuery("SELECT * FROM accounts")) {
             //itll pick all the results recieved from database when it fires the query written along it to mysql
            
             //now this part is reading each row recied by above part and storing it in vault 
            while (rs.next()) { //while rs the object made above has next line then execute following block
                String plat = rs.getString("platform_name"); //itll read the colomn named "platform_name" and will store the result in plat variable
                String pswd = rs.getString("password"); //same for these two
                String key = rs.getString("security_key");
                vault.put(plat, new Account(plat, pswd, key)); //then we put all the result in the vault
            }
            System.out.println("Vault loaded from database successfully");
        } catch (SQLException e) { //except if any possible error occurs
            System.out.println("Error loading database: " + e.getMessage());
        }
        return vault;
    }
    
    public static void saveToDatabase(HashMap<String, Account> vault) { //it actually recieve our vault from main java
        String deleteQuery = "DELETE FROM accounts"; //to delete account on mysql databse
        String insertQuery = "INSERT INTO accounts (platform_name, password, security_key) VALUES (?, ?, ?)"; //to put new data in its place and question marks acting as placeholders 

        //agian same connection setup as above
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS)) {
            conn.setAutoCommit(false); //in java default is "true" and sql query immedeiately run so inthat case our new data will never be inderted because delete query will run at its own
         
            try (Statement st = conn.createStatement()) {  //agian to send query to databse we used statement class made object and conn is same as above
                st.executeUpdate(deleteQuery);//delete query is what we write above to delete data from mysql db
             } //executequery is used for INSERT/UPDATE/DELETE work on mysql db
            

             //now for the sexond query which was insert query
            try (PreparedStatement pstmt = conn.prepareStatement(insertQuery)) //we used PreparedStatement because we have to insert multiple data in mysql db and it is more better than "statement" class and its more safer and fast
             { //agian using insertquery
                for (Account a : vault.values()) { //using for each loop 
                    //PreparedStatement allow us to use placeholders as we use above "?" and now we need to fill theose paecholders with data
                    pstmt.setString(1, a.getPlatformName());
                    pstmt.setString(2, a.getPassword()); //setString is method which is replaing "?" with actual data and 1,2,3 is the position of "?" in our query
                    pstmt.setString(3, a.getSecurityKey());
                    pstmt.addBatch();//it allows to not send data immediately to database 
                }
                pstmt.executeBatch(); //now data will be sent to databse
                conn.commit();//abouve we set autocommt as false here we are setting it as default and its default value is true so from here itll run
                System.out.println("Database synced successfully.");
            }
        } catch (SQLException e) {
            System.out.println("Sync Error: " + e.getMessage());
        }
    }
}