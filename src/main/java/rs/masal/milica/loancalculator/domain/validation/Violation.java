package rs.masal.milica.loancalculator.domain.validation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class Violation {
    private String fieldName;
    private String message;
}
