package khuong.com.smartorder.service;

import khuong.com.smartorder.dto.UpdateUserDTO;
import khuong.com.smartorder.dto.UserDTO;
import khuong.com.smartorder.entity.User;
import khuong.com.smartorder.exception.ResourceNotFoundException;
import khuong.com.smartorder.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserDTO getUserProfile(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return mapToUserDTO(user);
    }

    public UserDTO updateUserProfile(Long userId, UpdateUserDTO updateRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (updateRequest.getFullName() != null) {
            user.setFullName(updateRequest.getFullName());
        }
        if (updateRequest.getPhone() != null) {
            user.setPhone(updateRequest.getPhone());
        }
        if (updateRequest.getAddress() != null) {
            user.setAddress(updateRequest.getAddress());
        }
        if (updateRequest.getGender() != null) {
            user.setGender(updateRequest.getGender());
        }

        user = userRepository.save(user);
        return mapToUserDTO(user);
    }

    private UserDTO mapToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        userDTO.setFullName(user.getFullName());
        userDTO.setPhone(user.getPhone());
        userDTO.setAddress(user.getAddress());
        userDTO.setGender(user.getGender());
        return userDTO;
    }

    public User getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        return userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
    }
}