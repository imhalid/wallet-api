package wallet.api.money.repository;

import wallet.api.money.entity.Accounts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Accounts, Long> {
    // Sorgular burada yapılacak
}
