package rs.masal.milica.loancalculator.data;

import rs.masal.milica.loancalculator.domain.dto.InstallmentDto;
import rs.masal.milica.loancalculator.domain.dto.InstallmentPlanDto;
import rs.masal.milica.loancalculator.domain.dto.LoanRequestDto;

import java.math.BigDecimal;
import java.util.List;

public class TestData {
    public static final LoanRequestDto LOAN_REQUEST_DTO = LoanRequestDto.builder()
            .loanAmount(BigDecimal.valueOf(1000))
            .annualInterestRate(BigDecimal.valueOf(5))
            .numberOfMonths(10)
            .build();

    public static final LoanRequestDto LOAN_REQUEST_NEGATIVE_LOAN_AMOUNT_DTO = LoanRequestDto.builder()
            .loanAmount(BigDecimal.valueOf(-1000))
            .annualInterestRate(BigDecimal.valueOf(5))
            .numberOfMonths(10)
            .build();

    public static final LoanRequestDto LOAN_REQUEST_NEGATIVE_ANNUAL_INTEREST_RATE = LoanRequestDto.builder()
            .loanAmount(BigDecimal.valueOf(100))
            .annualInterestRate(BigDecimal.valueOf(-5))
            .numberOfMonths(10)
            .build();

    public static final LoanRequestDto LOAN_REQUEST_ZERO_NUMBER_OF_MONTHS = LoanRequestDto.builder()
            .loanAmount(BigDecimal.valueOf(100))
            .annualInterestRate(BigDecimal.valueOf(5))
            .numberOfMonths(0)
            .build();

    public static final InstallmentDto INSTALLMENT_DTO_1 = InstallmentDto.builder()
            .installmentNo(1)
            .paymentAmount(BigDecimal.valueOf(102.31))
            .principalAmount(BigDecimal.valueOf(98.14))
            .interestAmount(BigDecimal.valueOf(4.17))
            .balanceOwed(BigDecimal.valueOf(901.86))
            .build();

    public static final InstallmentDto INSTALLMENT_DTO_2 = InstallmentDto.builder()
            .installmentNo(2)
            .paymentAmount(BigDecimal.valueOf(102.31))
            .principalAmount(BigDecimal.valueOf(98.55))
            .interestAmount(BigDecimal.valueOf(3.76))
            .balanceOwed(BigDecimal.valueOf(803.31))
            .build();

    public static final InstallmentDto INSTALLMENT_DTO_3 = InstallmentDto.builder()
            .installmentNo(3)
            .paymentAmount(BigDecimal.valueOf(102.31))
            .principalAmount(BigDecimal.valueOf(98.96))
            .interestAmount(BigDecimal.valueOf(3.35))
            .balanceOwed(BigDecimal.valueOf(704.35))
            .build();

    public static final InstallmentDto INSTALLMENT_DTO_4 = InstallmentDto.builder()
            .installmentNo(4)
            .paymentAmount(BigDecimal.valueOf(102.31))
            .principalAmount(BigDecimal.valueOf(99.38))
            .interestAmount(BigDecimal.valueOf(2.93))
            .balanceOwed(BigDecimal.valueOf(604.97))
            .build();

    public static final InstallmentDto INSTALLMENT_DTO_5 = InstallmentDto.builder()
            .installmentNo(5)
            .paymentAmount(BigDecimal.valueOf(102.31))
            .principalAmount(BigDecimal.valueOf(99.79))
            .interestAmount(BigDecimal.valueOf(2.52))
            .balanceOwed(BigDecimal.valueOf(505.18))
            .build();

    public static final InstallmentDto INSTALLMENT_DTO_6 = InstallmentDto.builder()
            .installmentNo(6)
            .paymentAmount(BigDecimal.valueOf(102.31))
            .principalAmount(BigDecimal.valueOf(100.21))
            .interestAmount(BigDecimal.valueOf(2.10))
            .balanceOwed(BigDecimal.valueOf(404.97))
            .build();

    public static final InstallmentDto INSTALLMENT_DTO_7 = InstallmentDto.builder()
            .installmentNo(7)
            .paymentAmount(BigDecimal.valueOf(102.31))
            .principalAmount(BigDecimal.valueOf(100.62))
            .interestAmount(BigDecimal.valueOf(1.69))
            .balanceOwed(BigDecimal.valueOf(304.35))
            .build();

    public static final InstallmentDto INSTALLMENT_DTO_8 = InstallmentDto.builder()
            .installmentNo(8)
            .paymentAmount(BigDecimal.valueOf(102.31))
            .principalAmount(BigDecimal.valueOf(101.04))
            .interestAmount(BigDecimal.valueOf(1.27))
            .balanceOwed(BigDecimal.valueOf(203.31))
            .build();

    public static final InstallmentDto INSTALLMENT_DTO_9 = InstallmentDto.builder()
            .installmentNo(9)
            .paymentAmount(BigDecimal.valueOf(102.31))
            .principalAmount(BigDecimal.valueOf(101.46))
            .interestAmount(BigDecimal.valueOf(0.85))
            .balanceOwed(BigDecimal.valueOf(101.85))
            .build();

    public static final InstallmentDto INSTALLMENT_DTO_10 = InstallmentDto.builder()
            .installmentNo(10)
            .paymentAmount(BigDecimal.valueOf(102.27))
            .principalAmount(BigDecimal.valueOf(101.85))
            .interestAmount(BigDecimal.valueOf(0.42))
            .balanceOwed(BigDecimal.ZERO)
            .build();

    public static List<InstallmentDto> INSTALLMENTS = List.of(
            INSTALLMENT_DTO_1, INSTALLMENT_DTO_2,
            INSTALLMENT_DTO_3, INSTALLMENT_DTO_4,
            INSTALLMENT_DTO_5, INSTALLMENT_DTO_6,
            INSTALLMENT_DTO_7, INSTALLMENT_DTO_8,
            INSTALLMENT_DTO_9, INSTALLMENT_DTO_10
    );

    public static final InstallmentPlanDto INSTALLMENT_PLAN_DTO = InstallmentPlanDto.builder()
            .totalPaidAmount(BigDecimal.valueOf(1023.06))
            .totalInterestAmount(BigDecimal.valueOf(23.06))
            .loanRequest(LOAN_REQUEST_DTO)
            .installments(INSTALLMENTS)
            .build();

    public static final InstallmentPlanDto INSTALLMENT_PLAN_DTO_TWO_INSTALLMENTS = InstallmentPlanDto.builder()
            .totalPaidAmount(BigDecimal.valueOf(1023.06))
            .totalInterestAmount(BigDecimal.valueOf(23.06))
            .loanRequest(LOAN_REQUEST_DTO)
            .installments(List.of(INSTALLMENT_DTO_1, INSTALLMENT_DTO_2))
            .build();

}
