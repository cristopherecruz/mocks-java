package br.dev.cristopher.mocks.service;

import br.dev.cristopher.mocks.model.Lance;
import br.dev.cristopher.mocks.model.Pagamento;
import br.dev.cristopher.mocks.repository.PagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.DayOfWeek;
import java.time.LocalDate;

@Service
public class GeradorDePagamento {
	private PagamentoRepository pagamentos;
	private Clock clock;
	@Autowired
	public GeradorDePagamento(PagamentoRepository pagamentos, Clock clock) {
		this.pagamentos = pagamentos;
		this.clock = clock;
	}

	public void gerarPagamento(Lance lanceVencedor) {
		LocalDate vencimento = LocalDate.now(clock).plusDays(1);
		Pagamento pagamento = new Pagamento(lanceVencedor, proximoDiaUtil(vencimento));
		this.pagamentos.save(pagamento);
	}
	private LocalDate proximoDiaUtil(LocalDate vencimento) {
		DayOfWeek dayOfWeek = vencimento.getDayOfWeek();

		if (dayOfWeek == DayOfWeek.SATURDAY) {
			return vencimento.plusDays(2);
		} else if (dayOfWeek == DayOfWeek.SUNDAY) {
			return vencimento.plusDays(1);
		}

		return vencimento;
	}

}
