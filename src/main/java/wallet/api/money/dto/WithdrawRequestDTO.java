package wallet.api.money.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class WithdrawRequestDTO {
    private Long accountId;
    private String accountCode;
    private String iban;
    private Double amount;
}
