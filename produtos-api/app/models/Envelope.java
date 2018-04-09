package models;

import java.util.List;

public class Envelope<C> {
	private List<C> results;

	public Envelope(List<C> results) {
		this.results = results;
	}

	public List<C> getProdutos() {
		return this.results;
	}
}
