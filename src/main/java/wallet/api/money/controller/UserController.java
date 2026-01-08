package wallet.api.money.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wallet.api.money.dto.user.CreateUserDTO;
import wallet.api.money.entity.Users;
import wallet.api.money.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/wallet/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<String> createUser(@Valid @RequestBody CreateUserDTO dto) {
        if (dto.getNationalId() == null) {
            return ResponseEntity.badRequest().body("National ID cannot be null.");
        }

        userService.createUser(dto);
        return ResponseEntity.ok("User created successfully.");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Users> getUserById(@PathVariable Long id) {
        Users user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully.");
    }

    @GetMapping("/all-users")
    public List<Users> getAllUsers() {
        return userService.getAllUsers();
    }
}
