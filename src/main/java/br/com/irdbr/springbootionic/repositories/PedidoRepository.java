package br.com.irdbr.springbootionic.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.irdbr.springbootionic.domain.Cliente;
import br.com.irdbr.springbootionic.domain.Pedido;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long>{
	
	// Pedidos por Cliente
	@Transactional(readOnly = true) // reduzir lock
	Page<Pedido> findByCliente(Cliente cliente, Pageable pageRequest);
	
}
