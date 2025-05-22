package com.ledanbio.notes.controller;

import com.ledanbio.notes.model.Users.Role;
import com.ledanbio.notes.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

@Controller
public class LoginController {

    private final AuthenticationService service;

    public LoginController(AuthenticationService service) {
        this.service = service;
    }

    @GetMapping(value = "/login")
    public String loginPage(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout,
            Model model,
            HttpServletRequest request
    ){
        if(error != null){
            HttpSession session = request.getSession(false);
            String errorMessage = null;
            if(session != null){
                AuthenticationException ex = (AuthenticationException)
                        session.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);

                if (ex != null){
                    errorMessage = ex.getMessage();
                }
            }

            model.addAttribute("errorMessage",
                    errorMessage != null
                            ? errorMessage
                            : "Invalid username or password"
            );
        }
        if (logout != null){
            model.addAttribute("logoutMessage",
                    "You have successfully logged out"
            );
        }

        return "login";
    }

    @PostMapping("/login")
    public ResponseEntity<?> authUser(
            @RequestParam String username,
            @RequestParam String password) {

        Authentication authentication = service.login(username, password);

        Map<String, Object> response = new HashMap<>();
        response.put("username",authentication.getName());
        response.put("authorities", Role.USER);
        return ResponseEntity.ok(response);
    }
}


