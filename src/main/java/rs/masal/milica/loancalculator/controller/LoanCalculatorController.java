package rs.masal.milica.loancalculator.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.masal.milica.loancalculator.domain.dto.InstallmentPlanDto;
import rs.masal.milica.loancalculator.domain.dto.LoanRequestDto;
import rs.masal.milica.loancalculator.service.LoanService;

import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/loans")
public class LoanCalculatorController {

    private final LoanService loanService;

    @PostMapping("/calculate")
    public InstallmentPlanDto calculateInstalmentPlan(@RequestBody @Valid LoanRequestDto loanRequestDto) {
        log.trace("Calculating installment plan for request {}", loanRequestDto);
        return loanService.calculateInstallmentPlan(loanRequestDto);
    }

}
