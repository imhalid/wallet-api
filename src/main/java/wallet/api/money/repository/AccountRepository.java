package wallet.api.money.repository;

import wallet.api.money.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    // Sorgular burada yapılacak
}
