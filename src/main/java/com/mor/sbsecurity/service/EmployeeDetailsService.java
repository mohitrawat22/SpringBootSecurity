package com.mor.sbsecurity.service;

import com.mor.sbsecurity.model.Employee;
import com.mor.sbsecurity.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class EmployeeDetailsService implements UserDetailsService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Employee employee = employeeRepository.getEmployeeByUsername(username);
        if(employee  == null) {
            throw new UsernameNotFoundException(username);
        }
        User user = new User(employee.getUsername(), employee.getPassword(), new ArrayList<>());
        return user;
    }

}
