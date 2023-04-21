package com.course.webproject.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.course.webproject.domain.Cidade;
import com.course.webproject.domain.Cliente;
import com.course.webproject.domain.Endereco;
import com.course.webproject.domain.enums.Perfil;
import com.course.webproject.domain.enums.Pessoa;
import com.course.webproject.dto.ClienteDTO;
import com.course.webproject.dto.ClienteNewDTO;
import com.course.webproject.repositories.ClienteRepository;
import com.course.webproject.repositories.EnderecoRepository;
import com.course.webproject.security.UserSS;
import com.course.webproject.services.exceptions.AuthorizationException;
import com.course.webproject.services.exceptions.DataIntegrityException;
import com.course.webproject.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	@Autowired
	private BCryptPasswordEncoder encoder;

	@Autowired
	private ClienteRepository repo;
	
	@Autowired
	private EnderecoRepository enderecoRepository;

	public List<Cliente> findAll() {
		List<Cliente> list = repo.findAll();
		if (list == null) {
			throw new ObjectNotFoundException("Base de dados vazia para tipo: " + Cliente.class.getName());
		}
		return list;
	}
	
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String direction, String orderBy){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}

	public Cliente find(Integer id) {
		UserSS user = UserService.authenticated();
		if(user == null || !user.hasRole(Perfil.ADMIN) && !id.equals(user.getId())) {
			throw new AuthorizationException("Acesso negado!");
		}
		
		Optional<Cliente> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}
	
	public Cliente insert(Cliente obj) {
		obj.setId(null);
		obj = repo.save(obj);
		enderecoRepository.saveAll(obj.getEnderecos());
		return obj;
	}

	public Cliente update(Cliente obj) {
		Cliente dataBase = find(obj.getId());
		dataBase.setNome(obj.getNome());
		dataBase.setEmail(obj.getEmail());
		return repo.save(dataBase);
	}

	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);
		} catch(DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir porque há pedidos relacionadas.");
		}
	}
	
	public Cliente fromDTO(ClienteDTO objDto) {
		return new Cliente(objDto.getId(), objDto.getNome(), objDto.getEmail(), null, null, null);
	}
	
	public Cliente fromDTO(ClienteNewDTO objDto) {
		Cliente cliente = new Cliente(null, objDto.getNome(), objDto.getEmail(), objDto.getCpfOuCnpj(),Pessoa.toEnum(objDto.getTipo()), encoder.encode(objDto.getSenha()));
		Cidade cidade = new Cidade(objDto.getCidadeId(), null, null);
		Endereco end = new Endereco(null, objDto.getLogradouro(), objDto.getNumero(), objDto.getComplemento(), objDto.getBairro(), objDto.getCep(), cliente, cidade);
		cliente.getEnderecos().add(end);
		cliente.getTelefones().add(objDto.getTelefone1());
		if(objDto.getTelefone2()!= null) {
			cliente.getTelefones().add(objDto.getTelefone2());
		}
		if(objDto.getTelefone3()!= null) {
			cliente.getTelefones().add(objDto.getTelefone3());
		}
		
		return cliente;
	}
}
