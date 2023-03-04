package com.course.webproject.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.course.webproject.domain.Cliente;
import com.course.webproject.dto.ClienteDTO;
import com.course.webproject.repositories.ClienteRepository;
import com.course.webproject.services.exceptions.DataIntegrityException;
import com.course.webproject.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repo;

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
		Optional<Cliente> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}
	
	public Cliente insert(Cliente obj) {
		obj.setId(null);
		return repo.save(obj);
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
			throw new DataIntegrityException("Não é possível excluir porque há entidades relacionadas.");
		}
	}
	
	public Cliente fromDTO(ClienteDTO objDto) {
		return new Cliente(objDto.getId(), objDto.getNome(), objDto.getEmail(), null, null);
	}
}
