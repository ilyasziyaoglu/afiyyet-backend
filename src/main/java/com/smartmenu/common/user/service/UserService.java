package com.smartmenu.common.user.service;

import com.smartmenu.client.user.UserRequest;
import com.smartmenu.client.user.UserResponse;
import com.smartmenu.common.basemodel.service.AbstractBaseService;
import com.smartmenu.common.basemodel.service.ServiceResult;
import com.smartmenu.common.user.db.entity.User;
import com.smartmenu.common.user.db.repository.UserRepository;
import com.smartmenu.common.user.mapper.UserMapper;
import com.smartmenu.common.user.mapper.UserUpdateMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */

@Service
public class UserService extends AbstractBaseService<UserRequest, User, UserResponse, UserMapper> implements UserDetailsService {
    private final UserRepository repository;
    private final UserMapper mapper;
    private final UserUpdateMapper updateMapper;

    public UserService(UserRepository repository, UserMapper mapper, UserUpdateMapper updateMapper) {
        this.repository = repository;
        this.mapper = mapper;
        this.updateMapper = updateMapper;
    }

    @Override
    public UserRepository getRepository() {
        return repository;
    }

    @Override
    public UserMapper getMapper() {
        return mapper;
    }

    @Override
    public UserUpdateMapper getUpdateMapper() {
        return updateMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = repository.findByUsername(username);
        user.orElseThrow(() -> new UsernameNotFoundException("Username not found with value: " + username));
        return user.get();
    }

    public ServiceResult<User> getUserByUsername(String username) {
        Optional<User> user = repository.findByUsername(username);
        return user.map(ServiceResult::new).orElseGet(() -> new ServiceResult<>(HttpStatus.NOT_FOUND, "User not found by the username: " + username));
    }


}
