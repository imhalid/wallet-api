package wallet.api.money.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Entity
@Table(name = "account")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String ownerName;
    private Double balance;
    private String currency;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<Transaction> transactions;



    public void deposit(Double amount) {
        this.balance += amount;
    }

    public void  withdraw(Double amount) {
        if(this.balance < amount) {
            throw new RuntimeException("Don't have money");
        }

        this.balance -= amount;
    }

}

