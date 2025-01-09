package br.com.alura.screenmatch.main;

import br.com.alura.screenmatch.model.DadosSerie;
import br.com.alura.screenmatch.model.DadosTemporadas;
import br.com.alura.screenmatch.service.ConsumoApi;
import br.com.alura.screenmatch.service.ConverteDados;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private final String ENDERECO = "http://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=e13b9bf9";
    private final String SEASON = "&season=";
    Scanner scan = new Scanner(System.in);
    ConsumoApi api = new ConsumoApi();
    ConverteDados conversao = new ConverteDados();

    public void pesquisaSerie(){
        System.out.println("Qual serie você quer pesquisar? ");
        var busca = scan.nextLine();
        var jsonBusca = api.obterDados(ENDERECO + URLEncoder.encode(busca, StandardCharsets.UTF_8) + API_KEY);
        DadosSerie dadosBusca = conversao.obterDados(jsonBusca, DadosSerie.class);
        System.out.println(dadosBusca);

        List<DadosTemporadas> temporadas = new ArrayList<>();

        for (int i = 1; i <= dadosBusca.totalTemporadas(); i++){
        		jsonBusca = api.obterDados(ENDERECO+ URLEncoder.encode(busca, StandardCharsets.UTF_8) + SEASON + i + API_KEY);
        		DadosTemporadas dadosTemporadas = conversao.obterDados(jsonBusca, DadosTemporadas.class);
        		temporadas.add(dadosTemporadas);
        	}
        	temporadas.forEach(System.out::println);

        //for (int i = 0; i < temporadas.size(); i++) {
        //    List<DadosEpisodio> episodioTemporada = temporadas.get(i).episodios();
        //    for (int j = 0; j < episodioTemporada.size(); j++) {
        //        System.out.println(episodioTemporada.get(j).titulo());
        //    }
        //}

        temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));
    }
}
