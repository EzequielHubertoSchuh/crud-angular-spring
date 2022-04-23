package com.ezequiel.controller;

import java.util.List;

import com.ezequiel.model.Course;
import com.ezequiel.repository.CourseRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import lombok.AllArgsConstructor;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/courses")
@AllArgsConstructor
@Api(value="API REST Forum")
public class CourseController {

    private final CourseRepository courseRepository;

    @ApiOperation(value="Retorna uma lista de Cursos")  //@RequestMapping(method = RequestMethod.GET)
    @GetMapping
    public @ResponseBody List<Course> list() {
        return courseRepository.findAll();
    }

}
