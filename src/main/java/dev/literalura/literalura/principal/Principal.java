package dev.literalura.literalura.principal;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import javax.management.RuntimeErrorException;

import dev.literalura.literalura.model.Autor;
import dev.literalura.literalura.model.DadosAutor;
import dev.literalura.literalura.model.DadosLivro;
import dev.literalura.literalura.model.Idioma;
import dev.literalura.literalura.model.Livro;
import dev.literalura.literalura.repository.AutorRepository;
import dev.literalura.literalura.repository.LivroRepository;
import dev.literalura.literalura.service.AutorService;
import dev.literalura.literalura.service.ConsumoApi;
import dev.literalura.literalura.service.ConverteDados;
import dev.literalura.literalura.service.LivroService;

public class Principal {
	private Scanner scan = new Scanner(System.in);
	private ConsumoApi consumo = new ConsumoApi();
	private ConverteDados conversor = new ConverteDados();
	private final String URL_BASE = "https://gutendex.com";
	private LivroRepository repositorioLivro;
	private AutorRepository repositorioAutor;
	private String json;
	
	public Principal(LivroRepository repositorioLivro, AutorRepository autorRepository) {
		this.repositorioLivro = repositorioLivro;
		this.repositorioAutor = autorRepository;
	}
	
	public void exibeMenu() {
		int opc = -1;
		var opcoes = "1 - Buscar livro pelo título\n" +
				     "2 - Listar livros registrados\n" +
				     "3 - Listar autores registrados\n" +
				     "4 - Listar autores vivos em um determinado ano\n" +
				     "5 - Listar livros em um determinado idioma\n\n" +
				     "0 - Sair";
		
		while (opc != 0) {
			System.out.println(opcoes);
			System.out.print("Escolha uma opção: ");
			try {
				opc = scan.nextInt();
				scan.nextLine();
			} catch (InputMismatchException e) {
				System.err.println(e.getMessage());
				opc = 0;
			}
			
			switch (opc) {
				case 1 :
					buscarLivro();
					break;
				case 2:
					listarLivros();
					break;
				case 3:
					listarAutores();
					break;
				case 4:
					listarAutoresVivosPorAno();
					break;
				case 5:
					listarLivrosPorIdioma();
					break;
				case 0:
					System.out.println("Saindo");
					opc = 0;
					break;
				default:
					System.out.println("Opção inválida");
			}
		}
	}
	
	private int encontraIdLivro(String url) {
		LivroService livroService = new LivroService();
		Optional<Integer> idLivro = livroService.encontraIdLivro(url);
		if (!idLivro.isEmpty()) {
			return idLivro.get();
		}
		return -1;
	}
	
	public DadosLivro getDadosLivro() {
		String titulo;
		System.out.print("Informe o título do livro buscado: ");
		titulo = scan.nextLine();
		int idTitulo= encontraIdLivro(URL_BASE + "/books?search=" + titulo.replace(" ", "%20"));
		if (idTitulo != -1) {
			json = consumo.obterDados(URL_BASE + "/books/" + idTitulo);
			DadosLivro dados = conversor.obterDados(json, DadosLivro.class);
			return dados;
		} else {
			return null;
		}
	}
	
	public void buscarLivro() {
		DadosLivro dadosLivro = getDadosLivro();
		Autor autor = buscarAutor();
		if (autor != null) {
			Livro livro = new Livro(dadosLivro);
			livro.setAutor(autor);
			autor.setLivrosPublicados(List.of(livro));
			repositorioAutor.save(autor);
		} else {
			System.out.println("Não foi possível encontrar livro");
		}
	}
	
	private Autor buscarAutor() {
		AutorService servicoAutor = new AutorService();
		var jsonAutor = servicoAutor.dadosAutor(json);
		if (jsonAutor != null) {
			System.out.println(jsonAutor);
			DadosAutor dadosAutor = conversor.obterDados(jsonAutor, DadosAutor.class);
			Autor autor = new Autor(dadosAutor);
			return autor;
		}
		return null;
	}
	
	public void listarLivros() {
		List<Livro> livros = repositorioLivro.findAll();
		if (livros.size() > 0) {
			livros.forEach(System.out::println);
		} else {
			System.out.println("Nenhum livro cadastrado");
		}
	}
	
	public void listarAutores() {
		List<Autor> autores = repositorioAutor.findAll();
		if (autores.size() > 0) {
			autores.forEach(System.out::println);
		}
	}
	
	public void listarAutoresVivosPorAno() {
		String anoString;
		int ano;
		System.out.print("Informe o ano: ");
		anoString = scan.nextLine();
		try {
			ano = Integer.parseInt(anoString);
			
			List<Autor> autores = repositorioAutor.findByAnoFalecimentoGreaterThanAndAnoNascimentoLessThan(LocalDate.of(ano, 1, 1), LocalDate.of(ano, 1, 1));
			if (autores.size() > 0) {
				autores.forEach(System.out::println);
			} else {
				System.out.println("Nenhum autor encontrado");
			}
		} catch (NumberFormatException e) {
			System.err.println(e.getMessage());
			System.out.println("Somente números são aceitos");
		}
	}
	
	private int indiceEscolha() {
		while (true) {
			System.out.print("Escolha uma opção: ");
			String escolha = scan.nextLine();
			try {
				return Integer.valueOf(escolha);
			} catch (NumberFormatException e) {
				System.err.println(e.getMessage());
			}
		}
	}
	
	private Idioma escolherIdioma() {
		List<Idioma> idiomas = new ArrayList<>();
		int escolha = -1;
		int i = 1;
		for (Idioma idioma : Idioma.values()) {
			idiomas.add(idioma);
			System.out.println(i + " - " +idioma.getIdiomaPortugues());
			i += 1;
		}
		escolha = indiceEscolha();
		while (escolha < 1 || escolha > i - 1) {
			System.out.println("Opção inválida");
			System.out.print("Escolha uma opção: ");
			escolha = indiceEscolha();
		}
		
		return idiomas.get(escolha - 1);
	}
	
	public void listarLivrosPorIdioma() {
		Idioma idioma = escolherIdioma();
		System.out.println("Idioma escolhido: " + idioma.getIdiomaPortugues());
		List<Livro> livros = repositorioLivro.findByIdioma(idioma);
		
		if (!livros.isEmpty()) {
			livros.forEach(System.out::println);
		} else {
			System.out.println("Nenhum livro cadastrado com o idioma " + idioma.getIdiomaPortugues());
		}
	}
}
