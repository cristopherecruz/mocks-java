package br.dev.cristopher.mocks.service;

import br.dev.cristopher.mocks.model.Lance;
import br.dev.cristopher.mocks.model.Leilao;
import br.dev.cristopher.mocks.model.Pagamento;
import br.dev.cristopher.mocks.model.Usuario;
import br.dev.cristopher.mocks.repository.PagamentoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.math.BigDecimal;
import java.time.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class GeradorDePagamentoTest {
    private GeradorDePagamento geradorDePagamento;
    @Mock
    private PagamentoRepository pagamentoRepository;
    @Captor
    private ArgumentCaptor<Pagamento> pagamentoArgumentCaptor;
    @Mock
    private Clock clock;

    @BeforeEach
    public void beforeEach() {
        MockitoAnnotations.openMocks(this);
        this.geradorDePagamento = new GeradorDePagamento(pagamentoRepository, clock);
    }

    @Test
    void gerarPagamento() {

        Leilao leilao = leilao();
        Lance lance = leilao.getLanceVencedor();

        LocalDate data = LocalDate.of(2022, 7, 18);

        Instant instant = data.atStartOfDay(ZoneId.systemDefault()).toInstant();

        Mockito.when(clock.instant()).thenReturn(instant);
        Mockito.when(clock.getZone()).thenReturn(ZoneId.systemDefault());

        geradorDePagamento.gerarPagamento(lance);

        Mockito.verify(pagamentoRepository).save(pagamentoArgumentCaptor.capture());

        Pagamento pagamento = pagamentoArgumentCaptor.getValue();

        assertEquals(LocalDate.now(clock).plusDays(1), pagamento.getVencimento());
        assertEquals(lance.getValor(), pagamento.getValor());
        assertFalse(pagamento.getPago());
        assertEquals(lance.getUsuario(), pagamento.getUsuario());
        assertEquals(leilao, pagamento.getLeilao());
    }

    private Leilao leilao() {

        Leilao leilao = new Leilao("Celular", new BigDecimal("500"), new Usuario("Fulano"));

        Lance lance = new Lance(new Usuario("Ciclano"), new BigDecimal("900"));

        leilao.propoe(lance);
        leilao.setLanceVencedor(lance);

        return leilao;
    }

}