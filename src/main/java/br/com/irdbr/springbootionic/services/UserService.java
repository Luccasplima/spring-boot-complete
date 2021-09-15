package br.com.irdbr.springbootionic.services;

import org.springframework.security.core.context.SecurityContextHolder;

import br.com.irdbr.springbootionic.security.UserSpringSecurity;

public class UserService {
	
	// Retorna o usuário logado
	
	public static UserSpringSecurity authenticated() {
		try {
			return (UserSpringSecurity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		} catch (Exception e) {
			return null;
		}
		
	}

}
