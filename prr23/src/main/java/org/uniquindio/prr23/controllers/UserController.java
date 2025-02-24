package org.uniquindio.prr23.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.uniquindio.prr23.dto.UserRegistrationRequest;
import org.uniquindio.prr23.service.UserService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<UserRegistrationRequest> createUser(
            @Valid @RequestBody UserRegistrationRequest userRegistrationRequest) {
        UserRegistrationRequest createdUser = userService.createUser(userRegistrationRequest);
        return ResponseEntity.ok(createdUser);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserRegistrationRequest> getUserById(@PathVariable Long id) {
        Optional<UserRegistrationRequest> user = userService.getUserById(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<UserRegistrationRequest>> getAllUsers() {
        List<UserRegistrationRequest> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/paginated")
    public ResponseEntity<Page<UserRegistrationRequest>> getAllUsersPaginated(Pageable pageable) {
        Page<UserRegistrationRequest> users = userService.getAllUsers(pageable);
        return ResponseEntity.ok(users);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserRegistrationRequest> updateUser(
            @PathVariable Long id, @Valid @RequestBody UserRegistrationRequest userRegistrationRequest) {
        Optional<UserRegistrationRequest> updatedUser = userService.updateUser(id, userRegistrationRequest);
        return updatedUser.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        boolean isDeleted = userService.deleteUser(id);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}