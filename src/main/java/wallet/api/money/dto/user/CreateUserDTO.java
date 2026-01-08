package wallet.api.money.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@AllArgsConstructor
@Builder
public class CreateUserDTO {

    @NotBlank(message = "Name cannot be empty")
    private String name;

    @NotBlank(message = "Surname cannot be empty")
    private String surname;

    @NotNull(message = "National ID is required")
    @Positive(message = "National ID must be a positive number")
    private Long nationalId;

    @Email(message = "Email format is invalid")
    @NotBlank(message = "Email cannot be empty")
    private String email;

    @NotNull(message = "Salary is required")
    @Positive(message = "Salary must be greater than zero")
    private Double salary;

    @NotNull(message = "Birth date is required")
    private LocalDateTime birthDate;
}

