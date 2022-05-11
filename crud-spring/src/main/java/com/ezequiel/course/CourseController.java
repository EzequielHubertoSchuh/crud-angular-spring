package com.ezequiel.course;

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
@Api(value = "API REST-FULL")
@Transactional
public class CourseController {

    @Autowired
    CourseRepository courseRepository;

    @ApiOperation(value = "Returns a list of courses")
    @GetMapping
    public ResponseEntity<List<Course>> listAll() {
        List<Course> courseList = courseRepository.findAll();
        if (courseList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            for (Course course : courseList) {
                long id = course.getId();
                course.add(linkTo(methodOn(CourseController.class).singleCourse(id)).withSelfRel());
            }
            return new ResponseEntity<List<Course>>(courseList, HttpStatus.OK);
        }
    }

    @ApiOperation(value = "Return a single course")
    @GetMapping("/{id}")
    public ResponseEntity<Course> singleCourse(@PathVariable(value = "id") long id) {
        Optional<Course> courseO = Optional.ofNullable(courseRepository.findById(id));
        if (!courseO.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            courseO.get().add(linkTo(methodOn(CourseController.class).listAll()).withRel("List of courses"));
            return new ResponseEntity<Course>(courseO.get(), HttpStatus.OK);
        }
    }

    @ApiOperation(value = "Save a course")
    @PostMapping
    public ResponseEntity<Course> saveCourse(@RequestBody @Validated Course course) {
        return new ResponseEntity<Course>(courseRepository.save(course), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Delete a course")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCourse(@PathVariable(value = "id") long id){
        Optional<Course> courseO = Optional.ofNullable(courseRepository.findById(id));
        if (!courseO.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else {
            courseRepository.delete(courseO.get());
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @ApiOperation(value = "Update a course")
    @PutMapping("/{id}")
    public ResponseEntity<Course> updateCourse(@PathVariable(value="id") long id,
                                                      @RequestBody @Validated Course course) {
        Optional<Course> courseO = Optional.ofNullable(courseRepository.findById(id));
        if(!courseO.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        course.setId(courseO.get().getId());
        return new ResponseEntity<Course>(courseRepository.save(course), HttpStatus.OK);
    }

}
