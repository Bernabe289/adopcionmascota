package com.example.rolservice;

import com.example.rolservice.Model.RolUsuario;
import com.example.rolservice.Repository.RolUsuarioRepository;
import com.example.rolservice.Service.RolUsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RolserviceApplicationTests {

    @Mock
    private RolUsuarioRepository rolUsuarioRepository;

    @InjectMocks
    private RolUsuarioService rolUsuarioService;

    private RolUsuario rolUsuario;

    @BeforeEach
    void setUp() {
        rolUsuario = new RolUsuario();
        rolUsuario.setIdRol(1);
        rolUsuario.setNombreRol("ADMIN");
    }

    @Test
    @DisplayName("Buscar rol por ID existente")
    void buscarRolPorIdExistente() {
        // Given
        when(rolUsuarioRepository.findById(1)).thenReturn(Optional.of(rolUsuario));

        // When
        RolUsuario resultado = rolUsuarioService.buscarPorId(1);

        // Then
        assertNotNull(resultado);
        assertEquals(1, resultado.getIdRol());
        assertEquals("ADMIN", resultado.getNombreRol());

    }

    @Test
    @DisplayName("Buscar rol por ID inexistente")
    void buscarRolPorIdInexistente() {
        // Given
        when(rolUsuarioRepository.findById(99)).thenReturn(Optional.empty());

        // When
        RolUsuario resultado = rolUsuarioService.buscarPorId(99);

        // Then
        assertNull(resultado);

    }

    @Test
    @DisplayName("Listar roles")
    void listarRoles() {
        // Given
        when(rolUsuarioRepository.findAll()).thenReturn(List.of(rolUsuario));

        // When
        List<RolUsuario> resultado = rolUsuarioService.listarRoles();

        // Then
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("ADMIN", resultado.get(0).getNombreRol());

    }
}