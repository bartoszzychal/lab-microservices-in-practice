package com.capgemini.ms.orderservice.controller;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.ms.orderservice.clients.CatalogClientHystrix;
import com.capgemini.ms.orderservice.clients.Customer;
import com.capgemini.ms.orderservice.clients.CustomerClientHystrix;
import com.capgemini.ms.orderservice.clients.Item;

@RestController
public class OrderController {

    @Autowired
    private CustomerClientHystrix customerClient;

    @Autowired
    private CatalogClientHystrix catalogClient;

    @RequestMapping(path = "/customers", method = RequestMethod.GET)
    public ResponseEntity<List<Customer>> getCustomers() {
        return Optional.ofNullable(customerClient.getAllCustomers())
                .map(c -> new ResponseEntity<>(c, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @RequestMapping(path = "/customers/{id}", method = RequestMethod.GET)
    public ResponseEntity<Customer> getCustomer(@PathVariable long id) {
        return Optional.ofNullable(customerClient.getCustomer(id))
            .map(c -> new ResponseEntity<>(c, HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @RequestMapping(path = "/items", method = RequestMethod.GET)
    public ResponseEntity<List<Item>> getItems() {
        return Optional.ofNullable(catalogClient.getAllItems())
                .map(c -> new ResponseEntity<>(c, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @RequestMapping(path = "/items/{id}", method = RequestMethod.GET)
    public ResponseEntity<Item> getItem(@PathVariable long id) {
        return Optional.ofNullable(catalogClient.getItem(id))
            .map(c -> new ResponseEntity<>(c, HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}
