public class Account { 
    private String platform_name;
    private String password;
    private String strength;
    private String securityKey;

    public Account(String name, String password,String inputkey) { //parameterized constructor
        this.platform_name = name; 
        //this keyword is used to refer to the current instance of the class and differentiate between instance variables and parameters with the same name.
        this.password = password;
        this.securityKey=inputkey;

        if (password.length() < 8) { 
            this.strength = "weak";
        } else {
            this.strength = "strong";
        }
    }
    public String getPlatformName(){
        return platform_name;
    }public String getPassword(){
        return password;
    }public String getSecurityKey(){
        return securityKey;
    }
    
    
    public void displayAccount(){
        System.out.println("Platform name"+ ": "+ platform_name);
        System.out.println("password Strength"+ ": "+strength);
        System.out.println("password"+ ": " +"******");
    }
    
    public void show_RealPassword(String inputkey){
        if (this.securityKey.equals(inputkey)){
            System.out.println("Decrypted Password : "+password);
        } else{
            System.out.println("Key doesnt match!");
        }
    }

    
    
    public void setPassword(String newPass) {
    this.password = newPass; 
    }

}