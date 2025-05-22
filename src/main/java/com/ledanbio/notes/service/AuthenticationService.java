package com.ledanbio.notes.service;

import com.ledanbio.notes.payroll.UserAlreadyExistsException;
import com.ledanbio.notes.model.Users.Role;
import com.ledanbio.notes.model.Users.User;
import com.ledanbio.notes.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthenticationService {
    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final AuthenticationManager manager;

    public AuthenticationService(PasswordEncoder passwordEncoder, UserRepository userRepository, AuthenticationManager manager) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.manager = manager;
    }

    @Transactional
    public void registerNewUser(String username, String password) {
        if (userRepository.existsByUsername(username)) {
            throw new UserAlreadyExistsException("User already exists");
        }

        User newUser = new User(
                username,
                passwordEncoder.encode(password),
                Role.USER
        );

        userRepository.save(newUser);
    }

    public Authentication login(String username, String password){
        try {
            Authentication authentication = manager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );

            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));


            return authentication;
        } catch (AuthenticationException e) {
            throw new RuntimeException(e);
        }
    }
}
