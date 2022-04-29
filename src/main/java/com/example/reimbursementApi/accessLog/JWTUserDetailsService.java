package com.example.reimbursementApi.accessLog;

import com.example.reimbursementApi.Repository.EmployeeRepository;
import com.example.reimbursementApi.model.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JWTUserDetailsService implements UserDetailsService {
    @Autowired
    EmployeeRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    private Logger logger = LoggerFactory.getLogger(JWTUserDetailsService.class);
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Employee> user = userRepository.findByUsername(username);


        if(!user.isPresent()) {
            throw new UsernameNotFoundException("User with username " + username + " was not found");
        }
        System.out.println(user.get().getRoles());
        return User.build(user.get());
    }
}
