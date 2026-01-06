package service;

import dto.AccountDTO;
import entity.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import repository.AccountRepository;

import java.util.*;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;

    public ResponseEntity<String> createAccount(AccountDTO accountDTO) {

        return ResponseEntity.accepted().body("successful");
    }
}
