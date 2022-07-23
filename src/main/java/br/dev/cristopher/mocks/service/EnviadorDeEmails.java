package br.dev.cristopher.mocks.service;

import br.dev.cristopher.mocks.model.Lance;
import br.dev.cristopher.mocks.model.Leilao;
import br.dev.cristopher.mocks.model.Usuario;
import org.springframework.stereotype.Service;

@Service
public class EnviadorDeEmails {

	// apenas simula o envio de um email...
	public void enviarEmailVencedorLeilao(Lance lanceVencedor) {
		Usuario vencedor = lanceVencedor.getUsuario();
		Leilao leilao = lanceVencedor.getLeilao();
		
		String email = String.format("Parabens {}! Voce venceu o leilao {} com o lance de R${}", vencedor.getNome(), leilao.getNome(), lanceVencedor.getValor());

		System.out.println(email);
	}

}
