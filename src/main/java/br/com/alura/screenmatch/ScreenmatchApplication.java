package br.com.alura.screenmatch;

import br.com.alura.screenmatch.model.DadosEpisodio;
import br.com.alura.screenmatch.model.DadosSerie;
import br.com.alura.screenmatch.model.DadosTemporadas;
import br.com.alura.screenmatch.service.ConsumoApi;
import br.com.alura.screenmatch.service.ConverteDados;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		var api = new ConsumoApi();
		var json = api.obterDados("http://www.omdbapi.com/?t=arcane&apikey=e13b9bf9");
		System.out.println(json);
		var conversao = new ConverteDados();
		DadosSerie dados = conversao.obterDados(json, DadosSerie.class);
		System.out.println(dados);

		json = api.obterDados("http://www.omdbapi.com/?t=arcane&season=1&episode=9&apikey=e13b9bf9");
		DadosEpisodio dadosEpisodio = conversao.obterDados(json, DadosEpisodio.class);
		System.out.println(dadosEpisodio);

		List<DadosTemporadas> temporadas = new ArrayList<>();
		for (int i = 1; i <= dados.totalTemporadas(); i++){
			json = api.obterDados("http://www.omdbapi.com/?t=arcane&season="+ i +"&apikey=e13b9bf9");
			DadosTemporadas dadosTemporadas = conversao.obterDados(json, DadosTemporadas.class);
			temporadas.add(dadosTemporadas);
		}
		System.out.println(temporadas);
	}
}
