package khuong.com.smartorder.controller;

import jakarta.validation.Valid;
import khuong.com.smartorder.dto.UpdateUserDTO;
import khuong.com.smartorder.dto.UserDTO;
import khuong.com.smartorder.security.UserDetailsImpl;
import khuong.com.smartorder.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/profile")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getUserProfile(@AuthenticationPrincipal UserDetailsImpl currentUser) {
        UserDTO userResponse = userService.getUserProfile(currentUser.getId());
        return ResponseEntity.ok(userResponse);
    }

    @PutMapping("/profile")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> updateUserProfile(
            @AuthenticationPrincipal UserDetailsImpl currentUser,
            @Valid @RequestBody UpdateUserDTO updateRequest) {
        UserDTO userResponse = userService.updateUserProfile(currentUser.getId(), updateRequest);
        return ResponseEntity.ok(userResponse);
    }
}