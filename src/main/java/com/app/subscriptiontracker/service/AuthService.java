package com.app.subscriptiontracker.service;

import com.app.subscriptiontracker.model.User;
import com.app.subscriptiontracker.repository.UserRepository;
import com.app.subscriptiontracker.security.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final EmailService emailService;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtUtil jwtUtil, EmailService emailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.emailService = emailService;
    }



    public String register(String name, String email, String password) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("Email-ul este deja folosit!");
        }

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));

        userRepository.save(user);


        emailService.sendWelcomeEmail(email, name);

        return jwtUtil.generateToken(email);
    }

    public String login(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Email sau parolă incorecte!"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Email sau parolă incorecte!");
        }
        return jwtUtil.generateToken(email);
    }
}