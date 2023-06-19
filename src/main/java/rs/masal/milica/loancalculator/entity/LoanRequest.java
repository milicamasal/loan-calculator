package rs.masal.milica.loancalculator.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "loan_request")
public class LoanRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "loan_amount", nullable = false)
    private BigDecimal loanAmount;

    @Column(name = "annual_interest_rate", nullable = false)
    private BigDecimal annualInterestRate;

    @Column(name = "number_of_months", nullable = false)
    private Integer numberOfMonths;

    @OneToMany(mappedBy = "loanRequest", orphanRemoval = true,
            fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Installment> installments = new ArrayList<>();

    public LoanRequest(BigDecimal loanAmount, BigDecimal annualInterestRate, Integer numberOfMonths) {
        this.loanAmount = loanAmount;
        this.annualInterestRate = annualInterestRate;
        this.numberOfMonths = numberOfMonths;
    }

    public void addInstallmentPlan(Installment installment) {
        installments.add(installment);
        installment.setLoanRequest(this);
    }

    public void removeInstallmentPlan(Installment installment) {
        installments.remove(installment);
        installment.setLoanRequest(null);
    }

}


