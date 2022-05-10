package com.ezequiel.person;

import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import java.util.Date;


@Data
@Entity
@Table(name = "person")
public class Person extends RepresentationModel<Person> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 200, nullable = false)
    private String name;

    private String cpf;

    private Date dataNascimento;

    private Sexo sexo;
}

