package dev.literalura.literalura.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "autores")
public class Autor {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(unique = true, nullable = false)
	private String nome;
	LocalDate anoNascimento;
	LocalDate anoFalecimento;
	@OneToMany(mappedBy = "autor", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	List<Livro> livrosPublicados = new ArrayList<>();
	
	public Autor() {}
	
	public Autor(DadosAutor dadosAutor) {
		setNome(dadosAutor.nome());
		this.anoNascimento = LocalDate.of(dadosAutor.anoNascimento(), 1, 1);
		this.anoFalecimento = LocalDate.of(dadosAutor.anoFalecimento(), 1, 1);
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		if (nome.contains(", ")) {
			String[] partes = nome.split(",");
			this.nome = partes[1] + " " + partes[0];
		} else {
			this.nome = nome;
		}
	}

	public LocalDate getAnoNascimento() {
		return anoNascimento;
	}

	public void setAnoNascimento(LocalDate anoNascimento) {
		this.anoNascimento = anoNascimento;
	}

	public LocalDate getAnoFalecimento() {
		return anoFalecimento;
	}

	public void setAnoFalecimento(LocalDate anoFalecimento) {
		this.anoFalecimento = anoFalecimento;
	}
	
	public void setLivrosPublicados(List<Livro> livros) {
		livros.forEach(l -> l.setAutor(this));
		livrosPublicados = livros;
	}

	@Override
	public String toString() {
		return "Autor [id=" + id + ", nome=" + nome + ", anoNascimento=" + anoNascimento.getYear() + ", anoFalecimento="
				+ anoFalecimento.getYear() + "]";
	}
}