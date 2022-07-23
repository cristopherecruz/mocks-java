package br.dev.cristopher.mocks.repository;

import br.dev.cristopher.mocks.model.Leilao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface LeilaoRepository extends JpaRepository<Leilao, Long> {
    @Query("SELECT l FROM Leilao l WHERE l.dataAbertura = :seteDiasAtras")
    List<Leilao> buscarLeiloesExpirados(LocalDate seteDiasAtras);

}
