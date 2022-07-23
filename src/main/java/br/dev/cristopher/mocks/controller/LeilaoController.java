package br.dev.cristopher.mocks.controller;

import br.dev.cristopher.mocks.dto.NovoLanceDto;
import br.dev.cristopher.mocks.dto.NovoLeilaoDto;
import br.dev.cristopher.mocks.model.Leilao;
import br.dev.cristopher.mocks.model.Usuario;
import br.dev.cristopher.mocks.repository.LeilaoRepository;
import br.dev.cristopher.mocks.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/leiloes")
public class LeilaoController {

	@Autowired
	private LeilaoRepository leiloes;

	@Autowired
	private UsuarioRepository usuarios;

	@GetMapping
	public ModelAndView index(Principal principal) {
		ModelAndView mv = new ModelAndView("leilao/index");
		mv.addObject("leiloes", leiloes.findAll());
		mv.addObject("usuarioLogado", principal);
		return mv;
	}

	@GetMapping("/{id}/form")
	public ModelAndView form(@PathVariable("id") Long id, Principal principal) {
		Leilao leilao = leiloes.findById(id).get();
		NovoLeilaoDto form = new NovoLeilaoDto(leilao);

		ModelAndView mv = new ModelAndView("leilao/form");
		mv.addObject("usuario", principal.getName());
		mv.addObject("leilao", form);
		return mv;
	}

	@PostMapping
	@Transactional
	public ModelAndView saveOrUpdate(@Valid @ModelAttribute("leilao") NovoLeilaoDto leilaoForm, Errors errors, RedirectAttributes attr, Principal principal) {
		if (errors.hasErrors()) {
			ModelAndView mv = new ModelAndView("/leilao/form");
			mv.addObject("leilao", leilaoForm);
			mv.addObject("usuario", principal.getName());
			return mv;
		}

		Usuario usuario = usuarios.findByNome(principal.getName());
		Leilao leilao = leilaoForm.toLeilao();
		leilao.setUsuario(usuario);

		leiloes.save(leilao);

		attr.addFlashAttribute("message", "Leilão salvo com sucesso");

		return new ModelAndView("redirect:/leiloes");
	}

	@GetMapping("/new")
	public ModelAndView newLeilao(Principal principal) {
		ModelAndView mv = new ModelAndView("leilao/form");
		mv.addObject("usuario", principal.getName());
		mv.addObject("leilao", new NovoLeilaoDto());
		return mv;
	}

	@GetMapping("/{id}")
	public ModelAndView show(@PathVariable Long id, Principal principal) {
		ModelAndView mv = new ModelAndView("leilao/show");
		mv.addObject("usuario", principal.getName());
		mv.addObject("leilao", leiloes.findById(id));
		mv.addObject("lance", new NovoLanceDto());
		return mv;
	}

}