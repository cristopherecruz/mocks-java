package br.dev.cristopher.mocks.service;


import br.dev.cristopher.mocks.model.Lance;
import br.dev.cristopher.mocks.model.Leilao;
import br.dev.cristopher.mocks.repository.LeilaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class FinalizarLeilaoService {
	private LeilaoRepository leiloes;
	private EnviadorDeEmails enviadorDeEmails;
	@Autowired
	public FinalizarLeilaoService(LeilaoRepository leiloes, EnviadorDeEmails enviadorDeEmails) {
		this.leiloes = leiloes;
		this.enviadorDeEmails = enviadorDeEmails;
	}

	public void finalizarLeiloesExpirados() {
		LocalDate seteDiasAtras = LocalDate.now().minusDays(7);
		List<Leilao> expirados = leiloes.buscarLeiloesExpirados(seteDiasAtras);
		expirados.forEach(leilao -> {
			Lance maiorLance = maiorLanceDadoNoLeilao(leilao);
			leilao.setLanceVencedor(maiorLance);
			leilao.fechar();
			leiloes.save(leilao);
			enviadorDeEmails.enviarEmailVencedorLeilao(maiorLance);
		});
	}
	private Lance maiorLanceDadoNoLeilao(Leilao leilao) {
		List<Lance> lancesDoLeilao = new ArrayList<>(leilao.getLances());
		lancesDoLeilao.sort((lance1, lance2) -> {
			return lance2.getValor().compareTo(lance1.getValor());
		});
		return lancesDoLeilao.get(0);
	}
	
}
