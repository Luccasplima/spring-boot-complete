package br.com.irdbr.springbootionic.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.com.irdbr.springbootionic.domain.Cliente;
import br.com.irdbr.springbootionic.domain.ItemPedido;
import br.com.irdbr.springbootionic.domain.PagamentoComBoleto;
import br.com.irdbr.springbootionic.domain.Pedido;
import br.com.irdbr.springbootionic.domain.enums.EstadoPagamento;
import br.com.irdbr.springbootionic.repositories.ItemPedidoRepository;
import br.com.irdbr.springbootionic.repositories.PagamentoRepository;
import br.com.irdbr.springbootionic.repositories.PedidoRepository;
import br.com.irdbr.springbootionic.security.UserSpringSecurity;
import br.com.irdbr.springbootionic.services.exceptions.AuthorizationException;
import br.com.irdbr.springbootionic.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private BoletoService boletoService;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
//	@Autowired
//	private ProdutoRepository produtoRepository;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private EmailService emailService;
	
	public Pedido buscar(Long id) {
		Optional<Pedido> obj = pedidoRepository.findById(id);
		
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
		
	}
	
	//@Transactional(readOnly = true)
	public Pedido insert(Pedido obj) {
		obj.setId(null);
		obj.setInstante(new Date());
		
		obj.setCliente(clienteService.find(obj.getCliente().getId()));
		
		obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);
		if (obj.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pgto = (PagamentoComBoleto) obj.getPagamento();
			boletoService.preencherPagamentoComBoleto(pgto, obj.getInstante());
		}
		
		obj = pedidoRepository.save(obj);
		pagamentoRepository.save(obj.getPagamento());
		
		for(ItemPedido ip: obj.getItens()) {
			ip.setDesconto(0.0);
			//Optional<Produto> prd = produtoRepository.findById(ip.getProduto().getId());
			//ip.setProduto(prd.orElse(null));
			ip.setProduto(produtoService.find(ip.getProduto().getId()));
			ip.setPreco(ip.getProduto().getPreco());
			ip.setPedido(obj);
		}
		itemPedidoRepository.saveAll(obj.getItens());
		
		//E-mail texto
		//emailService.sendOrderConfirmationEmail(obj);
		
		//E-mail HTML
		emailService.sendOrderConfirmationHtmlEmail(obj);
		return obj;
	}
	
	public Page<Pedido> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		UserSpringSecurity user = UserService.authenticated();
		
		if (user == null) {
			throw new AuthorizationException("Não autorizado");
		}
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		
		Cliente cliente =  clienteService.find(user.getId());
		
		return pedidoRepository.findByCliente(cliente, pageRequest);
		
	}
	
	
}
