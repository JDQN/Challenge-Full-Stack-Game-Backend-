package org.example.cardgame.application.handle;


import org.example.cardgame.application.handle.model.JuegoListViewModel;
import org.example.cardgame.application.handle.model.ListaDeTargetas;
import org.example.cardgame.application.handle.model.MazoViewModel;
import org.example.cardgame.application.handle.model.TableroViewModel;
import org.example.cardgame.domain.command.CrearRondaCommand;
import org.example.cardgame.usecase.usecase.CrearRondaUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;


@Configuration
public class QueryHandle {

	private final ReactiveMongoTemplate template;

	public QueryHandle(ReactiveMongoTemplate template) {
		this.template = template;
	}

	@Bean
	public RouterFunction<ServerResponse> listarJuego() {
		return route(
			 GET("/juego/listar/{id}"),
			 request -> template.find(filterByUId(request.pathVariable("id")), JuegoListViewModel.class, "gameview")
					.collectList()
					.flatMap(list -> ServerResponse.ok()
						 .contentType(MediaType.APPLICATION_JSON)
						 .body(BodyInserters.fromPublisher(Flux.fromIterable(list), JuegoListViewModel.class)))
		);
	}


	@Bean
	public RouterFunction<ServerResponse> getTablero() {
		return route(
			 GET("/juego/{id}"),
			 request -> template.findOne(filterById(request.pathVariable("id")), TableroViewModel.class, "gameview")
					.flatMap(element -> ServerResponse.ok()
						 .contentType(MediaType.APPLICATION_JSON)
						 .body(BodyInserters.fromPublisher(Mono.just(element), TableroViewModel.class)))
		);
	}

	@Bean
	public RouterFunction<ServerResponse> getMazo() {
		return route(
			 GET("/juego/mazo/{uid}/{juegoId}"),
			 request -> template.findOne(filterByUidAndId(request.pathVariable("uid"), request.pathVariable("juegoId")), MazoViewModel.class, "mazoview")
					.flatMap(element -> ServerResponse.ok()
						 .contentType(MediaType.APPLICATION_JSON)
						 .body(BodyInserters.fromPublisher(Mono.just(element), MazoViewModel.class)))
		);
	}


	@Bean
	public RouterFunction<ServerResponse> mazoPorJugador() {
		return RouterFunctions.route(
			 GET("/jugador/mazo/{uid}"),
			 request -> template.find(filterByUId(request.pathVariable("uid")), MazoViewModel.class, "mazoview")
					.collectList()
					.flatMap(list -> ServerResponse.ok()
						 .contentType(MediaType.APPLICATION_JSON)
						 .body(BodyInserters.fromPublisher(Flux.fromIterable(list), MazoViewModel.class)))
		);
	}


	@Bean
	public RouterFunction<ServerResponse> getGames(){
		return RouterFunctions.route(
			 GET("/juegos/"),
			 serverRequest -> template.findAll(JuegoListViewModel.class,"gameview")
					.collectList()
					.flatMap(games->ServerResponse.ok()
						 .contentType(MediaType.APPLICATION_JSON)
						 .body(BodyInserters.fromPublisher(Flux.fromIterable(games),JuegoListViewModel.class))));

	}


	//Segundo requerimiento
	@Bean
	public RouterFunction<ServerResponse> getAllCartas() {
		return route(
			 GET("/cartas"),
			 request -> template.findAll (ListaDeTargetas.class, "cards")
					.collectList()
					.flatMap(list -> ServerResponse.ok()
						 .contentType(MediaType.APPLICATION_JSON)
						 .body(BodyInserters.fromPublisher(Flux.fromIterable(list), ListaDeTargetas.class)))
		);
	}


	@Bean
	public RouterFunction<ServerResponse> createCard() {
		return route(
			 POST("/carta/creada").and(accept(MediaType.APPLICATION_JSON)),
			 request -> template.save(request.bodyToMono(ListaDeTargetas.class), "cards")
					.then(ServerResponse.ok().build())
		);
	}


	//primer requerimiento
	@Bean RouterFunction<ServerResponse> juegosFinalizados(){
		return RouterFunctions.route(
			 GET("/juego/finalizados/{jugadorId}"),
			 request -> template.find(filterByJugadores(request.pathVariable("jugadorId")),
				 JuegoListViewModel.class, "gameview").collectList()
				.flatMap(list -> ServerResponse.ok()
					 .contentType(MediaType.APPLICATION_JSON)
					 .body(BodyInserters.fromPublisher(Flux.fromIterable(list), JuegoListViewModel.class)))
		);
	}


	//primer requerimiento
	private Query filterByJugadores(String jugadorBusqueda){
		//log.info(jugadorBusqueda.toString());
		return new Query(
			 Criteria.where("jugadores."+jugadorBusqueda+".jugadorId").is(jugadorBusqueda)
					.and("finalizado").is(true)
		);
	}


	private Query filterByUId(String uid) {
		return new Query(
			 Criteria.where("uid").is(uid)
		);
	}

	private Query filterById(String juegoId) {
		return new Query(
			 Criteria.where("_id").is(juegoId)
		);
	}

	private Query filterByUidAndId(String uid, String juegoId) {
		return new Query(
			 Criteria.where("juegoId").is(juegoId).and("uid").is(uid)
		);
	}
}
