package hn.unah.reposicion2.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name ="posiciones")
@NoArgsConstructor
@Getter
@Setter
public class Posiciones {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="codigoEquipo")
    private int codigoEquipo;

    private int empates;

    private int ganados;

    private int perdidos;

    private int golesFavor;

    private int golesContra;

    private int puntos;


    @OneToOne
    @MapsId
    @JoinColumn(name ="codigoEquipo", referencedColumnName ="codigoEquipo")
    @JsonIgnore
    private Equipos equipos;


    public Posiciones(Equipos equipos) {

        this.codigoEquipo = equipos.getCodigoEquipo();
        this.empates = 0;
        this.ganados = 0;
        this.perdidos = 0;
        this.golesFavor = 0;
        this.golesContra = 0;
        this.puntos = 0;
        this.equipos = equipos;
    }

    
}
