package br.com.irdbr.springbootionic.security;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.irdbr.springbootionic.domain.enums.Perfil;

public class UserSpringSecurity implements UserDetails {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String email;
	private String senha;
	private Collection<? extends GrantedAuthority> authorities;
	
	public UserSpringSecurity() {
	}
	
	public UserSpringSecurity(Long id, String email, String senha, Set<Perfil> perfis) {
		this.id = id;
		this.email = email;
		this.senha = senha;
		this.authorities = perfis.stream().map(x -> new SimpleGrantedAuthority(x.getDescricao())).collect(Collectors.toList());
	}

	public Long getId() {
		return id;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return senha;
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		// Por padrão a conta não expira
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// Por padrão a conta não está bloqueada
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// Por padrão a credencia não expira
		return true;
	}

	@Override
	public boolean isEnabled() {
		// Por padrão está habilitado
		return true;
	}

	// Testa se o usuário tem um certo perfil
	public boolean hasRole(Perfil perfil) {
		return getAuthorities().contains(new SimpleGrantedAuthority(perfil.getDescricao()));
	}

}
