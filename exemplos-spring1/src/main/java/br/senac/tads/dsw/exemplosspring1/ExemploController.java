package br.senac.tads.dsw.exemplosspring1;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ExemploController {

	@GetMapping
	public String exemplo1() {
		return "exemplo1";
	}

	@GetMapping("/ex2")
	public String exemplo2(Model model) {
		model.addAttribute("mensagem", "Mensagem gerada no Controller");
		model.addAttribute("numeroSorte", 2140);
		model.addAttribute("dataAcesso", LocalDateTime.now());
		return "exemplo2";
	}

	@GetMapping("/ex2b")
	public ModelAndView exemplo2b() {
		ModelAndView resposta = new ModelAndView("exemplo2");
		resposta.addObject("mensagem", "Mensagem gerada no Controller usando ModelAndView");
		resposta.addObject("numeroSorte", 2204);
		resposta.addObject("dataAcesso", LocalDateTime.now());
		return resposta;
	}

	@GetMapping("/ex3")
	public ModelAndView exemplo3() {

		List<String> valores = new ArrayList<>();
		for (int i = 1; i <= 100; i++) {
			valores.add("Item " + i);
		}
		boolean par = false; // impar

		ModelAndView resposta = new ModelAndView("exemplo3");
		resposta.addObject("lista", valores);
		resposta.addObject("par", par);
		return resposta;
	}
	
	
	

}
