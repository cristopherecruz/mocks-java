package br.dev.cristopher.mocks.service;

import br.dev.cristopher.mocks.model.Lance;
import br.dev.cristopher.mocks.model.Leilao;
import br.dev.cristopher.mocks.model.Usuario;
import br.dev.cristopher.mocks.repository.LeilaoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FinalizarLeilaoServiceTest {
    private FinalizarLeilaoService finalizarLeilaoService;
    @Mock
    private LeilaoRepository leilaoRepository;

    @Mock
    private EnviadorDeEmails enviadorDeEmails;

    @BeforeEach
    public void beforeEach() {
        MockitoAnnotations.openMocks(this);
        this.finalizarLeilaoService = new FinalizarLeilaoService(leilaoRepository, enviadorDeEmails);
    }

    @Test
    void finalizarLeiloesExpirados() {
        List<Leilao> leiloes = leiloes();
        LocalDate seteDiasAtras = LocalDate.now().minusDays(7);

        Mockito.when(leilaoRepository.buscarLeiloesExpirados(seteDiasAtras)).thenReturn(leiloes);

        finalizarLeilaoService.finalizarLeiloesExpirados();

        Leilao leilao = leiloes.get(0);

        assertTrue(leilao.isFechado());
        assertEquals(new BigDecimal("900"), leilao.getLanceVencedor().getValor());
        Mockito.verify(leilaoRepository).save(leilao);
    }

    @Test
    void deveriaEnviarEmailParaVencedorDoLeilao() {
        List<Leilao> leiloes = leiloes();
        LocalDate seteDiasAtras = LocalDate.now().minusDays(7);

        Mockito.when(leilaoRepository.buscarLeiloesExpirados(seteDiasAtras)).thenReturn(leiloes);

        finalizarLeilaoService.finalizarLeiloesExpirados();

        Leilao leilao = leiloes.get(0);
        Lance lance = leilao.getLanceVencedor();

        Mockito.verify(enviadorDeEmails).enviarEmailVencedorLeilao(lance);
    }

    @Test
    void naoDeveriaEnviarEmailParaVencedorDoLeilao() {
        List<Leilao> leiloes = leiloes();
        LocalDate seteDiasAtras = LocalDate.now().minusDays(7);

        Mockito.when(leilaoRepository.buscarLeiloesExpirados(seteDiasAtras)).thenReturn(leiloes);

        Mockito.when(leilaoRepository.save(Mockito.any())).thenThrow(RuntimeException.class);

        try {
            finalizarLeilaoService.finalizarLeiloesExpirados();
            Mockito.verifyNoInteractions(enviadorDeEmails);
        }catch (Exception e) {}

    }
    private List<Leilao> leiloes() {

        List<Leilao> lista = new ArrayList<>();

        Leilao leilao = new Leilao("Celular", new BigDecimal("500"), new Usuario("Fulano"));

        Lance primeiro = new Lance(new Usuario("Beltrano"), new BigDecimal("600"));
        Lance segundo = new Lance(new Usuario("Ciclano"), new BigDecimal("900"));

        leilao.propoe(primeiro);
        leilao.propoe(segundo);
        lista.add(leilao);

        return lista;
    }

}