package pe.todotic.mitiendaapi_s3.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pe.todotic.mitiendaapi_s3.model.Usuario;


import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class UserDetailsImpl implements UserDetails {
    private final Integer id;
    private final String name;
    private final String username;
    private final String password;
    private final String role;
    private final List<GrantedAuthority> grantedAuthorityList;

    public UserDetailsImpl(Usuario usuario) {
        id = usuario.getId();
        name = usuario.getNombres();
        username = usuario.getEmail();
        password = usuario.getPassword();
        role = usuario.getRol().name();
        this.grantedAuthorityList = Collections.singletonList(
                new SimpleGrantedAuthority("ROLE_" + usuario.getRol())
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorityList;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }
}

