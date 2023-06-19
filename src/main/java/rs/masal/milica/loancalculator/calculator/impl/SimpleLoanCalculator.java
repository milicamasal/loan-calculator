package rs.masal.milica.loancalculator.calculator.impl;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import rs.masal.milica.loancalculator.calculator.Calculator;
import rs.masal.milica.loancalculator.domain.dto.InstallmentDto;
import rs.masal.milica.loancalculator.domain.dto.InstallmentPlanDto;
import rs.masal.milica.loancalculator.domain.dto.LoanRequestDto;
import rs.masal.milica.loancalculator.util.LoanUtils;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
@Validated
public class SimpleLoanCalculator implements Calculator<LoanRequestDto, InstallmentPlanDto> {

    @Override
    public InstallmentPlanDto calculate(@Valid LoanRequestDto request) {

        int numberOfMonths = request.getNumberOfMonths();
        BigDecimal loanAmount = request.getLoanAmount();

        BigDecimal monthlyInterestRate = LoanUtils.calculateMonthlyInterestRate(request.getAnnualInterestRate());
        BigDecimal paymentAmount = LoanUtils.calculatePaymentAmount(loanAmount, monthlyInterestRate, numberOfMonths);

        List<InstallmentDto> installments = calculateInstallmentsWithoutTheLastOne(numberOfMonths, loanAmount, monthlyInterestRate, paymentAmount);
        InstallmentDto lastInstallment = calculateLastInstallment(numberOfMonths, installments.get(installments.size() - 1).getBalanceOwed(), monthlyInterestRate);
        installments.add(lastInstallment);

        BigDecimal totalPaidAmount = installments.stream().map(InstallmentDto::getPaymentAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add).setScale(2, RoundingMode.HALF_UP);
        BigDecimal totalInterestRate = installments.stream().map(InstallmentDto::getInterestAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add).setScale(2, RoundingMode.HALF_UP);

        return InstallmentPlanDto.builder()
                .installments(installments)
                .loanRequest(request)
                .totalPaidAmount(totalPaidAmount)
                .totalInterestAmount(totalInterestRate)
                .build();
    }

    private List<InstallmentDto> calculateInstallmentsWithoutTheLastOne(int numberOfMonths,
                                                                        BigDecimal balanceOwed,
                                                                        BigDecimal monthlyInterestRate,
                                                                        BigDecimal paymentAmount) {
        List<InstallmentDto> installmentPlan = new ArrayList<>();
        for (int i = 1; i < numberOfMonths; i++) {
            BigDecimal interestAmount = monthlyInterestRate.multiply(balanceOwed).setScale(2, RoundingMode.HALF_UP);
            BigDecimal principalAmount = paymentAmount.subtract(interestAmount).setScale(2, RoundingMode.HALF_UP);
            balanceOwed = balanceOwed.subtract(principalAmount).setScale(2, RoundingMode.HALF_UP);
            InstallmentDto installmentDto = new InstallmentDto(i, paymentAmount, principalAmount, interestAmount, balanceOwed);
            installmentPlan.add(installmentDto);
        }
        return installmentPlan;
    }

    private InstallmentDto calculateLastInstallment(int numberOfMonths, BigDecimal balanceOwed, BigDecimal monthlyInterestRate) {
        BigDecimal interestAmount = monthlyInterestRate.multiply(balanceOwed).setScale(2, RoundingMode.HALF_UP);
        return InstallmentDto.builder()
                .installmentNo(numberOfMonths)
                .paymentAmount(interestAmount.add(balanceOwed))
                .principalAmount(balanceOwed)
                .interestAmount(interestAmount)
                .balanceOwed(BigDecimal.ZERO)
                .build();
    }

}
