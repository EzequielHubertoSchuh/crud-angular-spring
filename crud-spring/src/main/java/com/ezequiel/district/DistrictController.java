package com.ezequiel.district;

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
@RequestMapping("/api/district")
@AllArgsConstructor
@Api(value = "API REST-FULL")
@Transactional
public class DistrictController {
    @Autowired
    DistrictRepository districtRepository;

    @ApiOperation(value = "Returns a list of districts")
    @GetMapping
    public ResponseEntity<List<District>> listAll() {
        List<District> districtList = districtRepository.findAll();
        if (districtList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            for (District district : districtList) {
                long id = district.getId();
                district.add(linkTo(methodOn(DistrictController.class).singleDistrict(id)).withSelfRel());
            }
            return new ResponseEntity<List<District>>(districtList, HttpStatus.OK);
        }
    }

    @ApiOperation(value = "Return a single district")
    @GetMapping("/{id}")
    public ResponseEntity<District> singleDistrict(@PathVariable(value = "id") long id) {
        Optional<District> districtO = Optional.ofNullable(districtRepository.findById(id));
        if (!districtO.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            districtO.get().add(linkTo(methodOn(DistrictController.class).listAll()).withRel("List of districts"));
            return new ResponseEntity<District>(districtO.get(), HttpStatus.OK);
        }
    }

    @ApiOperation(value = "Save a district")
    @PostMapping
    public ResponseEntity<District> saveDistrict(@RequestBody @Validated District district) {
        return new ResponseEntity<District>(districtRepository.save(district), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Delete a district")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDistrict(@PathVariable(value = "id") long id){
        Optional<District> districtO = Optional.ofNullable(districtRepository.findById(id));
        if (!districtO.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else {
            districtRepository.delete(districtO.get());
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @ApiOperation(value = "Update a district")
    @PutMapping("/{id}")
    public ResponseEntity<District> updateDistrict(@PathVariable(value="id") long id,
                                               @RequestBody @Validated District district) {
        Optional<District> districtO = Optional.ofNullable(districtRepository.findById(id));
        if(!districtO.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        district.setId(districtO.get().getId());
        return new ResponseEntity<District>(districtRepository.save(district), HttpStatus.OK);
    }
}
