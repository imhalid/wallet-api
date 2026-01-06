package wallet.api.money.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DepositRequestDTO {
    private Long accountId;
    private Double amount;
}
