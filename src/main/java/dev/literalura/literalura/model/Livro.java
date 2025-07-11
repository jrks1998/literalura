package dev.literalura.literalura.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "livros")
public class Livro {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(unique = true, nullable = false)
	private String titulo;
	@Enumerated(EnumType.STRING)
	private Idioma idioma;
	private Integer qtdDownloads;
	@ManyToOne(fetch = FetchType.EAGER)
	private Autor autor;
	
	public Livro() {}
	
	public Livro(DadosLivro dadosLivro) {
		this.titulo = dadosLivro.titulo();
		this.idioma = Idioma.fromString(dadosLivro.idiomas().get(0));
		this.qtdDownloads = dadosLivro.qtdDownloads();
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public Autor getAutor() {
		return autor;
	}

	public void setAutor(Autor autor) {
		this.autor = autor;
	}

	public String getIdiomas() {
		return idioma.getIdiomaPortugues();
	}

	public void setIdiomas(Idioma idioma) {
		this.idioma = idioma;
	}

	public Integer getQtdDownloads() {
		return qtdDownloads;
	}

	public void setQtdDownloads(Integer qtdDownloads) {
		this.qtdDownloads = qtdDownloads;
	}

	@Override
	public String toString() {
		return "Livro [id=" + id + ", titulo=" + titulo + ", idioma=" + idioma + ", número de downloads=" + qtdDownloads
				+ ", autor=" + autor + "]";
	}
}
