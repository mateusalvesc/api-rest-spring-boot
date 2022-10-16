package br.com.api.services;

import br.com.api.domain.User;
import br.com.api.domain.dto.UserDto;

import java.util.List;

public interface UserService {

    User findById(Long id);
    List<User> findAll();
    User create(UserDto user);
    User update(UserDto user);
    void delete(Long id);
}
