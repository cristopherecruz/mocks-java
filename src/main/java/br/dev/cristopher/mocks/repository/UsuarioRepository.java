package br.dev.cristopher.mocks.repository;

import br.dev.cristopher.mocks.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario findByNome(String nome);

}
