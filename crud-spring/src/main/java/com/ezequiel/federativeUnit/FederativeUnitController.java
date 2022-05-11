package com.ezequiel.federativeUnit;

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
@RequestMapping("/api/federativeUnit")
@AllArgsConstructor
@Api(value = "API REST-FULL")
@Transactional
public class FederativeUnitController {
    @Autowired
    FederativeUnitRepository federativeUnitRepository;

    @ApiOperation(value = "Returns a list of federatives Units")
    @GetMapping
    public ResponseEntity<List<FederativeUnit>> listAll() {
        List<FederativeUnit> federativeUnitList = federativeUnitRepository.findAll();
        if (federativeUnitList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            for (FederativeUnit federativeUnit : federativeUnitList) {
                long id = federativeUnit.getId();
                federativeUnit.add(linkTo(methodOn(FederativeUnitController.class).singleFederativeUnit(id)).withSelfRel());
            }
            return new ResponseEntity<List<FederativeUnit>>(federativeUnitList, HttpStatus.OK);
        }
    }

    @ApiOperation(value = "Return a single federative Unit")
    @GetMapping("/{id}")
    public ResponseEntity<FederativeUnit> singleFederativeUnit(@PathVariable(value = "id") long id) {
        Optional<FederativeUnit> federativeUnitO = Optional.ofNullable(federativeUnitRepository.findById(id));
        if (!federativeUnitO.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            federativeUnitO.get().add(linkTo(methodOn(FederativeUnitController.class).listAll()).withRel("List of cities"));
            return new ResponseEntity<FederativeUnit>(federativeUnitO.get(), HttpStatus.OK);
        }
    }

    @ApiOperation(value = "Save a federative Unit")
    @PostMapping
    public ResponseEntity<FederativeUnit> saveFederativeUnit(@RequestBody @Validated FederativeUnit federativeUnit) {
        return new ResponseEntity<FederativeUnit>(federativeUnitRepository.save(federativeUnit), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Delete a federative Unit")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFederativeUnit(@PathVariable(value = "id") long id){
        Optional<FederativeUnit> federativeUnitO = Optional.ofNullable(federativeUnitRepository.findById(id));
        if (!federativeUnitO.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else {
            federativeUnitRepository.delete(federativeUnitO.get());
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @ApiOperation(value = "Update a federative Unit")
    @PutMapping("/{id}")
    public ResponseEntity<FederativeUnit> updateFederativeUnit(@PathVariable(value="id") long id,
                                                     @RequestBody @Validated FederativeUnit federativeUnit) {
        Optional<FederativeUnit> federativeUnitO = Optional.ofNullable(federativeUnitRepository.findById(id));
        if(!federativeUnitO.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        federativeUnit.setId(federativeUnitO.get().getId());
        return new ResponseEntity<FederativeUnit>(federativeUnitRepository.save(federativeUnit), HttpStatus.OK);
    }
}
