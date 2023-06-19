package rs.masal.milica.loancalculator.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import rs.masal.milica.loancalculator.calculator.impl.SimpleLoanCalculator;
import rs.masal.milica.loancalculator.data.TestData;
import rs.masal.milica.loancalculator.domain.dto.InstallmentDto;
import rs.masal.milica.loancalculator.domain.dto.InstallmentPlanDto;

import javax.validation.ConstraintViolationException;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
class SimpleLoanCalculatorTest {
    private final Locale LOCALE_EN = new Locale("en");

    @Autowired
    SimpleLoanCalculator simpleLoanCalculator;

    @Autowired
    MessageSource messageSource;


    @Test
    void submitsLoanRequest_ReturnsCorrectInstallmentPlan() {
        InstallmentPlanDto expectedInstallmentPlan = TestData.INSTALLMENT_PLAN_DTO;
        InstallmentPlanDto calculatedInstallmentPlan = simpleLoanCalculator.calculate(TestData.LOAN_REQUEST_DTO);

        Assertions.assertEquals(0, expectedInstallmentPlan.getTotalPaidAmount().compareTo(calculatedInstallmentPlan.getTotalPaidAmount()));
        Assertions.assertEquals(0, expectedInstallmentPlan.getTotalInterestAmount().compareTo(calculatedInstallmentPlan.getTotalInterestAmount()));
        Assertions.assertEquals(0, expectedInstallmentPlan.getLoanRequest().getLoanAmount().compareTo(calculatedInstallmentPlan.getLoanRequest().getLoanAmount()));
        Assertions.assertEquals(0, expectedInstallmentPlan.getLoanRequest().getAnnualInterestRate().compareTo(calculatedInstallmentPlan.getLoanRequest().getAnnualInterestRate()));
        Assertions.assertEquals(expectedInstallmentPlan.getLoanRequest().getNumberOfMonths(), calculatedInstallmentPlan.getLoanRequest().getNumberOfMonths());

        Assertions.assertEquals(expectedInstallmentPlan.getInstallments().size(), calculatedInstallmentPlan.getInstallments().size());
        for (int i = 0; i < expectedInstallmentPlan.getInstallments().size(); i++) {
            InstallmentDto installment1 = expectedInstallmentPlan.getInstallments().get(i);
            InstallmentDto installment2 = calculatedInstallmentPlan.getInstallments().get(i);
            Assertions.assertEquals(installment1.getInstallmentNo(), installment2.getInstallmentNo());
            Assertions.assertEquals(0, installment1.getPaymentAmount().compareTo(installment2.getPaymentAmount()));
            Assertions.assertEquals(0, installment1.getPrincipalAmount().compareTo(installment2.getPrincipalAmount()));
            Assertions.assertEquals(0, installment1.getInterestAmount().compareTo(installment2.getInterestAmount()));
            Assertions.assertEquals(0, installment1.getBalanceOwed().compareTo(installment2.getBalanceOwed()));
        }

    }

    @Test
    void submitsNegativeLoanAmount_ThrowsException() {
        Exception exception = assertThrows(ConstraintViolationException.class, () -> {
            simpleLoanCalculator.calculate(TestData.LOAN_REQUEST_NEGATIVE_LOAN_AMOUNT_DTO);
        });

        String actualMessage = exception.getMessage();

        assertTrue(actualMessage
                .contains(messageSource.getMessage("loan-amount.positive", null, LOCALE_EN)));
    }

    @Test
    void submitsNegativeAnnualInterestRate_ThrowsException() {
        Exception exception = assertThrows(ConstraintViolationException.class, () -> {
            simpleLoanCalculator.calculate(TestData.LOAN_REQUEST_NEGATIVE_ANNUAL_INTEREST_RATE);
        });

        String actualMessage = exception.getMessage();

        assertTrue(actualMessage
                .contains(messageSource.getMessage("annual-interest-rate.positive", null, LOCALE_EN)));
    }

    @Test
    void submitsZeroNumberOfMonths_ThrowsException() {
        Exception exception = assertThrows(ConstraintViolationException.class, () -> {
            simpleLoanCalculator.calculate(TestData.LOAN_REQUEST_ZERO_NUMBER_OF_MONTHS);
        });

        String actualMessage = exception.getMessage();

        assertTrue(actualMessage
                .contains(messageSource.getMessage("number-of-months.positive", null, LOCALE_EN)));
    }
}
