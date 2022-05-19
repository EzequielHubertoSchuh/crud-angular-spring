package com.ezequiel.automovel;

import com.ezequiel.city.CityController;
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
@RequestMapping("/api/automoveis")
@AllArgsConstructor
@Api(value = "API REST-FULL")
@Transactional
public class AutomovelController {

    @Autowired
    AutomovelRepository automovelRepository;

    @ApiOperation(value = "Returns a list of automoveis")
    @GetMapping
    public ResponseEntity<List<Automovel>> listAll() {
        List<Automovel> automovelList = automovelRepository.findAll();
        if (automovelList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            for (Automovel automovel : automovelList) {
                long id = automovel.getId();
                automovel.add(linkTo(methodOn(AutomovelController.class).singleAutomovel(id)).withSelfRel());
            }
            return new ResponseEntity<List<Automovel>>(automovelList, HttpStatus.OK);
        }
    }

    @ApiOperation(value = "Return a single automovel")
    @GetMapping("/{id}")
    public ResponseEntity<Automovel> singleAutomovel(@PathVariable(value = "id") long id) {
        Optional<Automovel> automovelO = Optional.ofNullable(automovelRepository.findById(id));
        if (!automovelO.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            automovelO.get().add(linkTo(methodOn(AutomovelController.class).listAll()).withRel("List of automoveis"));
            return new ResponseEntity<Automovel>(automovelO.get(), HttpStatus.OK);
        }
    }

    @ApiOperation(value = "Save a automovel")
    @PostMapping
    public ResponseEntity<Automovel> saveAutomovel(@RequestBody @Validated Automovel automovel) {
        return new ResponseEntity<Automovel>(automovelRepository.save(automovel), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Delete a automovel")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAutomovel(@PathVariable(value = "id") long id){
        Optional<Automovel> automovelO = Optional.ofNullable(automovelRepository.findById(id));
        if (!automovelO.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else {
            automovelRepository.delete(automovelO.get());
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @ApiOperation(value = "Update a automovel")
    @PutMapping("/{id}")
    public ResponseEntity<Automovel> updateAutomovel(@PathVariable(value="id") long id,
                                           @RequestBody @Validated Automovel automovel) {
        Optional<Automovel> automovelO = Optional.ofNullable(automovelRepository.findById(id));
        if(!automovelO.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        automovel.setId(automovelO.get().getId());
        return new ResponseEntity<Automovel>(automovelRepository.save(automovel), HttpStatus.OK);
    }
}
