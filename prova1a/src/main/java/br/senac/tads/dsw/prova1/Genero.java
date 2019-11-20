package br.senac.tads.dsw.prova1;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "nome", name = "un_nome"))
public class Genero implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String nome;
    
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "genero")
    private Set<Filme> filmes;
    
    public Genero() {
        
    }
    
    public Genero(String nome) {
        this.nome = nome;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Set<Filme> getFilmes() {
        return filmes;
    }

    public void setFilmes(Set<Filme> filmes) {
        this.filmes = filmes;
    }

}
