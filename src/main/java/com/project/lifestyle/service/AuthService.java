package com.project.lifestyle.service;


import com.project.lifestyle.dto.AuthenticationResponse;
import com.project.lifestyle.dto.LoginRequest;
import com.project.lifestyle.dto.RefreshTokenRequest;
import com.project.lifestyle.dto.RegisterRequest;
import com.project.lifestyle.model.Role;
import com.project.lifestyle.model.User;
import com.project.lifestyle.repository.RefreshTokenRepository;
import com.project.lifestyle.repository.RoleRepository;
import com.project.lifestyle.repository.UserRepository;
import com.project.lifestyle.repository.VerificationTokenRepository;
import com.project.lifestyle.security.JwtUtil;
import lombok.AllArgsConstructor;


import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;


import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final MailService mailService;
    private final VerificationTokenRepository verificationTokenRepository;
    private final JwtUtil jwtUtil;
    private final RefreshTokenRepository refreshTokenRepository;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public User registration(RegisterRequest registerRequest){
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setEmail(registerRequest.getEmail());
        user.setCreated(Instant.now());
        user.setEnabled(false);

        userRepository.save(user);

        String token = generateVerificationToken(user);
        mailService.sendEmail(user.getEmail(),"Registration on LifeStyle",
                "Thank you for registration, " +
                        "please click on the below " + "url to activate your account : " +
                        "http://localhost:8080/api/auth/accountVerification/" + token);

        return user;
    }

    public void setUserDefaultRole(User user){
        Role roleUser = roleRepository.findByName("USER");
        user.addRole(roleUser);

        userRepository.save(user);
    }

    private String generateVerificationToken(User user){
        String token = UUID.randomUUID().toString();
        User verificationToken = getUser(user.getUsername());
        verificationToken.setToken(token);

        verificationTokenRepository.save(verificationToken);
        return token;
    }

    public void verifyAccount(String token){
        Optional<User> verificationToken = verificationTokenRepository.findByToken(token);
        fetchUserAndEnable(verificationToken.orElseThrow());
    }

    private void fetchUserAndEnable(User verificationToken){
        String username = verificationToken.getUsername();
        User user = userRepository.findByUsername(username).orElseThrow();
        user.setEnabled(true);
        userRepository.save(user);
    }

    void validateRefreshToken(String token){
        refreshTokenRepository.findByToken(token).orElseThrow();
    }

    public AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        validateRefreshToken(refreshTokenRequest.getRefreshToken());

        String token = jwtUtil.generateAccessToken(getUser(refreshTokenRequest.getUsername()) );
        return AuthenticationResponse.builder()
                .authenticationToken(token)
                .refreshToken(refreshTokenRequest.getRefreshToken())
                .username(refreshTokenRequest.getUsername())
                .expiresAt(Instant.now().plusMillis(jwtUtil.getEXPIRE_DURATION()))
                .build();
    }

    @Transactional
    public void deleteRefreshToken(String token) {
       userRepository.findByToken(token).setToken(null);
    }

    public boolean isLoggedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated();
    }

    public User getUser(String username) {
            return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User name not found - " + username));
    }

    @Transactional(readOnly = true)
    public User getUser() {
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findByUsername(principal.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User with name - " + principal.getUsername() + " not found"));
    }

    public AuthenticationResponse login(LoginRequest loginRequest) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String token = jwtUtil.generateAccessToken(getUser());
        return AuthenticationResponse.builder()
                .authenticationToken(token)
                .refreshToken(generateVerificationToken(getUser()))
                .expiresAt(Instant.now().plusMillis(jwtUtil.getEXPIRE_DURATION()))
                .username(loginRequest.getUsername())
                .isAdmin(isAdmin(loginRequest.getUsername()))
                .build();
    }
    public boolean isAdmin(String username){
        User user = getUser(username);
        for (Role role : user.getRoles()){
            if (role == roleRepository.findByName("ADMIN")){
                return true;
            }
        }
        return false;
    }
}
