package rs.masal.milica.loancalculator.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import rs.masal.milica.loancalculator.calculator.Calculator;
import rs.masal.milica.loancalculator.domain.dto.InstallmentDto;
import rs.masal.milica.loancalculator.domain.dto.InstallmentPlanDto;
import rs.masal.milica.loancalculator.domain.dto.LoanRequestDto;
import rs.masal.milica.loancalculator.entity.Installment;
import rs.masal.milica.loancalculator.entity.LoanRequest;
import rs.masal.milica.loancalculator.repository.LoanRequestRepository;
import rs.masal.milica.loancalculator.service.LoanService;

import javax.transaction.Transactional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class LoanServiceImpl implements LoanService {
    private final LoanRequestRepository loanRequestRepository;
    private final ModelMapper modelMapper;
    private final Calculator<LoanRequestDto, InstallmentPlanDto> calculator;

    @Override
    @Transactional
    public InstallmentPlanDto calculateInstallmentPlan(LoanRequestDto loanRequestDto) {
        log.trace("Calculating installment plan for request {}", loanRequestDto);
        InstallmentPlanDto installmentPlan = calculator.calculate(loanRequestDto);
        LoanRequest loanRequest = modelMapper.map(loanRequestDto, LoanRequest.class);

        installmentPlan.getInstallments().stream()
                .map(installmentDto -> modelMapper.map(installmentDto, Installment.class))
                .forEach(loanRequest::addInstallmentPlan);

        LoanRequest savedLoanRequest = loanRequestRepository.save(loanRequest);

        return InstallmentPlanDto.builder()
                .installments(savedLoanRequest.getInstallments().stream()
                        .map(installment -> modelMapper.map(installment, InstallmentDto.class))
                        .collect(Collectors.toList()))
                .loanRequest(modelMapper.map(savedLoanRequest, LoanRequestDto.class))
                .totalInterestAmount(installmentPlan.getTotalInterestAmount())
                .totalPaidAmount(installmentPlan.getTotalPaidAmount())
                .build();
    }

}
