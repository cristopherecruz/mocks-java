package br.dev.cristopher.mocks.service;

import br.dev.cristopher.mocks.model.Lance;
import br.dev.cristopher.mocks.model.Pagamento;
import br.dev.cristopher.mocks.repository.PagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class GeradorDePagamento {

	@Autowired
	private PagamentoRepository pagamentos;

	public void gerarPagamento(Lance lanceVencedor) {
		LocalDate vencimento = LocalDate.now().plusDays(1);
		Pagamento pagamento = new Pagamento(lanceVencedor, vencimento);
		this.pagamentos.save(pagamento);
	}

}
