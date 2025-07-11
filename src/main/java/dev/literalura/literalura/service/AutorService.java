package dev.literalura.literalura.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AutorService {
	public String dadosAutor(String json) {
		ObjectMapper mapper = new ObjectMapper();
		JsonNode root;
		try {
			root = mapper.readTree(json);
		} catch (JsonMappingException e) {
			System.err.println(e.getMessage());
			root = null;
			return null;
		} catch (JsonProcessingException e) {
			System.err.println(e.getMessage());
			root = null;
			return null;
		}
		
		try {
			return mapper.writeValueAsString(root.get("authors").get(0));
		} catch (JsonProcessingException e) {
			System.err.println(e.getMessage());
			return null;
		}
	}
}
