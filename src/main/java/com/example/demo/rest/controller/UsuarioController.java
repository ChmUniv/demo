package com.example.demo.rest.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.persistence.model.Usuario;
import com.example.demo.persistence.repository.UsuarioRepository;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class UsuarioController {
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	@GetMapping("/usuarios")
	public ResponseEntity<List<Usuario>> getAllUsuarios(@RequestParam(required = false) String nombre,
														@RequestParam(required = false) String apellido1,
														@RequestParam(required = false) String apellido2,
														@RequestParam(required = false) String dni) {
		try {
			List<Usuario> usuarios = new ArrayList<Usuario>();

			if (nombre == null && apellido1 == null && apellido2 == null && dni == null ) {
				usuarioRepository.findAll().forEach(usuarios::add);
			}
			
			if (nombre != null && apellido1 == null && apellido2 == null && dni == null ) {
				usuarioRepository.findByNombreContaining(nombre).forEach(usuarios::add);
			}
			
			if (nombre == null && apellido1 != null && apellido2 == null && dni == null ) {
				usuarioRepository.findByApellido1Containing(apellido1).forEach(usuarios::add);
			}
			
			if (nombre == null && apellido1 == null && apellido2 != null && dni == null ) {
				usuarioRepository.findByApellido2Containing(apellido2).forEach(usuarios::add);
			}
			
			if (nombre == null && apellido1 == null && apellido2 == null && dni != null ) {
				usuarioRepository.findByDniContaining(dni).forEach(usuarios::add);
			}
			
			if (usuarios.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			
			
			return new ResponseEntity<>(usuarios, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/usuarios/{id}")
	public ResponseEntity<Usuario> getUsuarioById(@PathVariable("id") Integer id) {
		Optional<Usuario> usuarioData = usuarioRepository.findById(id);
		
		if (usuarioData.isPresent()) {
			return new ResponseEntity<>(usuarioData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping("/usuarios")
	public ResponseEntity<Usuario> createUsuario(@RequestBody Usuario usuario) {
		try {
			Usuario usu = usuarioRepository.save(new Usuario(usuario.getId(),
															 usuario.getNombre(), 
															 usuario.getApellido1(), 
															 usuario.getApellido1(),
															 usuario.getDni()));
			return new ResponseEntity<>(usu, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/usuarios/{id}")
	public ResponseEntity<Usuario> updateUsuario(@PathVariable("id") Integer id, @RequestBody Usuario usuario) {
		Optional<Usuario> usuarioData = usuarioRepository.findById(id);
		if (usuarioData.isPresent()) {
			Usuario _usuario = usuarioData.get();
			_usuario.setNombre(usuario.getNombre());
			_usuario.setApellido1(usuario.getApellido1());
			_usuario.setApellido2(usuario.getApellido2());
			_usuario.setDni(usuario.getDni());
			return new ResponseEntity<>(usuarioRepository.save(_usuario), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("/usuarios/{id}")
	public ResponseEntity<HttpStatus> deleteUsuario(@PathVariable("id") Integer id) {
		try {
			usuarioRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/usuarios")
	public ResponseEntity<HttpStatus> deleteAllUsuarios() {
		try {
			usuarioRepository.deleteAll();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	

}