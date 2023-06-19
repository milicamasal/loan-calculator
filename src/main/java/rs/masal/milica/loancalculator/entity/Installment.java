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
@Table(name = "INSTALLMENT")
public class Installment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "INSTALLMENT_NO", nullable = false, updatable = false)
    private int installmentNo;

    @Column(name = "PAYMENT_AMOUNT", nullable = false, updatable = false)
    private BigDecimal paymentAmount;

    @Column(name = "PRINCIPAL_AMOUNT", nullable = false, updatable = false)
    private BigDecimal principalAmount;

    @Column(name = "INTEREST_AMOUNT", nullable = false, updatable = false)
    private BigDecimal interestAmount;

    @Column(name = "BALANCE_OWED", nullable = false, updatable = false)
    private BigDecimal balanceOwed;

    @ManyToOne
    @JoinColumn(name = "LOAN_REQUEST", updatable = false, nullable = false)
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
