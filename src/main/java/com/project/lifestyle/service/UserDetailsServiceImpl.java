//package com.project.lifestyle.service;
//
//import com.project.lifestyle.model.User;
//import com.project.lifestyle.repository.UserRepository;
//import lombok.AllArgsConstructor;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.Collection;
//import java.util.Optional;
//
//import static java.util.Collections.singletonList;
//
//@Service
//@AllArgsConstructor
//public class UserDetailsServiceImpl implements UserDetailsService {
//
//    private final UserRepository userRepository;
//
//    @Override
//    @Transactional(readOnly = true)
//    public UserDetails loadUserByUsername(String username){
//        Optional<User> userOptional = userRepository.findByUsername(username);
//        User user = userOptional.orElseThrow(() -> new UsernameNotFoundException("No user " +
//                "Found with username : " + username));
//        return new com.project.lifestyle.service.CustomUserDetails( user);
//    }
//}
