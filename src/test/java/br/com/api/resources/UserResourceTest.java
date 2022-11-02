package br.com.api.resources;

import br.com.api.domain.User;
import br.com.api.domain.dto.UserDto;
import br.com.api.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
        ServletRequestAttributes servletRequestAttributes = new ServletRequestAttributes(mockHttpServletRequest);
        RequestContextHolder.setRequestAttributes(servletRequestAttributes);
        startUser();
    }

    @Test
    void whenFindByIdThenReturnSuccess() {
        when(userService.findById(anyLong())).thenReturn(user);
        when(modelMapper.map(any(), any())).thenReturn(userDto);
        ResponseEntity<UserDto> userResponse = userResource.findById(ID);
        assertNotNull(userResponse);
        assertNotNull(userResponse.getBody());
        assertEquals(ResponseEntity.class, userResponse.getClass());
        assertEquals(UserDto.class, userResponse.getBody().getClass());

        assertEquals(ID, userResponse.getBody().getId());
        assertEquals(NAME, userResponse.getBody().getName());
        assertEquals(EMAIL, userResponse.getBody().getEmail());
    }

    @Test
    void whenFindAllThenReturnAListOfUserDto() {
        when(userService.findAll()).thenReturn(List.of(user));
        when(modelMapper.map(any(), any())).thenReturn(userDto);
        ResponseEntity<List<UserDto>> userResponse = userResource.findAll();
        assertNotNull(userResponse);
        assertNotNull(userResponse.getBody());
        assertEquals(HttpStatus.OK, userResponse.getStatusCode());
        assertEquals(ArrayList.class, userResponse.getBody().getClass());
        assertEquals(UserDto.class, userResponse.getBody().get(INDEX).getClass());

        assertEquals(ID, userResponse.getBody().get(INDEX).getId());
        assertEquals(NAME, userResponse.getBody().get(INDEX).getName());
        assertEquals(EMAIL, userResponse.getBody().get(INDEX).getEmail());
    }

    @Test
    void whenCreateThenReturnCreated() {
        when(userService.create(any())).thenReturn(user);
        ResponseEntity<UserDto> response = userResource.create(userDto);
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getHeaders().get("Location"));
    }

    @Test
    void whenUpdateThenReturnSuccess() {
        when(userService.update(userDto)).thenReturn(user);
        when(modelMapper.map(any(), any())).thenReturn(userDto);
        ResponseEntity<UserDto> userResponse = userResource.update(ID, userDto);
        assertNotNull(userResponse);
        assertNotNull(userResponse.getBody());
        assertEquals(ResponseEntity.class, userResponse.getClass());
        assertEquals(UserDto.class, userResponse.getBody().getClass());

        assertEquals(ID, userResponse.getBody().getId());
        assertEquals(NAME, userResponse.getBody().getName());
        assertEquals(EMAIL, userResponse.getBody().getEmail());

    }

    @Test
    void testDelete() {
    }

    private void startUser() {
        user = new User(ID, NAME, EMAIL, PASSWORD);
        userDto = new UserDto(ID, NAME, EMAIL, PASSWORD);
    }
}