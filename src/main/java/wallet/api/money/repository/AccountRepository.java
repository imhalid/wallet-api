package wallet.api.money.repository;

import wallet.api.money.entity.Accounts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.SecureRandom;

@Repository
public interface AccountRepository extends JpaRepository<Accounts, Long> {
    // Sorgular burada yapılacak

    public enum Currency {
        TRY, USD, EUR, GBP
    }
}
