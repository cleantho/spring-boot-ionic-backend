package com.course.webproject.domain.enums;

public enum Pessoa {
	PESSOAFISICA(1, "Pessoa Física"), PESSOAJURIDICA(2, "Pessoa Jurídica");

	private int cod;
	private String descricao;

	private Pessoa(int cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}

	public int getCod() {
		return cod;
	}

	public String getDescricao() {
		return descricao;
	}

	public static Pessoa toEnum(Integer cod) {
		if (cod == null) {
			return null;
		}
		for (Pessoa p : Pessoa.values()) {
			if (cod.equals(p.getCod())) {
				return p;
			}
		}
		throw new IllegalArgumentException("Id(" + cod + ") inválido para enumeração Pessoa" );
	}
}
