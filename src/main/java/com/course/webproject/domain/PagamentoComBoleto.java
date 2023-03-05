package com.course.webproject.domain;

import java.time.Instant;

import com.course.webproject.domain.enums.Status;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonTypeName;

import jakarta.persistence.Entity;

@Entity
@JsonTypeName("pagamentoComBoleto")
public class PagamentoComBoleto extends Pagamento {
	private static final long serialVersionUID = 1L;
	
	@JsonFormat(pattern = "dd-MM-yyyy")
	private Instant vencimento;
	
	@JsonFormat(pattern = "dd-MM-yyyy")
	private Instant pagamento;
	
	public PagamentoComBoleto() {
		super();
	}
	
	public PagamentoComBoleto(Integer id, Status estado, Pedido pedido, Instant vencimento, Instant pagamento) {
		super(id, estado, pedido);
		this.vencimento = vencimento;
		this.pagamento = pagamento;
	}

	public Instant getVencimento() {
		return vencimento;
	}

	public void setVencimento(Instant vencimento) {
		this.vencimento = vencimento;
	}

	public Instant getPagamento() {
		return pagamento;
	}

	public void setPagamento(Instant pagamento) {
		this.pagamento = pagamento;
	}

}
