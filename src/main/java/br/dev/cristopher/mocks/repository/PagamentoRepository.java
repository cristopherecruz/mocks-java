package br.dev.cristopher.mocks.repository;

import br.dev.cristopher.mocks.model.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {

}
