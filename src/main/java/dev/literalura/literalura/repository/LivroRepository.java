package dev.literalura.literalura.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.literalura.literalura.model.Idioma;
import dev.literalura.literalura.model.Livro;

public interface LivroRepository extends JpaRepository<Livro, Long> {
	List<Livro> findByIdioma(Idioma idioma);
}
