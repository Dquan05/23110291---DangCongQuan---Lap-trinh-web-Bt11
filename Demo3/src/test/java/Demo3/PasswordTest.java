package Demo3;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordTest {
    
    @Test
    public void testPasswordEncoding() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        
        String rawPassword = "123456";
        String encodedFromDB = "$2a$10$8bvH9B.pRHY1I.xk5bqxJO7eB3WR8KI7FW9KKSYyF5FLJPxCqM2Rq";
        
        System.out.println("Testing password: " + rawPassword);
        System.out.println("Hash from DB: " + encodedFromDB);

        boolean matches = encoder.matches(rawPassword, encodedFromDB);
        System.out.println("Password matches: " + matches);

        String newHash = encoder.encode(rawPassword);
        System.out.println("New hash: " + newHash);

        boolean newMatches = encoder.matches(rawPassword, newHash);
        System.out.println("New hash matches: " + newMatches);
    }
    
    @Test
    public void generatePasswordHash() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String password = "123456";
        String hash = encoder.encode(password);
        
        System.out.println("========================================");
        System.out.println("Password: " + password);
        System.out.println("BCrypt Hash: " + hash);
        System.out.println("========================================");
        System.out.println("Use this SQL to update password:");
        System.out.println("UPDATE users SET password = '" + hash + "' WHERE username = 'admin';");
        System.out.println("========================================");
    }
}
