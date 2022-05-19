package com.ezequiel.produto;

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
@Table(name = "produto")
public class Produto {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(unique=true,name = "codigo")
    private String codigo;

    @Column(name = "valor")
    private Float valor;
}
