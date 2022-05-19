package com.ezequiel.modelo;

import com.ezequiel.marca.Marca;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "modelo")
public class Modelo {
    @Id
    @GeneratedValue
    private Long id;

    private String descricao;

    private Integer potencia;

    @ManyToOne
    private Marca marca;
}
