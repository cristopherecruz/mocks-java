package br.dev.cristopher.mocks.repository;

import br.dev.cristopher.mocks.model.Lance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LanceRepository extends JpaRepository<Lance, Long> {
}
