package com.puchocoding.app.usuario;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;


@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
class UsuarioRepositoryTest {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Test
	public void testCrearRoles() {
		Rol rolAdmin = new Rol("Administrador");
		Rol rolEditor = new Rol("Editor");
		Rol rolVisitante = new Rol("Visitante");
		
		entityManager.persist(rolAdmin);
		entityManager.persist(rolEditor);
		entityManager.persist(rolVisitante);
	}

	@Test
	public void testCrearNuevoUsuarioConUnRol() {
		Rol rolAdmin = entityManager.find(Rol.class, 1);
		Usuario usuario = new Usuario("christian90@gmail.com","12345");
		
		usuario.anhadirRol(rolAdmin);
		usuarioRepository.save(usuario);
	}
	
	@Test
	public void testCrearNuevoUsuarioConDosRoles() {
		Rol rolEditor = entityManager.find(Rol.class, 2);
		Rol rolVisitante = entityManager.find(Rol.class, 3);
		Usuario usuario = new Usuario("yo@gmail.com","53413");
		
		usuario.anhadirRol(rolEditor);
		usuario.anhadirRol(rolVisitante);
		
		usuarioRepository.save(usuario);
	}
	
	@Test
	public void testEliminarRolDeUnUsuarioExistente() {
		Usuario usuario = usuarioRepository.findById(1).get();
		Rol rol = new Rol(2);
		usuario.eliminarRoles(rol);
	}
	
	@Test
	public void testCrearNuevoUsuarioConNuevoRol() {
		Rol rolVendedor = new Rol("Vendedor");
		Usuario usuario = new Usuario("paco@gmail.com","435343");
		
		usuario.anhadirRol(rolVendedor);
		usuarioRepository.save(usuario);
	}
	
	@Test
	public void testObtenerUsuario() {
		Usuario usuario = usuarioRepository.findById(1).get();
		entityManager.detach(usuario);
		
		System.out.println(usuario);
		System.out.println(usuario.getRoles());
	}
	
	@Test
	public void testEliminarUsuario() {
		usuarioRepository.deleteById(2);
	}
}
