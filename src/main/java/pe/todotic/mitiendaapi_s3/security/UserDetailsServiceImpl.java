
package pe.todotic.mitiendaapi_s3.security;

        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.security.core.userdetails.UserDetails;
        import org.springframework.security.core.userdetails.UserDetailsService;
        import org.springframework.security.core.userdetails.UsernameNotFoundException;
        import org.springframework.stereotype.Service;
        import pe.todotic.mitiendaapi_s3.model.Usuario;
        import pe.todotic.mitiendaapi_s3.repository.UsuarioRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository
                .findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));

        return new UserDetailsImpl(usuario);
    }

}
