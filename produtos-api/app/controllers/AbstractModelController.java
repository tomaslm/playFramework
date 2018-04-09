package controllers;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.inject.Inject;

import com.avaje.ebean.Model;
import com.fasterxml.jackson.databind.JsonNode;

import daos.AbstractModelDAO;
import models.Envelope;
import models.Produto;
import play.data.DynamicForm;
import play.data.Form;
import play.data.FormFactory;
import play.data.validation.ValidationError;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

public abstract class AbstractModelController<C extends Model> extends Controller {

	@Inject
	private FormFactory formularios;

	@Inject
	private AbstractModelDAO<C> modelDAO;

	Class<C> clazz;

	public AbstractModelController(AbstractModelDAO<C> modelDAO, Class clazz) {
		this.modelDAO = modelDAO;
		this.clazz = clazz;
		// (Class<C>) ((ParameterizedType)
		// getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	public Result findAll() {
		DynamicForm formulario = formularios.form().bindFromRequest();
		Map<String, String> parametros = formulario.data();
		List<C> list;
		if (parametros.isEmpty()) {
			list = modelDAO.all();
		} else {
			this.validaParametros(formulario);
			if (formulario.hasErrors()) {
				JsonNode erros = Json.newObject().set("erros", formulario.errorsAsJson());
				return badRequest(erros);
			}
			list = modelDAO.comFiltros(parametros);
		}
		return ok(Json.toJson(new Envelope<C>(list)));
	}

	public Result save() {
		C entity = this.getEntityFromRequest();
		modelDAO.save(entity);
		return noContent();
	}

	public Result findById(Long id) {
		return ok(Json.toJson(modelDAO.findById(id)));
	}

	public Result update(Long id) {
		C entity = this.getEntityFromRequest();
		modelDAO.update(entity);
		return noContent();
	}

	public Result deleteById(Long id) {
		modelDAO.deleteById(id);
		return noContent();
	}

	protected C getEntityFromRequest() {
		Form<C> formulario = formularios.form(clazz).bindFromRequest();
		return formulario.get();
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
}
