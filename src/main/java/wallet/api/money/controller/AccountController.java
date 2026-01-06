package wallet.api.money.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wallet.api.money.dto.DepositRequestDTO;
import wallet.api.money.dto.TransferRequestDTO;
import wallet.api.money.dto.WithdrawRequestDTO;
import wallet.api.money.entity.Account;
import wallet.api.money.entity.Transaction;
import wallet.api.money.service.AccountService;

import java.util.List;

@RestController
@RequestMapping("/api/wallet")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @PostMapping("/create-account")
    public ResponseEntity<Account> createAccount(@RequestBody Account account) {
        return ResponseEntity.ok(accountService.createAccount(account.getOwnerName(), account.getCurrency()));
    }

    @PostMapping("/deposit")
    public ResponseEntity<String> deposit(@RequestBody DepositRequestDTO request) {
        return ResponseEntity.ok(accountService.deposit(request));
    }

    @PostMapping("/withdraw")
    public ResponseEntity<String> withdraw(@RequestBody WithdrawRequestDTO request) {
        return ResponseEntity.ok(accountService.withdraw(request));
    }

    @PostMapping("/transfer")
    public ResponseEntity<String> transfer(@RequestBody TransferRequestDTO request) {
        return ResponseEntity.ok(accountService.transferTransaction(request));
    }

    @GetMapping("/account-history/{accountId}")
    public ResponseEntity<List<Transaction>> getHistory(@PathVariable Long accountId) {
        return ResponseEntity.ok(accountService.getAccountHistory(accountId));
    }
}
