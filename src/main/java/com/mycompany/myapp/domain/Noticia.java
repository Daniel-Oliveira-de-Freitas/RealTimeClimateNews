package com.mycompany.myapp.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Noticia.
 */
@Entity
@Table(name = "noticia")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Noticia implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "titulo")
    private String titulo;

    @Column(name = "conteudo")
    private String conteudo;

    @Column(name = "data_publicacao")
    private LocalDate dataPublicacao;

    @Column(name = "autor")
    private String autor;

    @Column(name = "categoria")
    private String categoria;

    @Column(name = "fonte")
    private String fonte;

    @Column(name = "url")
    private String url;

    @Column(name = "classificacao")
    private String classificacao;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Noticia id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return this.titulo;
    }

    public Noticia titulo(String titulo) {
        this.setTitulo(titulo);
        return this;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getConteudo() {
        return this.conteudo;
    }

    public Noticia conteudo(String conteudo) {
        this.setConteudo(conteudo);
        return this;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public LocalDate getDataPublicacao() {
        return this.dataPublicacao;
    }

    public Noticia dataPublicacao(LocalDate dataPublicacao) {
        this.setDataPublicacao(dataPublicacao);
        return this;
    }

    public void setDataPublicacao(LocalDate dataPublicacao) {
        this.dataPublicacao = dataPublicacao;
    }

    public String getAutor() {
        return this.autor;
    }

    public Noticia autor(String autor) {
        this.setAutor(autor);
        return this;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getCategoria() {
        return this.categoria;
    }

    public Noticia categoria(String categoria) {
        this.setCategoria(categoria);
        return this;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getFonte() {
        return this.fonte;
    }

    public Noticia fonte(String fonte) {
        this.setFonte(fonte);
        return this;
    }

    public void setFonte(String fonte) {
        this.fonte = fonte;
    }

    public String getUrl() {
        return this.url;
    }

    public Noticia url(String url) {
        this.setUrl(url);
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getClassificacao() {
        return this.classificacao;
    }

    public Noticia classificacao(String classificacao) {
        this.setClassificacao(classificacao);
        return this;
    }

    public void setClassificacao(String classificacao) {
        this.classificacao = classificacao;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Noticia)) {
            return false;
        }
        return getId() != null && getId().equals(((Noticia) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Noticia{" +
            "id=" + getId() +
            ", titulo='" + getTitulo() + "'" +
            ", conteudo='" + getConteudo() + "'" +
            ", dataPublicacao='" + getDataPublicacao() + "'" +
            ", autor='" + getAutor() + "'" +
            ", categoria='" + getCategoria() + "'" +
            ", fonte='" + getFonte() + "'" +
            ", url='" + getUrl() + "'" +
            ", classificacao='" + getClassificacao() + "'" +
            "}";
    }
}
