package wallet.api.money.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class CurrencyDTO {
    private String fromCurrency;
    private String toCurrency;
    private Double amount;
}
