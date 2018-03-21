package controllers;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.inject.Inject;

import com.fasterxml.jackson.databind.JsonNode;

import daos.ProdutoDAO;
import models.EnvelopeDeProdutos;
import models.Produto;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.data.validation.ValidationError;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

public class ApiController extends Controller {

	@Inject
	private ProdutoDAO produtoDAO;

	@Inject
	private FormFactory formularios;

	public Result todosOsProdutos() {
		DynamicForm formulario = formularios.form().bindFromRequest();
		Map<String, String> parametros = formulario.data();
		List<Produto> list;
		if (parametros.isEmpty()) {
			list = produtoDAO.todos();
		} else {
			this.validaParametros(formulario);
			if (formulario.hasErrors()) {
				JsonNode erros = Json.newObject().set("erros", formulario.errorsAsJson());
				return badRequest(erros);
			}
			list = produtoDAO.comFiltros(parametros);
		}
		EnvelopeDeProdutos envelope = new EnvelopeDeProdutos(list);
		return ok(Json.toJson(envelope));
	}

	private void validaParametros(DynamicForm formulario) {
		Map<String, String> parametros = formulario.data();
		List<String> atributos = Stream.of(Produto.class.getDeclaredFields()).map(f -> f.getName())
				.collect(Collectors.toList());
		parametros.keySet().forEach(chave -> {
			if (!atributos.contains(chave)) {
				formulario.reject(new ValidationError("Atributos inválidos", chave));
			}
		});
	}

	public Result doTipo(String tipo) {
		EnvelopeDeProdutos envelope = new EnvelopeDeProdutos(produtoDAO.doTipo(tipo));
		return ok(Json.toJson(envelope));
	}

}
