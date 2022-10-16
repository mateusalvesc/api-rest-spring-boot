package br.com.api.services.impl;

import br.com.api.domain.User;
import br.com.api.domain.dto.UserDto;
import br.com.api.repositories.UserRepository;
import br.com.api.services.UserService;
import br.com.api.services.exceptions.DataIntegratyViolationException;
import br.com.api.services.exceptions.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public User findById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User create(UserDto user) {
        findByEmail(user);
        return userRepository.save(modelMapper.map(user, User.class));
    }

    @Override
    public User update(UserDto user) {
        findByEmail(user);
        return userRepository.save(modelMapper.map(user, User.class));
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    private void findByEmail(UserDto user) {
        Optional<User> emailUser = userRepository.findByEmail(user.getEmail());
        if (emailUser.isPresent() && !emailUser.get().getId().equals(user.getId())) {
            throw new DataIntegratyViolationException("E-mail já cadastrado no sistema");
        }

    }
}
