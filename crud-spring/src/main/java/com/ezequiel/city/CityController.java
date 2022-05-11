package com.ezequiel.city;

import com.ezequiel.course.Course;
import com.ezequiel.course.CourseController;
import com.ezequiel.course.CourseRepository;
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
@RequestMapping("/api/cities")
@AllArgsConstructor
@Api(value = "API REST-FULL")
@Transactional
public class CityController {
    @Autowired
    CityRepository cityRepository;

    @ApiOperation(value = "Returns a list of cities")
    @GetMapping
    public ResponseEntity<List<City>> listAll() {
        List<City> cityList = cityRepository.findAll();
        if (cityList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            for (City city : cityList) {
                long id = city.getId();
                city.add(linkTo(methodOn(CityController.class).singleCity(id)).withSelfRel());
            }
            return new ResponseEntity<List<City>>(cityList, HttpStatus.OK);
        }
    }

    @ApiOperation(value = "Return a single city")
    @GetMapping("/{id}")
    public ResponseEntity<City> singleCity(@PathVariable(value = "id") long id) {
        Optional<City> cityO = Optional.ofNullable(cityRepository.findById(id));
        if (!cityO.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            cityO.get().add(linkTo(methodOn(CityController.class).listAll()).withRel("List of cities"));
            return new ResponseEntity<City>(cityO.get(), HttpStatus.OK);
        }
    }

    @ApiOperation(value = "Save a city")
    @PostMapping
    public ResponseEntity<City> saveCity(@RequestBody @Validated City city) {
        return new ResponseEntity<City>(cityRepository.save(city), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Delete a city")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCity(@PathVariable(value = "id") long id){
        Optional<City> cityO = Optional.ofNullable(cityRepository.findById(id));
        if (!cityO.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else {
            cityRepository.delete(cityO.get());
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @ApiOperation(value = "Update a course")
    @PutMapping("/{id}")
    public ResponseEntity<City> updateCity(@PathVariable(value="id") long id,
                                               @RequestBody @Validated City city) {
        Optional<City> cityO = Optional.ofNullable(cityRepository.findById(id));
        if(!cityO.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        city.setId(cityO.get().getId());
        return new ResponseEntity<City>(cityRepository.save(city), HttpStatus.OK);
    }
}
