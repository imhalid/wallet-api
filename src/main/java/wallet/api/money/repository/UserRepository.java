package wallet.api.money.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wallet.api.money.entity.Users;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
}
