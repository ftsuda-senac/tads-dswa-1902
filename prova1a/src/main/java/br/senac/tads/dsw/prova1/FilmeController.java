package br.senac.tads.dsw.prova1;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/filme")
public class FilmeController {

	@Autowired
	private FilmeRepository filmeRepo;

	@Autowired
	private GeneroRepository generoRepo;

	@GetMapping
	public ModelAndView listar() {
		List<Filme> filmes = filmeRepo.findAll();
		ModelAndView mv = new ModelAndView("lista")
				.addObject("filmes", filmes);
		return mv;
	}

	@GetMapping("/novo")
	public ModelAndView abrirForm() {
		List<Genero> generos = generoRepo.findAll();
		return new ModelAndView("form")
				.addObject("filme", new Filme())
				.addObject("generos", generos);
	}

	@PostMapping("/salvar")
	public ModelAndView salvar(
			@ModelAttribute @Valid Filme filme,
			BindingResult bindingResult,
			RedirectAttributes redirAttr) {
		
		if (bindingResult.hasErrors()) {
			List<Genero> generos = generoRepo.findAll();
			return new ModelAndView("form")
					.addObject("filme", new Filme())
					.addObject("generos", generos)
					.addObject("msgErro", "Campos não preenchidos");
		}
		
		Genero genero = generoRepo.findById(filme.getIdGenero());
		filme.setGenero(genero);
		genero.setFilmes(new HashSet<>(Arrays.asList(filme)));
		
		filmeRepo.save(filme);
		redirAttr.addFlashAttribute("msg", "Filme " + filme.getNome() + " salvo com sucesso");
		return new ModelAndView("redirect:/filme");
		
	}


}
