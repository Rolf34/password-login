import java.util.Scanner;
import javax.naming.AuthenticationException;// shtuchka dlya isklucheniya oshibochek (v budushem uberu)

//

public class App {
    private static final int MAX_USERS = 15;
    private static final int MIN_USERNAME_LENGTH = 5;
    private static final int MIN_PASSWORD_LENGTH = 10;
    private static final int MIN_DIGITS_IN_PASSWORD = 3;
    
    private String[] usernames;
    private String[] passwords;
    private String[] bannedPasswords;
    private int userCount;
    private int bannedPasswordsCount;
    private static Scanner scanner = new Scanner(System.in);

    public App() {
        usernames = new String[MAX_USERS];
        passwords = new String[MAX_USERS];
        bannedPasswords = new String[50];

        //ce v nas spisok zabanenih paroley

        bannedPasswords[0] = "password";
        bannedPasswords[1] = "admin";
        bannedPasswords[2] = "pass";
        bannedPasswords[3] = "qwerty";
        bannedPasswords[4] = "ytrweq";
        bannedPasswords[5] = "123456"; // ya svoy dobavil
        bannedPasswordsCount = 6;
        userCount = 0;
    }
    public void register(String username, String password) throws AuthenticationException{
        if (userCount >= MAX_USERS) {
            throw new AuthenticationException("Max users reached(15)");
        }
        trueUsername(username);

        for(int i = 0; i < userCount; i++) {
            if (usernames[i].equals(username)) {
                throw new AuthenticationException("Username already exists");
            }
        } 
        truePassword(password);
        usernames[userCount] = username;
        passwords[userCount] = password;
        userCount++;

        System.out.println("User registered successfully");
    }
    public void delete(String username) throws AuthenticationException{
             int userIndex = -1;
        
        for (int i = 0; i < userCount; i++) {
            if (usernames[i].equals(username)) {
                userIndex = i;
                break;
            }
        }
        
        if (userIndex == -1) {
            throw new AuthenticationException("User with username '" + username + "' not found.");
        }
        
        
        for (int i = userIndex; i < userCount - 1; i++) {
            usernames[i] = usernames[i + 1];
            passwords[i] = passwords[i + 1];
        }
        
        
        usernames[userCount - 1] = null;
        passwords[userCount - 1] = null;
        userCount--;
        
        System.out.println("User deleted successfully.");
    }
    
    public void authenticateUser(String username, String password) throws AuthenticationException {
        for (int i = 0; i < userCount; i++) {
            if (usernames[i].equals(username)) {
                if (passwords[i].equals(password)) {
                    System.out.println("User authenticated successfully.");
                    return;
                } else {
                    throw new AuthenticationException("Incorrect password.");
                }
            }
        }
        
        throw new AuthenticationException("User with username '" + username + "' not found.");
    }
    private void trueUsername(String username) throws AuthenticationException {
        if (username.length() < MIN_USERNAME_LENGTH) {
            throw new AuthenticationException("Username must be at least " + MIN_USERNAME_LENGTH + " characters long.");
        }
    }

    private void truePassword(String password) throws AuthenticationException {
        if (password.length() < MIN_PASSWORD_LENGTH) {
            throw new AuthenticationException("Password must be at least " + MIN_PASSWORD_LENGTH + " characters long.");
        }

        int digitCount = 0;
        for (char c : password.toCharArray()) {
            if (Character.isDigit(c)) {
                digitCount++;
            }
        }

        if (digitCount < MIN_DIGITS_IN_PASSWORD) {
            throw new AuthenticationException("Password must contain at least " + MIN_DIGITS_IN_PASSWORD + " digits.");
        }

        for (int i = 0; i < bannedPasswordsCount; i++) {
            if (bannedPasswords[i].equals(password)) {
                throw new AuthenticationException("Password is banned.");
            }
        }
    }
    // private void addBannedPassword(String bannedPassword) throws AuthenticationException {
    //   for (int i = 0; i < bannedPasswordsCount; i++) {
    //         if (bannedPasswords[i].equals(bannedPassword)) {
    //             throw new AuthenticationException("This password is already in the forbidden list.");
    //         }
    //     }
        
    //     // Add forbidden password
    //     if (bannedPasswordsCount < bannedPasswords.length) {
    //         bannedPasswords[bannedPasswordsCount] = bannedPassword;
    //         bannedPasswordsCount++;
    //         System.out.println("that password wasd banned.");
    //     } else {
    //         throw new AuthenticationException("Maximum number of forbidden passwords reached.");
    //     }  
    // }

    public static void main(String[] args) throws Exception {
        
    }
}
