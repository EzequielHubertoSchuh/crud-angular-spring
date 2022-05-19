package com.ezequiel.customer;

import com.ezequiel.district.District;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "customer")
@Entity
public class Customer extends RepresentationModel<Customer> {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "name", length = 200, nullable = false)
    private String name;

    @NotNull
    @Column(name = "email", length = 200, nullable = false)
    private String email;

}
