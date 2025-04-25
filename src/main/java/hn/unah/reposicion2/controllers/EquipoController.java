package hn.unah.reposicion2.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hn.unah.reposicion2.entities.Equipos;
import hn.unah.reposicion2.entities.Posiciones;
import hn.unah.reposicion2.services.EquipoService;




@RestController
@RequestMapping("/equipos")
public class EquipoController {
    
    @Autowired
    private EquipoService equipoService;
    
    @PostMapping("/crear")
    public String crearEquipo(@RequestBody Equipos equipo) {
    
        return this.equipoService.crearEquipo(equipo);
    }
    
    @DeleteMapping("/eliminar/{codigoEquipo}")
    public String eliminarEquipo(@PathVariable(name ="codigoEquipo")int codigoEquipo){

        return this.equipoService.eliminarEquipo(codigoEquipo);
    }

    @GetMapping("/buscar/{codigoEquipo}")
    public Equipos buscarEquipo(@PathVariable(name ="codigoEquipo") int codigoEquipo) {
        return this.equipoService.buscarEquipo(codigoEquipo);
    }

    @PostMapping("/simular-partidos")
    public String simularPartidos() {
        this.equipoService.simularPartidos();
        return "Partidos simulados satisfactoriamente";
    }
    
    @GetMapping("/posiciones")
    public List<Posiciones> obtenerPosiciones() {
        return this.equipoService.obtenerTabla();
    }
}
