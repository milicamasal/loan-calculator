package rs.masal.milica.loancalculator.domain.validation;

import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ValidationErrorResponse {
    private String error;
    private Integer status;
    private Date timestamp;
    private List<Violation> violations = new ArrayList<>();
}
