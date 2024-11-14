package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Noticia} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class NoticiaDTO implements Serializable {

    private Long id;

    private String titulo;

    private String conteudo;

    private LocalDate dataPublicacao;

    private String autor;

    private String categoria;

    private String fonte;

    private String url;

    private String classificacao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public LocalDate getDataPublicacao() {
        return dataPublicacao;
    }

    public void setDataPublicacao(LocalDate dataPublicacao) {
        this.dataPublicacao = dataPublicacao;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getFonte() {
        return fonte;
    }

    public void setFonte(String fonte) {
        this.fonte = fonte;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getClassificacao() {
        return classificacao;
    }

    public void setClassificacao(String classificacao) {
        this.classificacao = classificacao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NoticiaDTO)) {
            return false;
        }

        NoticiaDTO noticiaDTO = (NoticiaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, noticiaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NoticiaDTO{" +
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
