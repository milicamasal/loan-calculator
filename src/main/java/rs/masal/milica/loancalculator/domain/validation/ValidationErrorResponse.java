package rs.masal.milica.loancalculator.domain.validation;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ValidationErrorResponse {
    private String error;
    private Integer status;
    private Date timestamp;
    private List<Violation> violations = new ArrayList<>();
}
