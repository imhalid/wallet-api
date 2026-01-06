package wallet.api.money.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class TransferRequestDTO {
    public Long fromAccountId;
    public Long toAccountId;
    public Double amount;
    public LocalDateTime timestamp;
}
