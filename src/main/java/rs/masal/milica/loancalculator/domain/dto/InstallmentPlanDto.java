package rs.masal.milica.loancalculator.domain.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class InstallmentPlanDto {
    private BigDecimal totalPaidAmount;
    private BigDecimal totalInterestAmount;
    private LoanRequestDto loanRequest;
    private List<InstallmentDto> installments;
}
