package com.ezequiel.product;


import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Pageable;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/product")
@AllArgsConstructor
@Api(value = "API REST-FULL")
@Transactional
public class ProductController {
    @Autowired
    private ProductRepository repository;

    @RequestMapping(method = RequestMethod.GET, produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> list(Pageable pageable){
        return new ResponseEntity<>(repository.findAll(pageable), HttpStatus.OK);
    }

    @RequestMapping(value= "/all", method= RequestMethod.GET, produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> listAll(){
        return new ResponseEntity<>(repository.findAll(), HttpStatus.OK);
    }

}
