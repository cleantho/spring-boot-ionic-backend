package com.course.webproject.domain.enums;

public enum Status {
	PENDENTE(1, "Pendente"), QUITADO(2, "Quitado"), CANCELADO(3, "Cancelado");

	private int cod;
	private String descricao;

	private Status(int cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}

	public int getCod() {
		return cod;
	}

	public String getDescricao() {
		return descricao;
	}

	public static Status toEnum(Integer cod) {
		if (cod == null) {
			return null;
		}
		for (Status s : Status.values()) {
			if (cod.equals(s.getCod())) {
				return s;
			}
		}
		throw new IllegalArgumentException("Id(" + cod + ") inválido para enumeração Status");
	}
}
