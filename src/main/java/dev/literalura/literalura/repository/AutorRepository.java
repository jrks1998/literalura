package dev.literalura.literalura.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.literalura.literalura.model.Autor;

public interface AutorRepository extends JpaRepository<Autor, Long> {
	List<Autor> findByAnoFalecimentoGreaterThanAndAnoNascimentoLessThan(LocalDate anoFalecimento, LocalDate anoNascimento);
}
