package wallet.api.money.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class WithdrawRequestDTO {
    private Long accountId;
    private Double amount;
}
