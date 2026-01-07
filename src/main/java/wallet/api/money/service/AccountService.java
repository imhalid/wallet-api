package wallet.api.money.service;

import jakarta.transaction.Transactional;
import wallet.api.money.constant.CurrencyConstant;
import wallet.api.money.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
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
    private final CurrencyService currencyService;

    private Account findAccountById(Long id) {
        return accountRepository.findById(id).orElseThrow(()-> new RuntimeException("Don't have a transaction"));    }

    private void createAndSaveTransaction(Account account, Double amount, Transaction.TransactionType type) {
        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setType(type);
        transaction.setAccount(account);
        transaction.setTimestamp(LocalDateTime.now());
        transactionRepository.save(transaction);
    }
    @Transactional
    public String deposit(DepositRequestDTO request) {
        Account account = findAccountById(request.getAccountId());

        account.setBalance(account.getBalance() + request.getAmount());
        accountRepository.save(account);

        createAndSaveTransaction(account, request.getAmount(), Transaction.TransactionType.DEPOSIT);

        return "Deposit is successful new balance: " + account.getBalance();
    }

    @Transactional
    public String withdraw(WithdrawRequestDTO draw) {
        Account account = findAccountById(draw.getAccountId());
        if (account.getBalance() < draw.getAmount()) {
            throw new RuntimeException("This account balance is too low, balance: " + account.getBalance());
        }

        account.setBalance(account.getBalance() - draw.getAmount());
        accountRepository.save(account);

        createAndSaveTransaction(account, draw.getAmount(), Transaction.TransactionType.WITHDRAW);

        return "Withdraw is successful, new balance: " + account.getBalance();
    }

    @Transactional
    public String transferTransaction(TransferRequestDTO transfer) {
        Account fromAccount = findAccountById(transfer.fromAccountId);
        Account toAccount = findAccountById(transfer.toAccountId);

        if (fromAccount.getBalance() < transfer.getAmount()) {
            throw new RuntimeException("now have a money: " + fromAccount.getBalance());
        }

        CurrencyDTO fromToCurrency = new CurrencyDTO(
                fromAccount.getCurrency(),
                toAccount.getCurrency(),
                transfer.getAmount()
        );

        Double convertedFromToAmount = currencyService.convert(fromToCurrency);

        CurrencyDTO toFromCurrency = new CurrencyDTO(
                fromAccount.getCurrency(),
                toAccount.getCurrency(),
                transfer.getAmount()
        );

        CurrencyDTO cas = CurrencyDTO.builder()
                .fromCurrency(fromToCurrency.getFromCurrency())
                .amount(transfer.getAmount())
                .toCurrency(toAccount.getCurrency())
                .build();

        Double convertedToFromAmount = currencyService.convert(toFromCurrency);

        fromAccount.setBalance(fromAccount.getBalance() - transfer.getAmount());
        accountRepository.save(fromAccount);

        createAndSaveTransaction(fromAccount, convertedFromToAmount, Transaction.TransactionType.WITHDRAW);


        toAccount.setBalance(toAccount.getBalance() + transfer.getAmount());
        accountRepository.save(toAccount);

        createAndSaveTransaction(toAccount, convertedToFromAmount, Transaction.TransactionType.WITHDRAW);


        return "Transfer başarılı! Yeni bakiyeniz: " + fromAccount.getBalance();
    }

    public Account createAccount(String ownerName, String currency) {
        currency = currency.toUpperCase();
        if (!CurrencyConstant.RATES.containsKey(currency)) {
            throw new IllegalArgumentException("Unsupported currency: " + currency);
        }

        Account account = new Account();
        account.setOwnerName(ownerName);
        account.setCurrency(currency);
        account.setBalance(0.0);
        return accountRepository.save(account);
    }

    public List<Account> getAllAccount() {
        return accountRepository.findAll();
    }

    public void deleteAccount(Long id) {
        accountRepository.deleteById(id);
    }

    public List<TransactionDTO> getAccountHistory(Long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Hesap bulunamadı"));

        return account.getTransactions().stream()
                .map(t -> {
                    TransactionDTO dto = new TransactionDTO();
                    dto.setAmount(t.getAmount());
                    dto.setType(t.getType());
                    dto.setTimestamp(t.getTimestamp());
                    dto.setMessage(t.getType() + " işlemi gerçekleştirildi.");
                    return dto;
                })
                .toList();
    }
}
