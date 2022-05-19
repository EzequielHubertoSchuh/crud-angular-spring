package com.ezequiel.city;

import com.ezequiel.federativeUnit.FederativeUnit;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "city")
public class City extends RepresentationModel<City> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "name", length = 200, nullable = false)
    private String name;

    @NotNull
    @ManyToOne
    private FederativeUnit federativeUnit;
}
