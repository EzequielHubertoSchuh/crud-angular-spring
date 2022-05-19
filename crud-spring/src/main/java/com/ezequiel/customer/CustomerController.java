package com.ezequiel.customer;

import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/customer")
@AllArgsConstructor
@Api(value = "API REST-FULL")
@Transactional
public class CustomerController {

    @Autowired
    CustomerService service;

    @GetMapping("/search")
    public Page<Customer> search(
            @RequestParam("searchTerm") String searchTerm,
            @RequestParam(
                    value = "page",
                    required = false,
                    defaultValue = "0") int page,
            @RequestParam(
                    value = "size",
                    required = false,
                    defaultValue = "10") int size) {
        return service.search(searchTerm, page, size);

    }

    @GetMapping
    public Page<Customer> getAll() {
        return service.findAll();
    }

}
