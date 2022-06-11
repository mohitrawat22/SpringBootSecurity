package com.mor.sbsecurity.service;

import com.mor.sbsecurity.model.Employee;
import com.mor.sbsecurity.repository.EmployeeRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Transactional
@Service
public class EmployeeService implements IEmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private EmployeeDetailsService employeeDetailsService;

    @Override
    public Employee saveEmployee(Employee employee) {
        Employee savedEmployee = employeeRepository.save(employee);
        return savedEmployee;
    }

    @Override
    public String getAuthenticationToken(Employee employee) throws Exception {

        String userName = employee.getUsername();
        try {
            authenticationManager.authenticate(
              new UsernamePasswordAuthenticationToken(userName, employee.getPassword())
            );
        }
        catch(BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }
        UserDetails userDetails = employeeDetailsService.loadUserByUsername(userName);

        String jwtToken = Jwts.builder()
                .setSubject(userName)
                .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(1)))
                .signWith(SignatureAlgorithm.HS512, "secret")
                .compact();
        return jwtToken;
    }
}
