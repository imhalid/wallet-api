package wallet.api.money.service;

import jakarta.transaction.Transactional;
import org.springframework.http.RequestEntity;
import wallet.api.money.dto.AccountDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import wallet.api.money.dto.DepositRequestDTO;
import wallet.api.money.dto.TransferRequestDTO;
import wallet.api.money.dto.WithdrawRequestDTO;
import wallet.api.money.entity.Account;
import wallet.api.money.entity.Transaction;
import wallet.api.money.repository.AccountRepository;
import wallet.api.money.repository.TransactionRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    @Transactional
    public String deposit(DepositRequestDTO request) {
        Account account = accountRepository.findById(request.getAccountId()).orElseThrow(()-> new RuntimeException("Not have a account"));

        account.setBalance(account.getBalance() + request.getAmount());
        accountRepository.save(account);

        Transaction transaction = new Transaction();
        transaction.setAmount(request.getAmount());
        transaction.setType("DEPOSIT");
        transaction.setAccount(account);
        transaction.setTimestamp(LocalDateTime.now());

        transactionRepository.save(transaction);

        return "Deposit is successful new balance: " + account.getBalance();
    }

    @Transactional
    public String withdraw(WithdrawRequestDTO draw) {
        Account account = accountRepository.findById(draw.getAccountId()).orElseThrow(()-> new RuntimeException("Not have a account"));
        if (account.getBalance() < draw.getAmount()) {
            throw new RuntimeException("This account balance is too low, balance: " + account.getBalance());
        }

        account.setBalance(account.getBalance() - draw.getAmount());
        accountRepository.save(account);

        Transaction transaction = new Transaction();
        transaction.setAmount(draw.getAmount());
        transaction.setType("WITHDRAW");
        transaction.setAccount(account);
        transaction.setTimestamp(LocalDateTime.now());

        transactionRepository.save(transaction);

        return "Withdraw is successful, new balance: " + account.getBalance();
    }

    @Transactional
    public String transferTransaction(TransferRequestDTO transfer) {
        Account fromAccount = accountRepository.findById(transfer.getFromAccountId())
                .orElseThrow(() -> new RuntimeException("account not found"));
        Account toAccount = accountRepository.findById(transfer.getToAccountId())
                .orElseThrow(() -> new RuntimeException("account not found"));

        if (fromAccount.getBalance() < transfer.getAmount()) {
            throw new RuntimeException("now have a money: " + fromAccount.getBalance());
        }

        fromAccount.setBalance(fromAccount.getBalance() - transfer.getAmount());
        accountRepository.save(fromAccount);

        Transaction fromTransaction = new Transaction();
        fromTransaction.setAmount(transfer.getAmount());
        fromTransaction.setType("TRANSFER_OUT");
        fromTransaction.setTimestamp(LocalDateTime.now());
        fromTransaction.setAccount(fromAccount);
        transactionRepository.save(fromTransaction);

        toAccount.setBalance(toAccount.getBalance() + transfer.getAmount());
        accountRepository.save(toAccount);

        Transaction toTransaction = new Transaction();
        toTransaction.setAmount(transfer.getAmount());
        toTransaction.setType("TRANSFER_IN");
        toTransaction.setTimestamp(LocalDateTime.now());
        toTransaction.setAccount(toAccount);
        transactionRepository.save(toTransaction);

        return "Transfer başarılı! Yeni bakiyeniz: " + fromAccount.getBalance();
    }

    public Account createAccount(String ownerName, String currency) {
        Account account = new Account();
        account.setOwnerName(ownerName);
        account.setCurrency(currency);
        account.setBalance(0.0);
        return accountRepository.save(account);
    }

    public List<Transaction> getAccountHistory(Long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Hesap bulunamadı"));

        return account.getTransactions();
    }
}
