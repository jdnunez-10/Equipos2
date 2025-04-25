package hn.unah.reposicion2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hn.unah.reposicion2.entities.Posiciones;

@Repository
public interface PosicionesRepository extends JpaRepository<Posiciones, Integer>{
    
}
