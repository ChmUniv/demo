package com.example.demo.persistence.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.persistence.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
	  List<Usuario> findByNombreContaining(String nombre);
	  List<Usuario> findByApellido1Containing(String apellido1);
	  List<Usuario> findByApellido2Containing(String apellido2);
	  List<Usuario> findByDniContaining(String dni);

}

