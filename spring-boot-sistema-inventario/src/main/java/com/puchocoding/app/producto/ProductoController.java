package com.puchocoding.app.producto;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.puchocoding.app.categoria.Categoria;
import com.puchocoding.app.categoria.CategoriaRepository;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class ProductoController {

	@Autowired
	private ProductoRepository productoRepository;
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@GetMapping("productos/nuevo")
	public String mostrarFormularioDeNuevoProducto(Model model) {
		List<Categoria> listaCategorias = categoriaRepository.findAll();
		model.addAttribute("producto",new Producto());
		model.addAttribute("listaCategorias",listaCategorias);
		return "producto_formulario";
	}
	
	@PostMapping("/productos/guardar")
	public String guardarProducto(Producto producto, HttpServletRequest request) {
		String[] detallesIds = request.getParameterValues("detallesIds");
		String[] detallesNombres = request.getParameterValues("detallesNombre");
		String[] detallesValores = request.getParameterValues("detallesValor");
		
		for(int i= 0; i< detallesNombres.length; i++) {
			if(detallesIds !=null && detallesIds.length > 0) {
				producto.setDetalle(Integer.valueOf(detallesIds[i]),detallesNombres[i],detallesValores[i]);
			}else {
				producto.anhadirDetalles(detallesNombres[i],detallesValores[i]);
			}
		}
		productoRepository.save(producto);
		return "redirect:/";
	}
	
	@GetMapping("/productos")
	public String listarProductos(Model model) {
		List<Producto> listaProductos = productoRepository.findAll();
		model.addAttribute("listaProductos",listaProductos);
		return "productos";
	}
	
	@GetMapping("/productos/editar/{id}")
	public String mostrarFormularioDeModificarProducto(@PathVariable("id") Integer id, Model model) {
		Producto producto = productoRepository.findById(id).get();
		model.addAttribute("producto",producto);
		List<Categoria> listaCategorias = categoriaRepository.findAll();
		model.addAttribute("listaCategorias",listaCategorias);
		return "producto_formulario";
	}
	
	@GetMapping("/productos/eliminar/{id}")
	public String eliminarProducto(@PathVariable("id") Integer id, Model model) {
		productoRepository.deleteById(id);
		return "redirect:/productos";
	}
}
