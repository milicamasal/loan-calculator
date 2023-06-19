package rs.masal.milica.loancalculator.domain.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InstallmentPlanDto {
    private BigDecimal totalPaidAmount;
    private BigDecimal totalInterestAmount;
    private LoanRequestDto loanRequest;
    private List<InstallmentDto> installments;
}
