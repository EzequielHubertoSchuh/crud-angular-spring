package com.ezequiel.federativeUnit;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "federativeUnit")
public class FederativeUnit extends RepresentationModel<FederativeUnit> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    @Column(name = "name", length = 200, nullable = false)
    private String name;

    @NotBlank
    @Column(name = "uf", length = 2, nullable = false)
    private String uf;
}
