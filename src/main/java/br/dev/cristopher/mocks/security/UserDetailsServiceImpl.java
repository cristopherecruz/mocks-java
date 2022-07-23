package br.dev.cristopher.mocks.security;

import br.dev.cristopher.mocks.model.Usuario;
import br.dev.cristopher.mocks.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UsuarioRepository usuarioDao;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario user = usuarioDao.findByNome(username);

		if (user == null) {
			throw new UsernameNotFoundException("Usuario nao encontrado");
		}

		return new LeilaoUserDetails(user);
	}

}
