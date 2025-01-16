package khuong.com.smartorder.service;

import khuong.com.smartorder.entity.User;
import khuong.com.smartorder.payload.request.*;
import khuong.com.smartorder.payload.response.MessageResponse;
import khuong.com.smartorder.repository.UserRepository;
import khuong.com.smartorder.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private PasswordEncoder encoder;

    public ResponseEntity<?> login(LoginRequest loginRequest) {
        // Implement login logic
        return ResponseEntity.ok("Login successful");
    }

    public ResponseEntity<?> logout() {
        // Implement logout logic
        return ResponseEntity.ok(new MessageResponse("Logout successful"));
    }

    public ResponseEntity<?> refreshToken(TokenRefreshRequest request) {
        // Implement refresh token logic
        String token = request.getRefreshToken();
        if (jwtUtils.validateJwtToken(token)) {
            String newToken = jwtUtils.generateJwtTokenFromRefreshToken(token);
            return ResponseEntity.ok(new MessageResponse("Token refreshed: " + newToken));
        } else {
            return ResponseEntity.badRequest().body(new MessageResponse("Invalid refresh token"));
        }
    }

    public ResponseEntity<?> forgotPassword(ForgotPasswordRequest request) {
        // Implement forgot password logic
        String email = request.getEmail();
        if (userRepository.existsByEmail(email)) {
            // Send password reset link logic
            return ResponseEntity.ok(new MessageResponse("Password reset link sent"));
        } else {
            return ResponseEntity.badRequest().body(new MessageResponse("Email not found"));
        }
    }

    public ResponseEntity<?> resetPassword(ResetPasswordRequest request) {
        // Implement reset password logic
        String token = request.getToken();
        if (jwtUtils.validateJwtToken(token)) {
            String newPassword = request.getNewPassword();
            User user = userRepository.findByEmail(jwtUtils.getUserNameFromJwtToken(token)).orElse(null);
            if (user != null) {
                user.setPassword(encoder.encode(newPassword));
                userRepository.save(user);
                return ResponseEntity.ok(new MessageResponse("Password reset successful"));
            } else {
                return ResponseEntity.badRequest().body(new MessageResponse("User not found"));
            }
        } else {
            return ResponseEntity.badRequest().body(new MessageResponse("Invalid reset token"));
        }
    }
}