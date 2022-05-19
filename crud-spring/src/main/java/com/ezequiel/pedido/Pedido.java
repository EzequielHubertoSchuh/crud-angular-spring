package com.ezequiel.pedido;

import com.ezequiel.produto.Produto;
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
@Table(name = "pedido")
public class Pedido {
    @Id
    @GeneratedValue
    private Integer id;

    //@Column(name = "produto")
    @ManyToOne
    private Produto produto;

    @Column(name = "quantidade")
    private Integer quantidade;

    @Column(name = "pedidoPopular")
    private boolean pedidoPopular;
}
