package br.com.api.services.impl;

import br.com.api.domain.User;
import br.com.api.domain.dto.UserDto;
import br.com.api.repositories.UserRepository;
import br.com.api.services.exceptions.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

class UserServiceImplTest {

    public static final long ID = 1L;
    public static final String NAME = "Mateus";
    public static final String EMAIL = "mateus@email.com";
    public static final String PASSWORD = "12345678";
    public static final String OBJETO_NAO_ENCONTRADO = "Objeto não encontrado";
    public static final int INDEX = 0;

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    private User user;

    private UserDto userDto;

    private Optional<User> optionalUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startUser();
    }

    @Test
    void whenFindByIdThenReturnAnUserInstance() {
        when(userRepository.findById(anyLong())).thenReturn(optionalUser);

        User userResponse = userService.findById(ID);

        assertNotNull(userResponse);
        assertEquals(User.class, userResponse.getClass());
        assertEquals(ID, userResponse.getId());
        assertEquals(NAME, userResponse.getName());
        assertEquals(EMAIL, userResponse.getEmail());
    }

    @Test
    void whenFindByIdThenReturnAnObjectNotFoundException() {
        when(userRepository.findById((anyLong()))).thenThrow(new ObjectNotFoundException(OBJETO_NAO_ENCONTRADO));

        try {
            userService.findById(ID);
        } catch (Exception e ) {
            assertEquals(ObjectNotFoundException.class, e.getClass());
            assertEquals(OBJETO_NAO_ENCONTRADO, e.getMessage());
        }
    }

    @Test
    void whenFindAllThenReturnAnListOfUsers() {
        when(userRepository.findAll()).thenReturn(List.of(user));

        List<User> users = userService.findAll();

        assertNotNull(users);
        assertEquals(1, users.size());
        assertEquals(User.class, users.get(INDEX).getClass());
        assertEquals(ID, users.get(INDEX).getId());
        assertEquals(NAME, users.get(INDEX).getName());
        assertEquals(EMAIL, users.get(INDEX).getEmail());
        assertEquals(PASSWORD, users.get(INDEX).getPassword());
    }

    @Test
    void whenCreateThenReturnSucess() {
        when(userRepository.save(any())).thenReturn(user);

        User userResponse = userService.create(userDto);
        assertNotNull(userResponse);
        assertEquals(User.class, userResponse.getClass());
        assertEquals(ID, userResponse.getId());
        assertEquals(NAME, userResponse.getName());
        assertEquals(EMAIL, userResponse.getEmail());
        assertEquals(PASSWORD, userResponse.getPassword());
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    private void startUser() {
        user = new User(ID, NAME, EMAIL, PASSWORD);
        userDto = new UserDto(ID, NAME, EMAIL, PASSWORD);
        optionalUser = Optional.of(new User(ID, NAME, EMAIL, PASSWORD));
    }
}