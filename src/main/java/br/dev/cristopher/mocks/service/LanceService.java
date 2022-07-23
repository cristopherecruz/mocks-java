package br.dev.cristopher.mocks.service;

import br.dev.cristopher.mocks.dto.NovoLanceDto;
import br.dev.cristopher.mocks.model.Lance;
import br.dev.cristopher.mocks.model.Leilao;
import br.dev.cristopher.mocks.model.Usuario;
import br.dev.cristopher.mocks.repository.LanceRepository;
import br.dev.cristopher.mocks.repository.LeilaoRepository;
import br.dev.cristopher.mocks.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LanceService {

	@Autowired
	private LanceRepository lances;

	@Autowired
	private UsuarioRepository usuarios;

	@Autowired
	private LeilaoRepository leiloes;

	public boolean propoeLance(NovoLanceDto lanceDto, String nomeUsuario) {

		Usuario usuario = usuarios.findByNome(nomeUsuario);
		Lance lance = lanceDto.toLance(usuario);

		Leilao leilao = this.getLeilao(lanceDto.getLeilaoId());

		if (leilao.propoe(lance)) {
			lances.save(lance);
			return true;
		}

		return false;
	}

	public Leilao getLeilao(Long leilaoId) {
		return leiloes.findById(leilaoId).get();
	}

}