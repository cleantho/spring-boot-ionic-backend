package com.course.webproject.domain;

import com.course.webproject.domain.enums.Status;
import com.fasterxml.jackson.annotation.JsonTypeName;

import jakarta.persistence.Entity;

@Entity
@JsonTypeName("pagamentoComCartao")
public class PagamentoComCartao extends Pagamento {
	private static final long serialVersionUID = 1L;
	
	private Integer parcelas;

	public PagamentoComCartao() {
		super();
	}

	public PagamentoComCartao(Integer id, Status estado, Pedido pedido, Integer parcelas) {
		super(id, estado, pedido);
		this.parcelas = parcelas;
	}

	public Integer getParcelas() {
		return parcelas;
	}

	public void setParcelas(Integer parcelas) {
		this.parcelas = parcelas;
	}
	
}
