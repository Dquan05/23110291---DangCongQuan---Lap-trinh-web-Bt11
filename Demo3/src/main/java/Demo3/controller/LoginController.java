package Demo3.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {
    // Controller này để xử lý các custom login handlers nếu cần
    // Hiện tại Spring Security đã tự động xử lý login
    
    @PostMapping("/login_success_handler")
    public String loginSuccessHandler() {
        System.out.println("✅ User login success...");
        return "redirect:/";
    }

    @PostMapping("/login_failure_handler")
    public String loginFailureHandler() {
        System.out.println("❌ Login failure...");
        return "redirect:/login?error=true";
    }
}
