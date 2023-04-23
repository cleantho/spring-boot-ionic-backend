package com.course.webproject.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.course.webproject.domain.Cidade;
import com.course.webproject.repositories.CidadeRepository;
import com.course.webproject.services.exceptions.ObjectNotFoundException;

@Service
public class CidadeService {

	@Autowired
	private CidadeRepository repo;

	public List<Cidade> findByEstado(Integer estadoId) {
		List<Cidade> list = repo.findCidades(estadoId);
		if (list == null) {
			throw new ObjectNotFoundException("Baese de dados vazia para tipo: " + Cidade.class.getName());
		}
		return list;
	}
}
