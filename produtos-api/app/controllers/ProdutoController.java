package controllers;

import javax.inject.Inject;

import play.data.DynamicForm;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.formularioDeNovoProduto;

public class ProdutoController extends Controller {
	@Inject
	private FormFactory formularios;

	public Result salvaNovoProduto() {
		DynamicForm formulario = formularios.form().bindFromRequest();
		String titulo = formulario.get("titulo");
		return ok("Formulário foi recebido " + titulo);
	}

	public Result formularioDeNovoProduto() {
		return ok(formularioDeNovoProduto.render("Cadastro de produto"));
	}
}
