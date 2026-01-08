package wallet.api.money.dto;

import lombok.Getter;
import lombok.Setter;
import wallet.api.money.entity.Transactions;

import java.time.LocalDateTime;

@Getter
@Setter
public class TransactionDTO {
    private Double amount;
    private Transactions.TransactionType type;
    private LocalDateTime timestamp;
    private String message;
}
