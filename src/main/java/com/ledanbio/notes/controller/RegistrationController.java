package com.ledanbio.notes.controller;


import com.ledanbio.notes.model.Users.User;
import com.ledanbio.notes.repository.UserRepository;
import com.ledanbio.notes.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/registration")
public class RegistrationController {

    private final AuthenticationService authenticationService;

    public RegistrationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @GetMapping
    public String registration(){
        return "registration";
    }

    @PostMapping
    public String addUser(
            @RequestParam String username,
            @RequestParam String password
    ){

        authenticationService.registerNewUser(username, password);

        return "redirect:/login";
    }
}
