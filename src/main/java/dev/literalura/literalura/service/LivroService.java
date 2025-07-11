package dev.literalura.literalura.service;

import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class LivroService {
	public Optional<Integer> encontraIdLivro(String url) {
		var json = new ConsumoApi().obterDados(url);
		if (!json.equals("erro")) {
			ObjectMapper mapper = new ObjectMapper();
			JsonNode root;
			try {
				root = mapper.readTree(json);
			} catch (JsonMappingException e) {
				System.err.println(e.getMessage());
				json = "";
				root = null;
			} catch (JsonProcessingException e) {
				System.err.println(e.getMessage());
				json = "";
				root = null;
			}
			
			if (!json.isEmpty()) {
				return Optional.of(root.get("results").get(0).get("id").asInt());
			}
		}
		return Optional.empty();
	}
}
