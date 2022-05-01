package com.ezequiel.controller;

import java.util.List;

import com.ezequiel.model.Course;
import com.ezequiel.repository.CourseRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import lombok.AllArgsConstructor;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/courses")
@AllArgsConstructor
@Api(value="API REST Cursos")
public class CourseController {

    private final CourseRepository courseRepository;

    @ApiOperation(value="Retorna uma lista de Cursos")  //@RequestMapping(method = RequestMethod.GET)
    @GetMapping
    public ResponseEntity<List<Course>> list (){
        List<Course> courseList = courseRepository.findAll();
        if(courseList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else {
            for(Course curso : courseList) {
                long id = curso.getId();
                curso.add(linkTo(methodOn(CourseController.class).listaProdutoUnico(id)).withSelfRel());
            }
            return new ResponseEntity<List<Course>>(courseList, HttpStatus.OK);
        }
    }

    @ApiOperation(value="Retorna um curso unico")
    @GetMapping("/{id}")
    public Course listaProdutoUnico(@PathVariable(value="id") long id){
        return courseRepository.findById(id);
    }

    @ApiOperation(value="Salva um curso")
    @PostMapping
    public Course salvaProduto(@RequestBody @Validated Course curso) {
        return courseRepository.save(curso);
    }

    @ApiOperation(value="Deleta um curso")
    @DeleteMapping
    public void deletaProduto(@RequestBody @Validated Course curso) {
        courseRepository.delete(curso);
    }

    @ApiOperation(value="Atualiza um curso")
    @PutMapping
    public Course atualizaProduto(@RequestBody @Validated Course curso) {
        return courseRepository.save(curso);
    }

}
