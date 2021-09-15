package br.com.irdbr.springbootionic.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.irdbr.springbootionic.domain.Estado;

@Repository
public interface EstadoRepository extends JpaRepository<Estado, Long>{
	
	@Transactional(readOnly = true)
	public List<Estado> findAllByOrderByNome();
	
}
