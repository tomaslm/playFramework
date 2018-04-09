package daos;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.Model;
import com.avaje.ebean.Model.Finder;

public abstract class AbstractModelDAO<C extends Model> {
	private Finder<Long, C> entities;

	public AbstractModelDAO() {
		Class<C> clazz = (Class<C>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		this.entities = new Finder<>(clazz);
	}

	public List<C> all() {
		return comFiltros(new HashMap<>());
	}

	public void save(C entity) {
		entity.save();
	}

	public C findById(Long id) {
		return entities.byId(id);
	}

	public void update(C entity) {
		entity.save();
	}

	public void deleteById(Long id) {
		entities.deleteById(id);
	}

	public List<C> comFiltros(Map<String, String> filtros) {
		ExpressionList<C> consulta = entities.where();
		filtros.entrySet().forEach(e -> consulta.eq(e.getKey(), e.getValue()));
		return consulta.findList();
	}
}
