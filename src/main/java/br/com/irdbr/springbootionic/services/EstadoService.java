package br.com.irdbr.springbootionic.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.irdbr.springbootionic.domain.Estado;
import br.com.irdbr.springbootionic.repositories.EstadoRepository;

@Service
public class EstadoService {
	
	@Autowired
	private EstadoRepository estadoRepository;

	
	public List<Estado> findAll(){
		return estadoRepository.findAllByOrderByNome();
	}
	
}
