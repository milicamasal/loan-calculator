package rs.masal.milica.loancalculator.service;

import rs.masal.milica.loancalculator.domain.dto.InstallmentPlanDto;
import rs.masal.milica.loancalculator.domain.dto.LoanRequestDto;

public interface LoanService {
    InstallmentPlanDto calculateInstallmentPlan(LoanRequestDto loanRequest);
}
