package com.rainer.veebipood.controller;

import com.rainer.veebipood.models.*;
import com.rainer.veebipood.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SupplierController {

    @Autowired
    private SupplierService supplierService;

    @GetMapping("supplier1")
    public List<Supplier1Product> getSupplier1Products() {
        return supplierService.getSupplier1Products();
    }

    @GetMapping("supplier2")
    public List<Supplier2Product> getSupplier2Products() {
        return supplierService.getSupplier2Products();
    }

    @GetMapping("supplier3")
    public List<Supplier3Product> getSupplier3Products() {
        return supplierService.getSupplier3Products();
    }

    @GetMapping("supplier4")
    public List<Supplier4Product> getSupplier4Products() {
        return supplierService.getSupplier4Products();
    }

    @GetMapping("supplier5")
    public List<Supplier5Feature> getSupplier5Products() {
        return supplierService.getSupplier5Products();
    }

}
