package br.senac.tads.dsw.exemplosspring.produto;

import java.util.List;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

@Repository
public class ProdutoRepositoryJpaImpl implements ProdutoRepository {

	@PersistenceContext
	private EntityManager em;

	@Override
	public List<Produto> findAll(int offset, int quantidade) {
//		Query jpqlQuery = em.createQuery("SELECT p FROM Produto p");
		Query jpqlQuery = em.createNamedQuery("Produto.findAll")
				.setFirstResult(offset)
				.setMaxResults(quantidade);
		return jpqlQuery.getResultList();
	}

	@Override
	public List<Produto> findByCategoria(List<Integer> idsCat, int offset, int quantidade) {
		Query jpqlQuery = em.createNamedQuery("Produto.findByCategoriaId")
				.setParameter("idsCat", idsCat)
				.setFirstResult(offset)
				.setMaxResults(quantidade);
		return jpqlQuery.getResultList();
	}

	public Produto findByIdSemFetch(Long id) {
		Query jpqlQuery = em.createNamedQuery("Produto.findById")
				.setParameter("idProd", id);
		return (Produto) jpqlQuery.getSingleResult();
	}

	@Override
	public Produto findById(Long id) {
		EntityGraph<?> namedEntityGraph = 
				em.getEntityGraph("graph.ProdutoCategoriasImagens");

		Query jpqlQuery = em.createNamedQuery("Produto.findById")
				.setParameter("idProd", id)
				.setHint("javax.persistence.loadgraph", namedEntityGraph);
		return (Produto) jpqlQuery.getSingleResult();
	}

	@Override
	@Transactional
	public Produto save(Produto p) {
		if (p.getId() == null) {
			em.persist(p); // ID NULL -> cria novo
		} else {
			p = em.merge(p); // ID não nulo -> atualiza existente
		}
		return p;
	}

	@Override
	@Transactional
	public void deleteById(Long id) {
		Produto p = em.find(Produto.class, id);
		em.remove(p);
	}

	
	
	
}
