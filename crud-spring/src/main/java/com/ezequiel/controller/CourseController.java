package com.ezequiel.controller;

import java.util.List;

import com.ezequiel.model.Course;
import com.ezequiel.repository.CourseRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import lombok.AllArgsConstructor;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/courses")
@AllArgsConstructor
@Api(value="API REST Cursos")
public class CourseController {

    private final CourseRepository courseRepository;

    @ApiOperation(value="Retorna uma lista de Cursos")  //@RequestMapping(method = RequestMethod.GET)
    @GetMapping
    public @ResponseBody List<Course> list() {
        return courseRepository.findAll();
    }

    @ApiOperation(value="Retorna um curso unico")
    @GetMapping("/{id}")
    public Course listaProdutoUnico(@PathVariable(value="id") long id){
        return courseRepository.findById(id);
    }

    @ApiOperation(value="Salva um curso")
    @PostMapping
    public Course salvaProduto(@RequestBody @Validated Course produto) {
        return courseRepository.save(produto);
    }

    @ApiOperation(value="Deleta um curso")
    @DeleteMapping
    public void deletaProduto(@RequestBody @Validated Course produto) {
        courseRepository.delete(produto);
    }

    @ApiOperation(value="Atualiza um curso")
    @PutMapping
    public Course atualizaProduto(@RequestBody @Validated Course produto) {
        return courseRepository.save(produto);
    }

}
