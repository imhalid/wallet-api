package wallet.api.money.service;

import jakarta.transaction.Transactional;
import wallet.api.money.constant.CurrencyConstant;
import wallet.api.money.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import wallet.api.money.dto.account.CreateAccountDTO;
import wallet.api.money.entity.Accounts;
import wallet.api.money.entity.Transactions;
import wallet.api.money.entity.Users;
import wallet.api.money.repository.AccountRepository;
import wallet.api.money.repository.TransactionRepository;
import wallet.api.money.utils.WalletUtils;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final CurrencyService currencyService;
    private final WalletUtils walletUtils;
    private final UserService userService;

    private Accounts findAccountById(Long id) {
        return accountRepository.findById(id).orElseThrow(()-> new RuntimeException("Don't have a transaction"));    }

    private void createAndSaveTransaction(Accounts account, Double amount, Transactions.TransactionType type) {
        Transactions transaction = new Transactions();
        transaction.setAmount(amount);
        transaction.setType(type);
        transaction.setAccount(account);
        transaction.setTimestamp(LocalDateTime.now());
        transactionRepository.save(transaction);
    }

    @Transactional
    public String deposit(DepositRequestDTO request) {
        Accounts account = findAccountById(request.getAccountId());

        account.setBalance(account.getBalance() + request.getAmount());
        accountRepository.save(account);

        createAndSaveTransaction(account, request.getAmount(), Transactions.TransactionType.DEPOSIT);

        return "Deposit is successful new balance: " + account.getBalance();
    }

    @Transactional
    public String withdraw(WithdrawRequestDTO draw) {
        Accounts account = findAccountById(draw.getAccountId());
        if (account.getBalance() < draw.getAmount()) {
            throw new RuntimeException("This account balance is too low, balance: " + account.getBalance());
        }

        account.setBalance(account.getBalance() - draw.getAmount());
        accountRepository.save(account);

        createAndSaveTransaction(account, draw.getAmount(), Transactions.TransactionType.WITHDRAW);

        return "Withdraw is successful, new balance: " + account.getBalance();
    }

    @Transactional
    public String transferTransaction(TransferRequestDTO transfer) {
        Accounts fromAccount = findAccountById(transfer.fromAccountId);
        Accounts toAccount = findAccountById(transfer.toAccountId);

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

        Double convertedToFromAmount = currencyService.convert(toFromCurrency);

        fromAccount.setBalance(fromAccount.getBalance() - transfer.getAmount());
        accountRepository.save(fromAccount);

        createAndSaveTransaction(fromAccount, convertedFromToAmount, Transactions.TransactionType.WITHDRAW);


        toAccount.setBalance(toAccount.getBalance() + convertedToFromAmount);
        accountRepository.save(toAccount);

        createAndSaveTransaction(toAccount, convertedToFromAmount, Transactions.TransactionType.WITHDRAW);


        return "Transfer başarılı! Yeni bakiyeniz: " + fromAccount.getBalance()  + " " + toAccount.getBalance();
    }

    public Accounts createAccount(CreateAccountDTO dto) {
        Users user = userService.getUserById(dto.getUserId());
        AccountRepository.Currency currency = dto.getCurrency();
        if (!CurrencyConstant.RATES.containsKey(currency.toString())) {
            throw new IllegalArgumentException("Unsupported currency: " + currency);
        }

        String ownerName = dto.getAccountName();
        String code = WalletUtils.generateAccountCode(dto.getUserId(), dto.getCurrency());
        String iban = WalletUtils.generateTrIban("006" ,code);

        Accounts account = new Accounts();
        account.setOwnerName(ownerName);
        account.setCurrency(currency.toString());
        account.setIban(iban);
        account.setAccountCode(code);
        account.setUser(user);

        account.setBalance(0.0);
        return accountRepository.save(account);
    }

    public List<Accounts> getAllAccount() {
        return accountRepository.findAll();
    }

    public void deleteAccount(Long id) {
        accountRepository.deleteById(id);
    }

    public List<TransactionDTO> getAccountHistory(Long accountId) {
        Accounts account = accountRepository.findById(accountId)
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
