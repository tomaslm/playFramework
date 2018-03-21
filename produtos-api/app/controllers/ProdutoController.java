package controllers;

import javax.inject.Inject;

import models.Produto;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.formularioDeNovoProduto;

public class ProdutoController extends Controller {

	@Inject
	private FormFactory formularios;

	public Result salvaNovoProduto() {
		Form<Produto> formulario = formularios.form(Produto.class).bindFromRequest();
		Produto produto = formulario.get();
		return ok("Formulário foi recebido " + produto.getTitulo());
	}

	public Result formularioDeNovoProduto() {
		return ok(formularioDeNovoProduto.render("Cadastro de produto"));
	}
}