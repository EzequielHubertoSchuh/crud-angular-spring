package com.ezequiel.automovel;

import com.ezequiel.facesmotors.validation.groups.ValidacaoMinima;
import com.ezequiel.modelo.Modelo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "automovel")
public class Automovel extends RepresentationModel<Automovel> {


    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne
    private Modelo modelo;

    @Min(1900)
    @MaxAnoAtualMais(message="O máximo do ano de fabricação é {0}")
    private Integer anoFabricacao;

    @Min(1900)
    @MaxAnoAtualMais(value=1, message="O máximo do ano do modelo é {0}")
    private Integer anoModelo;

    @NotNull(groups={ValidacaoMinima.class, Default.class})
    private BigDecimal preco;

    private Double kilometragem;

    @NotNull
    private String observacoes;
}
