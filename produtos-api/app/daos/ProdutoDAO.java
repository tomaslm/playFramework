package daos;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.Model.Finder;

import models.Produto;

public class ProdutoDAO {
	private Finder<Long, Produto> produtos = new Finder<>(Produto.class);

	public Optional<Produto> comCodigo(String codigo) {
		return Optional.ofNullable(produtos.where().eq("codigo", codigo).findUnique());
	}

	public List<Produto> todos() {
		return produtos.all();
	}

	// public List<Produto> doTipo(String tipo) {
	// return produtos.where().eq("tipo", tipo).findList();
	// }

	public List<Produto> comFiltros(Map<String, String> filtros) {
		ExpressionList<Produto> consulta = produtos.where();
		filtros.entrySet().forEach(e -> consulta.eq(e.getKey(), e.getValue()));
		return consulta.findList();
	}
}
