package controllers;

import models.Usuario;
import play.mvc.Result;

public class UsuarioController extends AbstractModelController<Usuario> {
	@Override
	public Result save() {
		Usuario usuario = getEntityFromRequest();
		usuario.encriptaSenhaSalva();
		// validaEmail();
		return super.save();
	}

	@Override
	public Result update(Long id) {
		Usuario usuario = getEntityFromRequest();
		// verificar se a senha já está encriptada
		// usuario.encriptaSenhaSalva();
		return super.update(id);
	}

}
