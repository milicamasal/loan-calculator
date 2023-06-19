package rs.masal.milica.loancalculator.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.stream.Stream;


public class LoanUtilTest {

    @ParameterizedTest
    @MethodSource("paymentTestData")
    void shouldCalculateCorrectPaymentAmount(BigDecimal loanAmount,
                                             BigDecimal monthlyInterestRate,
                                             int numberOfMonths,
                                             BigDecimal expectedPaymentAmount) {
        BigDecimal calculatedPaymentAmount = LoanUtils.calculatePaymentAmount(loanAmount, monthlyInterestRate, numberOfMonths);
        Assertions.assertEquals(0, calculatedPaymentAmount.compareTo(expectedPaymentAmount));
    }

    static Stream<Arguments> paymentTestData() {
        return Stream.of(
                Arguments.of(BigDecimal.valueOf(1000), BigDecimal.valueOf(0.00416667), 12, BigDecimal.valueOf(85.61)),
                Arguments.of(BigDecimal.valueOf(5000), BigDecimal.valueOf(0.0025), 24, BigDecimal.valueOf(214.91)),
                Arguments.of(BigDecimal.valueOf(20000), BigDecimal.valueOf(0.0025), 36, BigDecimal.valueOf(581.62)),
                Arguments.of(BigDecimal.valueOf(1500), BigDecimal.valueOf(0.00416667), 6, BigDecimal.valueOf(253.66))
        );
    }

    @ParameterizedTest
    @MethodSource("monthlyInterestTestData")
    void shouldCalculateCorrectMonthlyInterestRate(BigDecimal annualInterestRate,
                                                   BigDecimal expectedMonthlyInterestRate) {
        BigDecimal calculatedMonthlyInterestRate = LoanUtils.calculateMonthlyInterestRate(annualInterestRate);
        Assertions.assertEquals(0, calculatedMonthlyInterestRate.compareTo(expectedMonthlyInterestRate));
    }

    static Stream<Arguments> monthlyInterestTestData() {
        return Stream.of(
                Arguments.of(BigDecimal.valueOf(5), BigDecimal.valueOf(0.00416667)),
                Arguments.of(BigDecimal.valueOf(5), BigDecimal.valueOf(0.00416667)),
                Arguments.of(BigDecimal.valueOf(3), BigDecimal.valueOf(0.0025)),
                Arguments.of(BigDecimal.valueOf(100), BigDecimal.valueOf(0.08333333))
        );
    }
}
