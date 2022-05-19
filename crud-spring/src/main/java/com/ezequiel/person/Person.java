package com.ezequiel.person;

import com.sun.istack.NotNull;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;


@Data
@Entity
@Table(name = "person")
public class Person extends RepresentationModel<Person> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    @Column(name = "name", length = 200, nullable = false)
    private String name;

    @NotNull
    @Column(name = "cpf")
    private String cpf;

    @NotNull
    @Column(name = "dataNascimento")
    private LocalDateTime dataNascimento;

    @NotNull
    @Column(name = "sexo")
    private Sexo sexo;


}

