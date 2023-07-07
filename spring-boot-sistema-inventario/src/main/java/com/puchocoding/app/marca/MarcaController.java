package com.puchocoding.app.marca;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.puchocoding.app.categoria.Categoria;
import com.puchocoding.app.categoria.CategoriaRepository;
import com.puchocoding.app.producto.Producto;

@Controller
public class MarcaController {

	@Autowired
	private MarcaRepository marcaRepository;
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@GetMapping("/marcas/nueva")
	public String mostrarFormularioDeCrearNuevaMarca(Model model) {
		List<Categoria> listaCategorias = categoriaRepository.findAll();
		model.addAttribute("listaCategorias",listaCategorias);
		model.addAttribute("marca", new Marca());
		return "marca_formulario";
	}
	
	@PostMapping("/marcas/guardar")
	public String guardarMarca(Marca marca) {
		marcaRepository.save(marca);
		return "redirect:/";
	}
	
	@GetMapping("/marcas")
	public String listarMarcas(Model model) {
		List<Marca> listaMarcas = marcaRepository.findAll();
		model.addAttribute("listaMarcas",listaMarcas);
		return "marcas";
	}
	
	@GetMapping("/marcas/editar/{id}")
	public String mostrarFormularioDeModificarMarca(@PathVariable("id") Integer id, Model model) {
		List<Categoria> listaCategorias = categoriaRepository.findAll();
		Marca marca = marcaRepository.findById(id).get();
		model.addAttribute("listaCategorias",listaCategorias);
		model.addAttribute("marca", marca);
		return "marca_formulario";
	}
}
