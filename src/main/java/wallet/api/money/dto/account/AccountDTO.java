package wallet.api.money.dto.account;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountDTO {
    private Double balance;
    private String currency;
    private String accountCode;
    private String iban;
    private String accountName;
    private Long userId;
}
