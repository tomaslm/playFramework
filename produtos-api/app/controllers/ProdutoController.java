package controllers;

import models.Produto;
import play.mvc.Result;

public class ProdutoController extends AbstractModelController<Produto> {


	public ProdutoController() {
		super(modelDAO, clazz);
	}

	public Result formularioDeNovoProduto() {
		return ok(formularioDeNovoProduto.render("Cadastrar produto"));
	}
}
