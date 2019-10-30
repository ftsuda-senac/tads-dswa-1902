package br.senac.tads.dsw.exemplosspring;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.senac.tads.dsw.exemplosspring.produto.Categoria;
import br.senac.tads.dsw.exemplosspring.produto.CategoriaRepository;
import br.senac.tads.dsw.exemplosspring.produto.ImagemProduto;
import br.senac.tads.dsw.exemplosspring.produto.Produto;
import br.senac.tads.dsw.exemplosspring.produto.ProdutoRepository;

@RestController
@RequestMapping("/api/produto")
public class ProdutoRestController {

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private CategoriaRepository categoriaRepository;

	@GetMapping
	// @CrossOrigin(origins = "*")
	public List<Produto> listar() {
		List<Produto> resultados = produtoRepository.findAll(0, 100);
		return resultados;
	}

	@GetMapping("/{id}")
	// @CrossOrigin(origins = "*")
	public Produto obterPorId(@PathVariable("id") Long id) {
		Produto produto = produtoRepository.findById(id);
		return produto;
	}

	@PostMapping
	// @CrossOrigin(origins = "*")
	public ResponseEntity<Void> salvar(@ModelAttribute @Valid Produto produto, BindingResult bindingResult) {
		produto.setDtCadastro(LocalDateTime.now());
		if (produto.getIdsCategorias() != null && !produto.getIdsCategorias().isEmpty()) {
			Set<Categoria> categoriasSelecionadas = new HashSet<>();
			for (Integer idCat : produto.getIdsCategorias()) {
				Categoria cat = categoriaRepository.findById(idCat);
				categoriasSelecionadas.add(cat);
				cat.setProdutos(new HashSet<>(Arrays.asList(produto)));
			}
			produto.setCategorias(categoriasSelecionadas);
		}
		if (produto.getImagensList() != null && !produto.getImagensList().isEmpty()) {
			Set<ImagemProduto> imagens = new LinkedHashSet<>();
			for (ImagemProduto img : produto.getImagensList()) {
				img.setProduto(produto);
				imagens.add(img);
			}
			produto.setImagens(imagens);
		}
		produtoRepository.save(produto);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/{id}")
	// @CrossOrigin(origins = "*")
	public ResponseEntity<Void> remover(@PathVariable("id") Long id) {
		produtoRepository.deleteById(id);
		return ResponseEntity.ok().build();
	}

}
