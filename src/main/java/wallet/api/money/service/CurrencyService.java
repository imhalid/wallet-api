package wallet.api.money.service;

import org.springframework.stereotype.Service;
import wallet.api.money.constant.CurrencyConstant;
import wallet.api.money.dto.CurrencyDTO;

@Service
public class CurrencyService {

    public Double convert(CurrencyDTO currency) {
        if (currency.getFromCurrency().equals(currency.getToCurrency())) return currency.getAmount();

        Double fromRate = CurrencyConstant.RATES.get(currency.getFromCurrency());
        Double toRate = CurrencyConstant.RATES.get(currency.getToCurrency());

        if (fromRate == null || toRate == null) {
            throw new IllegalArgumentException("Unsupported currency code");
        }
        return (currency.getAmount() / fromRate) * toRate;
    }
}
