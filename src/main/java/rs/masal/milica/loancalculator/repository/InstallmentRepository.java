package rs.masal.milica.loancalculator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.masal.milica.loancalculator.entity.Installment;

@Repository
public interface InstallmentRepository extends JpaRepository<Installment, Long> {
}
