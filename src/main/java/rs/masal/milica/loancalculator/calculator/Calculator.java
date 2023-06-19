package rs.masal.milica.loancalculator.calculator;

import javax.validation.Valid;

public interface Calculator<Req, Res> {
    Res calculate(@Valid Req request);
}
