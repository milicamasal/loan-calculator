package rs.masal.milica.loancalculator.util;

import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.math.RoundingMode;

public final class LoanUtils {

    private LoanUtils() {
    }

    public static BigDecimal calculateMonthlyInterestRate(BigDecimal annualInterestRate) {
        return annualInterestRate
                .divide(BigDecimal.valueOf(100), 8, RoundingMode.HALF_UP)
                .divide(BigDecimal.valueOf(12), 8, RoundingMode.HALF_UP)
                .setScale(8, RoundingMode.HALF_UP);
    }

    public static BigDecimal calculatePaymentAmount(BigDecimal loanAmount,
                                                    BigDecimal monthlyInterestRate,
                                                    int numberOfMonths) {
        BigDecimal monthlyInterestRatePlusOne = monthlyInterestRate.add(BigDecimal.ONE);
        BigDecimal rateRaisedToPower = monthlyInterestRatePlusOne.pow(numberOfMonths);
        BigDecimal numerator = loanAmount.multiply(monthlyInterestRate).multiply(rateRaisedToPower);
        BigDecimal denominator = rateRaisedToPower.subtract(BigDecimal.ONE);

        return numerator.divide(denominator, 2, RoundingMode.HALF_UP);
    }
}
