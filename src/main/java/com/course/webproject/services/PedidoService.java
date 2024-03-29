package com.course.webproject.services;

import java.time.Instant;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.course.webproject.domain.Cliente;
import com.course.webproject.domain.ItemPedido;
import com.course.webproject.domain.PagamentoComBoleto;
import com.course.webproject.domain.Pedido;
import com.course.webproject.domain.enums.Status;
import com.course.webproject.repositories.ItemPedidoRepository;
import com.course.webproject.repositories.PagamentoRepository;
import com.course.webproject.repositories.PedidoRepository;
import com.course.webproject.security.UserSS;
import com.course.webproject.services.exceptions.AuthorizationException;
import com.course.webproject.services.exceptions.ObjectNotFoundException;

import jakarta.transaction.Transactional;

@Service
public class PedidoService {
	@Autowired
	private PedidoRepository repo;

	@Autowired
	private PagamentoRepository pagamentoRepository;

	@Autowired
	private ItemPedidoRepository itemPedidoRepository;

	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private EmailService emailService;

	public Pedido find(Integer id) {
		Optional<Pedido> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}
	
	public Page<Pedido> findPage(Integer page, Integer linesPerPage, String direction, String orderBy){
		UserSS user = UserService.authenticated();
		if(user == null) {
			throw new AuthorizationException("Acesso negado!");
		}
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		Cliente cliente = clienteService.find(user.getId());
		return repo.findByCliente(cliente, pageRequest);
	}

	@Transactional
	public Pedido insert(Pedido obj) {
		obj.setId(null);
		obj.setInstante(Instant.now());
		obj.setCliente(clienteService.find(obj.getCliente().getId()));
		obj.getPagamento().setEstado(Status.PENDENTE);
		obj.getPagamento().setPedido(obj);
		if (obj.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pb = (PagamentoComBoleto) obj.getPagamento();
			pb.setVencimento(Instant.now().plusSeconds(604800));
			pb.setPagamento(null);
		} 
		obj = repo.save(obj);
		pagamentoRepository.save(obj.getPagamento());
		for (ItemPedido item : obj.getItens()) {
			item.setDesconto(0.0);
			item.setProduto(produtoService.find(item.getProduto().getId()));
			item.setPrecoVenda(item.getProduto().getPreco());
			item.setPedido(obj);
		}
		itemPedidoRepository.saveAll(obj.getItens());
		emailService.sendOrderConfirmationHtmlEmail(obj);
		return obj;
	}

}
