package wallet.api.money.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import wallet.api.money.dto.user.CreateUserDTO;
import wallet.api.money.entity.Users;
import wallet.api.money.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public void createUser(CreateUserDTO dto) {

        Users user = new Users();
        user.setName(dto.getName());
        user.setSurname(dto.getSurname());
        user.setEmail(dto.getEmail());
        user.setSalary(dto.getSalary());
        user.setBirthDate(dto.getBirthDate());
        user.setNationalId(dto.getNationalId());
        userRepository.save(user);
    }

    public Users getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + id));
    }

    public List<Users> getAllUsers() {
        return userRepository.findAll();
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
