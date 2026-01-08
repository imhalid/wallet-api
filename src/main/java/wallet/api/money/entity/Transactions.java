package wallet.api.money.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transactions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double amount;
    private Long fromAccountId;
    private Long toAccountId;

    @Enumerated(EnumType.STRING)
    private TransactionType type;

    private LocalDateTime timestamp;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "account_id")
    private Accounts account;


    public enum TransactionType {
        DEPOSIT,
        WITHDRAW,
        TRANSFER_IN,
        TRANSFER_OUT
    }
}
