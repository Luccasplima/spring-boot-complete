package br.com.irdbr.springbootionic.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.irdbr.springbootionic.domain.Cidade;
import br.com.irdbr.springbootionic.repositories.CidadeRepository;

@Service
public class CidadeService {
	
	@Autowired
	private CidadeRepository cidadeRepository;

	
	public List<Cidade> findByEstado(Long estadoId){
		return cidadeRepository.findCidades(estadoId);
	}
	
}
