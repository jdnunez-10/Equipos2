package hn.unah.reposicion2.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name ="equipos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Equipos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="codigoEquipo")
    private int codigoEquipo;

    private String nombre;

    private double ataque;

    private double defensa;

    @OneToOne(mappedBy = "equipos", cascade = CascadeType.ALL) 
    private Posiciones posiciones;

}