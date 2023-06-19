package rs.masal.milica.loancalculator.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.bind.MethodArgumentNotValidException;
import rs.masal.milica.loancalculator.data.TestData;
import rs.masal.milica.loancalculator.domain.dto.InstallmentPlanDto;
import rs.masal.milica.loancalculator.service.LoanService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = LoanCalculatorController.class)
public class LoanCalculatorControllerTest {
    private final String LOAN_CALCULATOR_URL = "/api/v1/loans/calculate";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private LoanService loanService;


    @Test
    public void submitsValidLoanRequest_Returns200() throws Exception {
        String requestBody = objectMapper.writeValueAsString(TestData.LOAN_REQUEST_DTO);

        Mockito.when(loanService.calculateInstallmentPlan(TestData.LOAN_REQUEST_DTO))
                .thenReturn(InstallmentPlanDto.builder()
                        .installments(List.of(TestData.INSTALLMENT_DTO_1, TestData.INSTALLMENT_DTO_2))
                        .loanRequest(TestData.LOAN_REQUEST_DTO)
                        .totalPaidAmount(BigDecimal.TEN)
                        .totalInterestAmount(BigDecimal.ONE)
                        .build());

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .post(LOAN_CALCULATOR_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk())
                .andDo(print());

        // Add assertions to validate the response
        resultActions
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalPaidAmount").value(10))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalInterestAmount").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.loanRequest.loanAmount").value(1000))
                .andExpect(MockMvcResultMatchers.jsonPath("$.loanRequest.annualInterestRate").value(5))
                .andExpect(MockMvcResultMatchers.jsonPath("$.loanRequest.numberOfMonths").value(10))
                .andExpect(MockMvcResultMatchers.jsonPath("$.installments").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.installments.length()").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.installments[0].installmentNo").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.installments[0].paymentAmount").value(102.31))
                .andExpect(MockMvcResultMatchers.jsonPath("$.installments[0].principalAmount").value(98.14))
                .andExpect(MockMvcResultMatchers.jsonPath("$.installments[0].interestAmount").value(4.17))
                .andExpect(MockMvcResultMatchers.jsonPath("$.installments[0].balanceOwed").value(901.86))
                .andExpect(MockMvcResultMatchers.jsonPath("$.installments[1].installmentNo").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.installments[1].paymentAmount").value(102.31))
                .andExpect(MockMvcResultMatchers.jsonPath("$.installments[1].principalAmount").value(98.55))
                .andExpect(MockMvcResultMatchers.jsonPath("$.installments[1].interestAmount").value(3.76))
                .andExpect(MockMvcResultMatchers.jsonPath("$.installments[1].balanceOwed").value(803.31));
    }


    @Test
    public void submitsNegativeLoanAmount_Returns400() throws Exception {
        String requestBody = objectMapper.writeValueAsString(TestData.LOAN_REQUEST_NEGATIVE_LOAN_AMOUNT_DTO);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .post(LOAN_CALCULATOR_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isBadRequest());

        assertEquals(MethodArgumentNotValidException.class,
                Objects.requireNonNull(resultActions.andReturn().getResolvedException()).getClass());
    }

    @Test
    public void submitsNegativeAnnualInterestRate_Returns400() throws Exception {
        String requestBody = objectMapper.writeValueAsString(TestData.LOAN_REQUEST_NEGATIVE_ANNUAL_INTEREST_RATE);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .post(LOAN_CALCULATOR_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isBadRequest());
        assertEquals(MethodArgumentNotValidException.class,
                Objects.requireNonNull(resultActions.andReturn().getResolvedException()).getClass());
    }

    @Test
    public void submitsZeroNumberOfMonths_Returns400() throws Exception {
        String requestBody = objectMapper.writeValueAsString(TestData.LOAN_REQUEST_ZERO_NUMBER_OF_MONTHS);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .post(LOAN_CALCULATOR_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isBadRequest());

        assertEquals(MethodArgumentNotValidException.class,
                Objects.requireNonNull(resultActions.andReturn().getResolvedException()).getClass());
    }

    @Test
    public void submitsEmptyBody_Returns400() throws Exception {
        final String emptyBody = "{}";
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .post(LOAN_CALCULATOR_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(emptyBody))
                .andExpect(status().isBadRequest());

        assertEquals(MethodArgumentNotValidException.class,
                Objects.requireNonNull(resultActions.andReturn().getResolvedException()).getClass());
    }
}
