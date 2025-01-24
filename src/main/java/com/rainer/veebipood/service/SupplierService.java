package com.rainer.veebipood.service;

import com.rainer.veebipood.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;
import java.util.List;

@Service
public class SupplierService {

    @Autowired
    RestTemplate restTemplate;

    //https://fakestoreapi.com/products
    public List<Supplier1Product> getSupplier1Products() {
//        RestTemplate restTemplate = new RestTemplate();
        String url = "https://fakestoreapi.com/products";
        ResponseEntity<Supplier1Product[]> response = restTemplate.exchange(url, HttpMethod.GET, null, Supplier1Product[].class);
        List<Supplier1Product> products = List.of(response.getBody());
        products = products.stream().filter(e -> e.rating.rate > 4).toList();
        return products;
    }

    public List<Supplier2Product> getSupplier2Products() {
//        RestTemplate restTemplate = new RestTemplate();
        String url = "https://api.escuelajs.co/api/v1/products";
        ResponseEntity<Supplier2Product[]> response = restTemplate.exchange(url, HttpMethod.GET, null, Supplier2Product[].class);
        List<Supplier2Product> products = List.of(response.getBody());
        products = products.stream().filter(e -> !e.creationAt.equals(e.category.creationAt)).toList();
        return products;
    }

    public List<Supplier3Product> getSupplier3Products() {
//        RestTemplate restTemplate = new RestTemplate();
        String url = "https://dummyjson.com/products";
        ResponseEntity<Supplier3Response> response = restTemplate.exchange(url, HttpMethod.GET, null, Supplier3Response.class);
        List<Supplier3Product> products = response.getBody().getProducts();
        products = products.stream().filter(e -> e.rating > 4).toList();
        return products;

    }

    public List<Supplier4Product> getSupplier4Products() {
//        RestTemplate restTemplate = new RestTemplate();
        String url = "https://public.opendatasoft.com/api/explore/v2.1/catalog/datasets/global-shark-attack/records";
        ResponseEntity<Supplier4Response> response = restTemplate.exchange(url, HttpMethod.GET,null, Supplier4Response.class);
        List<Supplier4Product> sharkAttacks = response.getBody().getResults();
        sharkAttacks = sharkAttacks.stream().filter(e -> {
            if (e.activity == null) { return false; }
            else return e.activity.toString().equals("Surfing");
        }).toList();

        return sharkAttacks;
    }

    public List<Supplier5Feature> getSupplier5Products() {
//        RestTemplate restTemplate = new RestTemplate();
        String url = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2023-01-01&endtime=2023-12-30&minmagnitude=5";
        ResponseEntity<Supplier5Response> response = restTemplate.exchange(url, HttpMethod.GET,null, Supplier5Response.class);
        List<Supplier5Feature> features = response.getBody().getFeatures();
        features = features.stream().filter(e -> e.properties.mag > 7).toList();

        return features;
    }


}
