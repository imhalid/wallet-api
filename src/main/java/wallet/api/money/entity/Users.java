package wallet.api.money.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String surname;
    private String email;
    private Long nationalId;
    private Double salary;
    private LocalDateTime birthDate;
    private BigDecimal totalBalance;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Accounts> accounts;
}
