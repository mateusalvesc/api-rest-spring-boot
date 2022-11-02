package br.com.api.resources;

import br.com.api.domain.User;
import br.com.api.domain.dto.UserDto;
import br.com.api.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.mockito.Mockito.*;

class UserResourceTest {

    public static final long ID = 1L;
    public static final String NAME = "Mateus";
    public static final String EMAIL = "mateus@email.com";
    public static final String PASSWORD = "12345678";
    public static final String OBJETO_NAO_ENCONTRADO = "Objeto não encontrado";
    public static final int INDEX = 0;
    public static final String E_MAIL_JA_CADASTRADO_NO_SISTEMA = "E-mail já cadastrado no sistema";

    private User user;
    private UserDto userDto;

    @Mock
    ModelMapper modelMapper;
    @Mock
    UserService userService;
    @InjectMocks
    UserResource userResource;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startUser();
    }

    @Test
    void testFindById() {
        when(userService.findById(anyLong())).thenReturn(new User(Long.valueOf(1), "name", "email", "password"));

        ResponseEntity<UserDto> result = userResource.findById(Long.valueOf(1));
        Assertions.assertEquals(null, result);
    }

    @Test
    void testFindAll() {
        when(userService.findAll()).thenReturn(List.of(new User(Long.valueOf(1), "name", "email", "password")));

        ResponseEntity<List<UserDto>> result = userResource.findAll();
        Assertions.assertEquals(null, result);
    }

    @Test
    void testCreate() {
        when(userService.create(any())).thenReturn(new User(Long.valueOf(1), "name", "email", "password"));

        ResponseEntity<UserDto> result = userResource.create(new UserDto(Long.valueOf(1), "name", "email", "password"));
        Assertions.assertEquals(null, result);
    }

    @Test
    void testUpdate() {
        when(userService.update(any())).thenReturn(new User(Long.valueOf(1), "name", "email", "password"));

        ResponseEntity<UserDto> result = userResource.update(Long.valueOf(1), new UserDto(Long.valueOf(1), "name", "email", "password"));
        Assertions.assertEquals(null, result);
    }

    @Test
    void testDelete() {
        ResponseEntity<UserDto> result = userResource.delete(Long.valueOf(1));
        Assertions.assertEquals(null, result);
    }

    private void startUser() {
        user = new User(ID, NAME, EMAIL, PASSWORD);
        userDto = new UserDto(ID, NAME, EMAIL, PASSWORD);
    }
}