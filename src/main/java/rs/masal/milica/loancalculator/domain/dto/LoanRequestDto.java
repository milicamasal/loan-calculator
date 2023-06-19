package rs.masal.milica.loancalculator.domain.dto;


import lombok.*;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class LoanRequestDto {
    @Positive(message = "{loan-amount.positive}")
    @NotNull(message = "{loan-amount.required}")
    private BigDecimal loanAmount;
    @Positive(message = "{annual-interest-rate.positive}")
    @DecimalMax(value = "100")
    @NotNull(message = "{annual-interest-rate.required}")
    private BigDecimal annualInterestRate;
    @Positive(message = "{number-of-months.positive}")
    @Max(value = 600, message = "{number-of-months.less-than-600}")
    private int numberOfMonths;
}
