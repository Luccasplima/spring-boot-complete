package br.com.irdbr.springbootionic.services;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.irdbr.springbootionic.domain.Cliente;
import br.com.irdbr.springbootionic.repositories.ClienteRepository;
import br.com.irdbr.springbootionic.services.exceptions.ObjectNotFoundException;

@Service
public class AuthService {

	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private BCryptPasswordEncoder pe;
	
	@Autowired
	private EmailService emailService;
	
	private Random random = new Random();
	
	public void sendNewPassword(String email) {
		Cliente cliente = clienteRepository.findByEmail(email);
		
		if (cliente == null) {
			throw new ObjectNotFoundException("E-mail não encontrado.");
		}
		
		String newPass = newPassword();
		cliente.setSenha(pe.encode(newPass));
		clienteRepository.save(cliente);
		
		emailService.sendNewPasswordEmail(cliente, newPass);
		
	}

	private String newPassword() {
		// senha com 10 caracteres
		char[] vet = new char[10];
		for (int i = 0; i < vet.length; i++) {
			vet[i] = randomChar();
		}
		return new String(vet);
	}

	private char randomChar() {
		int opt = random.nextInt(3); //0, 1 e 2
		
		if (opt == 0) { // gera um digito
			return (char) (random.nextInt(10) + 48); // retorna de 0 a 9 (48 é o inicio do numero na tabela unicode-table.com)
		} else if (opt == 1) { // gera letra maiuscula
			return (char) (random.nextInt(26) + 65); // retorna de A a Z (26 letras, 65 é o 'A' na tabela unicode-table.com)
		} else { // gera letra minuscula
			return (char) (random.nextInt(26) + 97); // retorna de 'a' a 'z' (26 letras, 97 é o 'a' na tabela unicode-table.com)
		}
		
	}
	
	
}
