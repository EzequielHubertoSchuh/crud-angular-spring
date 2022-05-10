package com.ezequiel.person;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/person")
@AllArgsConstructor
@Api(value = "API REST-FULL")
@Transactional
public class PersonController {

    @Autowired
    PersonRepository personRepository;

    @ApiOperation(value = "Returns a list of person")
    @GetMapping
    public ResponseEntity<List<Person>> listAll() {
        List<Person> personList = personRepository.findAll();
        if (personList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            for (Person person : personList) {
                long id = person.getId();
                person.add(linkTo(methodOn(PersonController.class).singlePerson(id)).withSelfRel());
            }
            return new ResponseEntity<List<Person>>(personList, HttpStatus.OK);
        }
    }

    @ApiOperation(value = "Returns a single person")
    @GetMapping("/{id}")
    public ResponseEntity<Person> singlePerson(@PathVariable(value = "id") long id) {
        Optional<Person> personO = Optional.ofNullable(personRepository.findById(id));
        if (!personO.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            personO.get().add(linkTo(methodOn(PersonController.class).listAll()).withRel("List of person"));
            return new ResponseEntity<Person>(personO.get(), HttpStatus.OK);
        }
    }

    @ApiOperation(value = "Save a person")
    @PostMapping
    public ResponseEntity<Person> savePerson(@RequestBody @Validated Person person) {
        return new ResponseEntity<Person>(personRepository.save(person), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Delete a person")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePerson(@PathVariable(value = "id") long id){
        Optional<Person> personO = Optional.ofNullable(personRepository.findById(id));
        if (!personO.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else {
            personRepository.delete(personO.get());
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @ApiOperation(value = "Update a person")
    @PutMapping("/{id}")
    public ResponseEntity<Person> updatePerson(@PathVariable(value="id") long id,
                                               @RequestBody @Validated Person person) {
        Optional<Person> personO = Optional.ofNullable(personRepository.findById(id));
        if(!personO.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        person.setId(personO.get().getId());
        return new ResponseEntity<Person>(personRepository.save(person), HttpStatus.OK);
    }

}
