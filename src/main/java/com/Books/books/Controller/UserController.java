package com.Books.books.Controller;

import com.Books.books.Dto.UserDTO;
import com.Books.books.Entities.Role;
import com.Books.books.Entities.User;
import com.Books.books.Repository.RoleRepository;
import com.Books.books.Repository.UserRepository;
import com.Books.books.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserService userService;

   /* @Autowired
    private BCryptPasswordEncoder passwordEncoder;*/

    @GetMapping("/all")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/add")
    public ResponseEntity<String> addUser(@RequestBody UserDTO userDTO) {
        if (userRepository.findByUsername(userDTO.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Error: Username already exists");
        }

        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(NoOpPasswordEncoder.getInstance().encode(userDTO.getPassword()));
        user.setEnabled(true);

        // Handle role assignment
        Optional<Role> roleOptional = Optional.ofNullable(roleRepository.findByName("ROLE_" + userDTO.getRole().toUpperCase()));
        if (roleOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("Error: Role not found");
        }
        Role role = roleOptional.get();
        user.setRoles(new HashSet<>(Collections.singletonList(role)));

        try {
            userRepository.save(user);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Internal Server Error: " + e.getMessage());
        }

        return ResponseEntity.ok("User created successfully");
    }


}
