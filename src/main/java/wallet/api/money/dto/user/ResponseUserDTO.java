package wallet.api.money.dto.user;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ResponseUserDTO {
    private String name;
    private String surname;
    private String email;
    private String userJob;
    private String salary;
    private LocalDateTime birthDate;
}
