package com.course.webproject.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.course.webproject.domain.Categoria;
import com.course.webproject.repositories.CategoriaRepository;
import com.course.webproject.services.exceptions.DataIntegrityException;
import com.course.webproject.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository repo;

	public List<Categoria> findAll() {
		List<Categoria> list = repo.findAll();
		if (list == null) {
			throw new ObjectNotFoundException("Base de dados vazia para tipo: " + Categoria.class.getName());
		}
		return list;
	}

	public Categoria find(Integer id) {
		Optional<Categoria> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
	}
	
	public Categoria insert(Categoria obj) {
		obj.setId(null);
		return repo.save(obj);
	}

	public Categoria update(Categoria obj) {
		find(obj.getId());
		return repo.save(obj);
	}

	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);
		} catch(DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir um Categoria com produtos associados.");
		}
	}
}
