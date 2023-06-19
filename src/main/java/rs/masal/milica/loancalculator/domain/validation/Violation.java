package rs.masal.milica.loancalculator.domain.validation;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Violation {
    private String fieldName;
    private String message;
}
