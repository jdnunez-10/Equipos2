package hn.unah.reposicion2.services;

import java.util.Optional;
import java.util.Random;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hn.unah.reposicion2.entities.Equipos;
import hn.unah.reposicion2.entities.Posiciones;
import hn.unah.reposicion2.repositories.EquipoRepository;
import hn.unah.reposicion2.repositories.PosicionesRepository;

@Service
public class EquipoService {
    
    @Autowired
    private EquipoRepository equipoRepository;

    @Autowired
    private PosicionesRepository posicionRepository;

    private final Random random = new Random();

    public String crearEquipo(Equipos equipo) {

        
        Equipos nvoEquipo = new Equipos();
        nvoEquipo.setNombre(equipo.getNombre());
        nvoEquipo.setAtaque(equipo.getAtaque());
        nvoEquipo.setDefensa(equipo.getDefensa());

        this.equipoRepository.save(nvoEquipo);
        return "Equipos creado satisfactoriamente";
    }

    public String eliminarEquipo(int codigoEquipo){

        Optional<Equipos> optionalEquipo = this.equipoRepository.findById(codigoEquipo);

        if(optionalEquipo.isPresent()){

            Equipos equipo = optionalEquipo.get();

            if(equipo.getPosiciones()==null){
                
                this.equipoRepository.delete(equipo);
                return "Equipos eliminado satisfactoriamente";
            }
            return "No se puede eliminar el equipo porque tiene una posicion";
        }
        return "No existe el equipo a eliminar";

    }

    public Equipos buscarEquipo(int codigoEquipo){
        
        Optional <Equipos> equipo = this.equipoRepository.findById(codigoEquipo);

        if(equipo.isPresent()){

            return equipo.get();
        }
        return null;

    }

    public void simularPartidos(){

        List<Equipos> equipos = (List<Equipos>) equipoRepository.findAll();
        
    // Crear los equipos faltantes
    if (equipos.size() < 6) {

        for (int i = equipos.size() + 1; i <= 6; i++) {

            Equipos equipo = new Equipos();
            equipo.setNombre("Equipos " + i);
            equipo.setAtaque(random.nextDouble() * 100);
            equipo.setDefensa(random.nextDouble() * 100);
            equipoRepository.save(equipo);
        }

        equipos = (List<Equipos>) equipoRepository.findAll(); 
    }

    // Asegurar que todos los equipos tengan una posición inicial
    for (Equipos equipo : equipos) {

        if (!posicionRepository.existsById(equipo.getCodigoEquipo())) {
            
            Posiciones posicion = new Posiciones();

            posicion.setEquipos(equipo);
            posicion.setEmpates(0);
            posicion.setGanados(0);
            posicion.setPerdidos(0);
            posicion.setGolesFavor(0);
            posicion.setGolesContra(0);
            posicion.setPuntos(0);
            posicionRepository.save(posicion);
        }
    }

    // Simular partidos ida y vuelta
    for (int i = 0; i < equipos.size(); i++) {
        for (int j = i + 1; j < equipos.size(); j++) {
            Equipos equipoA = equipos.get(i);
            Equipos equipoB = equipos.get(j);
            
            // Partido ida
            int golesA = random.nextInt(6);
            int golesB = random.nextInt(6);
            
            actualizarPosiciones(equipoA, equipoB, golesA, golesB );

            // Partido vuelta
            golesA = random.nextInt(6);
            golesB = random.nextInt(6);
            actualizarPosiciones(equipoA, equipoB,golesA, golesB  );
        }
    }
}



public void actualizarPosiciones(Equipos equipoA, Equipos equipoB, int golesA, int golesB) {

    // Buscar o crear posición del equipo A
    Posiciones posicionA = posicionRepository.findById(equipoA.getCodigoEquipo())
            .orElse(new Posiciones(equipoA));

    // Buscar o crear posición del equipo B
    Posiciones posicionB = posicionRepository.findById(equipoB.getCodigoEquipo())
            .orElse(new Posiciones(equipoB));

    // Sumar goles a favor y en contra
    posicionA.setGolesFavor(posicionA.getGolesFavor() + golesA);
    posicionA.setGolesContra(posicionA.getGolesContra() + golesB);

    posicionB.setGolesFavor(posicionB.getGolesFavor() + golesB);
    posicionB.setGolesContra(posicionB.getGolesContra() + golesA);

    // Calcular resultado
    if (golesA > golesB) {
        posicionA.setGanados(posicionA.getGanados() + 1);
        posicionA.setPuntos(posicionA.getPuntos() + 3);
        posicionB.setPerdidos(posicionB.getPerdidos() + 1);
        
    } else if (golesA < golesB) {
        posicionB.setGanados(posicionB.getGanados() + 1);
        posicionB.setPuntos(posicionB.getPuntos() + 3);
        posicionA.setPerdidos(posicionA.getPerdidos() + 1);

    } else {
        posicionA.setEmpates(posicionA.getEmpates() + 1);
        posicionB.setEmpates(posicionB.getEmpates() + 1);
        posicionA.setPuntos(posicionA.getPuntos() + 1);
        posicionB.setPuntos(posicionB.getPuntos() + 1);
    }

    // Guardar actualizaciones
    posicionRepository.save(posicionA);
    posicionRepository.save(posicionB);
}


public List<Posiciones> obtenerTabla(){

    return (List<Posiciones>) posicionRepository.findAll();
}

}



