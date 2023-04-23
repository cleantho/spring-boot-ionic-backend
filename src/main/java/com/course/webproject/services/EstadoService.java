package com.course.webproject.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.course.webproject.domain.Estado;
import com.course.webproject.repositories.EstadoRepository;
import com.course.webproject.services.exceptions.ObjectNotFoundException;

@Service
public class EstadoService {

	@Autowired
	private EstadoRepository repo;

	public List<Estado> findAll() {
		List<Estado> list = repo.findAll();
		if (list == null) {
			throw new ObjectNotFoundException("Base de dados vazia para tipo: " + Estado.class.getName());
		}
		return list;
	}
}
