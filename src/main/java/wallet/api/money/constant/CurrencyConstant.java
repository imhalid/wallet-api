package wallet.api.money.constant;

import java.util.Map;

public class CurrencyConstant
{
    public static final Map<String, Double> RATES = Map.of(
        "USD", 1.0,
        "EUR", 0.85,
        "JPY", 110.0,
        "GBP", 0.75,
        "TRY", 8.5
    );
}
