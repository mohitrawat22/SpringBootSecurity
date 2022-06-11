package com.mor.sbsecurity.service;

import com.mor.sbsecurity.model.Employee;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

public interface IEmployeeService {

    Employee saveEmployee(Employee employee);

    String getAuthenticationToken(Employee employee) throws Exception;

}
