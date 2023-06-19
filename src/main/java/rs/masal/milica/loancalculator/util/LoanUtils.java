package rs.masal.milica.loancalculator.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Utility class for loan calculations.
 */
public final class LoanUtils {

    private LoanUtils() {
    }

    /**
     * Calculates the monthly interest rate based on the provided annual interest rate.
     *
     * @param annualInterestRate The annual interest rate.
     * @return The calculated monthly interest rate.
     * @throws IllegalArgumentException if the annual interest rate is not positive.
     */
    public static BigDecimal calculateMonthlyInterestRate(BigDecimal annualInterestRate) {
        if (annualInterestRate.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Annual interest rate must be positive");
        }
        return annualInterestRate
                .divide(BigDecimal.valueOf(100), 8, RoundingMode.HALF_UP)
                .divide(BigDecimal.valueOf(12), 8, RoundingMode.HALF_UP)
                .setScale(8, RoundingMode.HALF_UP);
    }

    /**
     * Calculates the monthly payment amount for a loan based on the provided loan amount,
     * monthly interest rate, and number of months.
     *
     * @param loanAmount          The loan amount.
     * @param monthlyInterestRate The monthly interest rate.
     * @param numberOfMonths      The number of months for the loan.
     * @return The calculated monthly payment amount.
     * @throws IllegalArgumentException if the loan amount, monthly interest rate,
     *                                  or number of months is not positive.
     */
    public static BigDecimal calculatePaymentAmount(BigDecimal loanAmount,
                                                    BigDecimal monthlyInterestRate,
                                                    int numberOfMonths) {
        if (loanAmount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Loan amount must be positive");
        }

        if (monthlyInterestRate.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Monthly interest rate must be positive");
        }

        if (numberOfMonths <= 0) {
            throw new IllegalArgumentException("Number of months must be positive");
        }

        BigDecimal monthlyInterestRatePlusOne = monthlyInterestRate.add(BigDecimal.ONE);
        BigDecimal rateRaisedToPower = monthlyInterestRatePlusOne.pow(numberOfMonths);
        BigDecimal numerator = loanAmount.multiply(monthlyInterestRate).multiply(rateRaisedToPower);
        BigDecimal denominator = rateRaisedToPower.subtract(BigDecimal.ONE);

        return numerator.divide(denominator, 2, RoundingMode.HALF_UP);
    }
}
