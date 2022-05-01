package com.ezequiel.controller;

import com.ezequiel.model.Course;
import com.ezequiel.repository.CourseRepository;
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
@RequestMapping("/api/courses")
@AllArgsConstructor
@Api(value = "API REST Cursos")
public class CourseController {

    @Autowired
    CourseRepository courseRepository;

    @ApiOperation(value = "Retorna uma lista de Cursos")
    @GetMapping
    public ResponseEntity<List<Course>> list() {
        List<Course> courseList = courseRepository.findAll();
        if (courseList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            for (Course course : courseList) {
                long id = course.getId();
                course.add(linkTo(methodOn(CourseController.class).listaCursoUnico(id)).withSelfRel());
            }
            return new ResponseEntity<List<Course>>(courseList, HttpStatus.OK);
        }
    }

    @ApiOperation(value = "Retorna um curso unico")
    @GetMapping("/{id}")
    public ResponseEntity<Course> listaCursoUnico(@PathVariable(value = "id") long id) {
        Optional<Course> courseO = Optional.ofNullable(courseRepository.findById(id));
        if (!courseO.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            courseO.get().add(linkTo(methodOn(CourseController.class).list()).withRel("Lista de cursos"));
            return new ResponseEntity<Course>(courseO.get(), HttpStatus.OK);
        }
    }

    @ApiOperation(value = "Salva um curso")
    @Transactional
    @PostMapping
    public ResponseEntity<Course> salvaCurso(@RequestBody @Validated Course course) {
        return new ResponseEntity<Course>(courseRepository.save(course), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Deleta um curso")
    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletaCurso(@PathVariable(value = "id") long id){
        Optional<Course> courseO = Optional.ofNullable(courseRepository.findById(id));
        if (!courseO.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else {
            courseRepository.delete(courseO.get());
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @ApiOperation(value = "Atualiza um curso")
    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<Course> updateProduto(@PathVariable(value="id") long id,
                                                      @RequestBody @Validated Course course) {
        Optional<Course> courseO = Optional.ofNullable(courseRepository.findById(id));
        if(!courseO.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        course.setId(courseO.get().getId());
        return new ResponseEntity<Course>(courseRepository.save(course), HttpStatus.OK);
    }

}
