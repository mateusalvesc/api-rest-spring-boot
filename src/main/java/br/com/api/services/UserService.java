package br.com.api.services;

import br.com.api.domain.User;

public interface UserService {

    User findById(Long id);
}
