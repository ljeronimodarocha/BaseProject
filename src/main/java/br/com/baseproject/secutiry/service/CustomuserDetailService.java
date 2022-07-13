package br.com.baseproject.secutiry.service;

import br.com.baseproject.applicationUser.entity.ApplicationUser;
import br.com.baseproject.applicationUser.repository.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class CustomuserDetailService implements UserDetailsService {
    private final ApplicationUserRepository applicationuserRepository;

    @Autowired
    public CustomuserDetailService(ApplicationUserRepository applicationuserRepository) {
        this.applicationuserRepository = applicationuserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ApplicationUser applicationUser = loadApplicationByUserName(username);
        return new CustomUserDatails(applicationUser);
    }

    public ApplicationUser loadApplicationByUserName(String username) {
        return applicationuserRepository.findByusername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    private final static class CustomUserDatails extends ApplicationUser implements UserDetails {

        public CustomUserDatails(ApplicationUser applicationUser) {
            super(applicationUser);
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            List<GrantedAuthority> authorityListProfessor = AuthorityUtils.createAuthorityList("ROLE_USER");
            return authorityListProfessor;
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
    }
}
