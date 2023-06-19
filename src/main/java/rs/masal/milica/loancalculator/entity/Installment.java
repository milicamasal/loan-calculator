package rs.masal.milica.loancalculator.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;


@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "installment")
public class Installment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "installment_no", nullable = false, updatable = false)
    private int installmentNo;

    @Column(name = "payment_amount", nullable = false, updatable = false)
    private BigDecimal paymentAmount;

    @Column(name = "principal_amount", nullable = false, updatable = false)
    private BigDecimal principalAmount;

    @Column(name = "interest_amount", nullable = false, updatable = false)
    private BigDecimal interestAmount;

    @Column(name = "balance_owed", nullable = false, updatable = false)
    private BigDecimal balanceOwed;

    @ManyToOne
    @JoinColumn(name = "loan_request", updatable = false, nullable = false)
    private LoanRequest loanRequest;

    public Installment(BigDecimal paymentAmount,
                       BigDecimal principalAmount,
                       BigDecimal interestAmount,
                       BigDecimal balanceOwed) {
        this.paymentAmount = paymentAmount;
        this.principalAmount = principalAmount;
        this.interestAmount = interestAmount;
        this.balanceOwed = balanceOwed;
    }
}
