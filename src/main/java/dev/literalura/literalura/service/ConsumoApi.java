package dev.literalura.literalura.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Redirect;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConsumoApi {
	public String obterDados(String url) {
		HttpClient client = HttpClient.newBuilder()
				.followRedirects(Redirect.NORMAL)
				.build();
		HttpRequest request = HttpRequest.newBuilder()
		         .uri(URI.create(url))
		         .build();
		try {
			HttpResponse<String> response = client
				     .send(request, HttpResponse.BodyHandlers.ofString());
			return response.body();
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
}
