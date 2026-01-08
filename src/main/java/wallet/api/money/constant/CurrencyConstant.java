package wallet.api.money.constant;

import java.util.Map;

public class CurrencyConstant
{
    public static final Map<String, Double> RATES = Map.of(
            // Baz: USD = 1.0
            "USD", 1.00,                        // 1 USD
            "EUR", 1.1693,                     // 1 EUR ≈ 1.1693 USD (EUR/USD)
            "JPY", 156.38,                     // 1 USD ≈ 156.38 JPY
            "GBP", 1.3500,                     // 1 GBP ≈ 1.35 USD
            "TRY", 43.04                       // 1 USD ≈ 43.04 TRY
    );
}
