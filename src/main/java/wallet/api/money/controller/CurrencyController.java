package wallet.api.money.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wallet.api.money.dto.CurrencyDTO;
import wallet.api.money.service.CurrencyService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/wallet/currency")
public class CurrencyController {

    private final CurrencyService currencyService;
    @PostMapping("/convert")
    public ResponseEntity<Double> convertCurrency(@RequestBody CurrencyDTO currency) {
        Double convertedAmount = currencyService.convert(currency);
        return ResponseEntity.ok(convertedAmount);
    }
}
