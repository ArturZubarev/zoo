package org.zubarev.instazoo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.zubarev.instazoo.entity.User;
import org.zubarev.instazoo.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Component
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository repository;
    @Autowired
    public CustomUserDetailsService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=repository.findUserByEmail(username).orElseThrow(()->new UsernameNotFoundException(
                "Имя пользователя не найдено"+username));

        return build(user) ;
    }
    public User loadUserById(Long userId){
       return repository.findUserById(userId).orElse(null);
    }
    public static User build(User user){
        List<GrantedAuthority> authorities=user.getRole()
                .stream()
                .map(role->new SimpleGrantedAuthority(role.name()))
                .collect(Collectors.toList());
        return new User(user.getId(), user.getUsername(),
                user.getEmail(), user.getPassword(), authorities);

    }
}
