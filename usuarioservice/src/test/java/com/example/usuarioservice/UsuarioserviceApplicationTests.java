package com.example.usuarioservice;

import com.example.usuarioservice.Client.UsuarioClient;
import com.example.usuarioservice.Dto.RolUsuarioDTO;
import com.example.usuarioservice.Model.Usuario;
import com.example.usuarioservice.Repository.UsuarioRepository;
import com.example.usuarioservice.Service.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UsuarioserviceApplicationTests {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private UsuarioClient usuarioClient;

    @InjectMocks
    private UsuarioService usuarioService;

    private Usuario usuario;
    private RolUsuarioDTO rolUsuarioDTO;

    @BeforeEach
    void setUp(){
        usuario = new Usuario();
        usuario.setIdUsuario(1);
        usuario.setRutUsuario("12345678-9");
        usuario.setEmailUsuario("usuario@gmail.com");
        usuario.setContrasenaUsuario("gabo123");
        usuario.setPnombreUsuario("Gabriel");
        usuario.setSnombreUsuario("Bernabé");
        usuario.setAppaternoUsuario("Bustamante");
        usuario.setApmaternoUsuario("Flores");
        usuario.setTelefonoUsuario("987654321");
        usuario.setDireccionUsuario("Calle 123");
        usuario.setEstadoUsuario("ACTIVO");
        usuario.setIdRol(1);

        rolUsuarioDTO = new RolUsuarioDTO();
        rolUsuarioDTO.setIdRol(1);
        rolUsuarioDTO.setNombreRol("ADOPTANTE");
    }

    @Test
    @DisplayName("Buscar usuario por ID existente")
    void buscarUsuarioPorIDExistente(){
        //Given
        when(usuarioRepository.findById(1)).thenReturn(Optional.of(usuario));

        //When
        Usuario resultado = usuarioService.buscarPorId(1);

        //Then
        assertNotNull(resultado);
        assertEquals(1, resultado.getIdUsuario());
        assertEquals("Gabriel", resultado.getPnombreUsuario());
    }

    @Test
    @DisplayName("Buscar usuario por ID inexistente")
    void buscarUsuarioPorIdInexistente(){
        //Given
        when(usuarioRepository.findById(99)).thenReturn(Optional.empty());

        //When
        Usuario resultado = usuarioService.buscarPorId(99);

        //Then
        assertNull(resultado);
    }

    @Test
    @DisplayName("Guardar usuario")
    void guardarUsuario() {
        // Given
        when(usuarioRepository.existsByRutUsuarioIgnoreCase("12345678-9")).thenReturn(false);
        when(usuarioRepository.existsByEmailUsuarioIgnoreCase("usuario@gmail.com")).thenReturn(false);
        when(usuarioClient.getRolById(1)).thenReturn(rolUsuarioDTO);
        when(usuarioRepository.save(usuario)).thenReturn(usuario);

        // When
        Usuario resultado = usuarioService.guardarUsuario(usuario);

        // Then
        assertNotNull(resultado);
        assertEquals("12345678-9", resultado.getRutUsuario());
        assertEquals("usuario@gmail.com", resultado.getEmailUsuario());
        assertEquals(1, resultado.getIdRol());
    }

    @Test
    @DisplayName("No guardar usuario si el RUT ya existe")
    void noGuardarUsuarioSiRutExiste() {
        // Given
        when(usuarioRepository.existsByRutUsuarioIgnoreCase("12345678-9")).thenReturn(true);

        // When
        Usuario resultado = usuarioService.guardarUsuario(usuario);

        // Then
        assertNull(resultado);
    }
}
