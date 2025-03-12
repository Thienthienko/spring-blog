package org.wildcodeschool.myblog.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.wildcodeschool.myblog.model.User;
import org.wildcodeschool.myblog.service.CustomUserDetailsService;
import org.wildcodeschool.myblog.service.UserService;

@RestController
@RequestMapping("/profile")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    @PreAuthorize("#id == authentication.principal.id")
    public ResponseEntity<User> getUserProfile(@PathVariable Long id) {
        User user = userService.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        return ResponseEntity.ok(user);
    }

    @PreAuthorize("isAuthenticated() and #user.isActive()")
    @PostMapping("/activate")
    public ResponseEntity<String> activateAccount(@RequestBody User user) {
        return ResponseEntity.ok("Compte activé avec succès");
    }
}