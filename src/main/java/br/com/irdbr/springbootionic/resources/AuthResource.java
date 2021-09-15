package br.com.irdbr.springbootionic.resources;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.irdbr.springbootionic.dto.EmailDTO;
import br.com.irdbr.springbootionic.security.JWTUtil;
import br.com.irdbr.springbootionic.security.UserSpringSecurity;
import br.com.irdbr.springbootionic.services.AuthService;
import br.com.irdbr.springbootionic.services.UserService;

@RestController
@RequestMapping(value = "/auth")
public class AuthResource {
	
	@Autowired
	private JWTUtil jwtUtil;
	
	@Autowired
	private AuthService service;

	@RequestMapping(value = "/refresh_token", method = RequestMethod.POST)
	public ResponseEntity<Void> refreshToken(HttpServletResponse response) {
		UserSpringSecurity user = UserService.authenticated();
		String token = jwtUtil.generateToken(user.getUsername());
		response.addHeader("Authorization", "Bearer " + token);
		
	/*  Por causa do cabeçalho personalizado: response.addHeader("Authorization", "Bearer " + token); 
	 	Expondo o header Authorization (problema de Cors)
		Cors (Cross-origin resource sharing): quais recursos (ex: quais métodos HTTP? quais headers?) estarão
		disponíveis para requisições advindas de origens diferentes? 
	 */
		response.addHeader("access-control-expose-headers", "Authorization");

		
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value = "/forgot", method = RequestMethod.POST)
	public ResponseEntity<Void> forgot(@Valid @RequestBody EmailDTO objDto) {
		
		service.sendNewPassword(objDto.getEmail());
		
		return ResponseEntity.noContent().build();
	}


}
