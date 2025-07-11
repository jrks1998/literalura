package dev.literalura.literalura.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosLivro(
		@JsonAlias("title") String titulo,
		@JsonAlias("languages") List<String> idiomas,
		@JsonAlias("download_count") Integer qtdDownloads) {
}
