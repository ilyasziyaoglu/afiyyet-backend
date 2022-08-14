package com.afiyyet.common.user.service;

import com.afiyyet.brand.db.repository.BrandRepository;
import com.afiyyet.client.user.UserRequest;
import com.afiyyet.client.user.UserResponse;
import com.afiyyet.common.basemodel.service.AbstractBaseService;
import com.afiyyet.common.basemodel.service.ServiceResult;
import com.afiyyet.common.user.db.entity.User;
import com.afiyyet.common.user.db.repository.UserRepository;
import com.afiyyet.common.user.mapper.UserMapper;
import com.afiyyet.common.user.mapper.UserUpdateMapper;
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

    private final BrandRepository brandRepository;

    public UserService(UserRepository repository, UserMapper mapper, UserUpdateMapper updateMapper, BrandRepository brandRepository) {
        this.repository = repository;
        this.mapper = mapper;
        this.updateMapper = updateMapper;
        this.brandRepository = brandRepository;
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
