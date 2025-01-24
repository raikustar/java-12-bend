package com.rainer.veebipood.controller;

import com.rainer.veebipood.dto.ProductNutrientsumDTO;
import com.rainer.veebipood.entity.Category;
import com.rainer.veebipood.entity.Product;
import com.rainer.veebipood.repository.CategoryRepository;
import com.rainer.veebipood.repository.ProductRepository;
import com.rainer.veebipood.service.ProductService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(origins = "http://localhost:3000")
@RestController
@Log4j2
public class ProductController {

    @Autowired
    ProductService productService;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("products/{productName}")
    public Product getPublicProduct(@PathVariable String productName) {
        return productRepository.findById(productName).orElseThrow();
    }

    // ?page=0&size=2
    @GetMapping("public-products/{categoryId}")
    public Page<Product> availableProducts(@PathVariable Long categoryId, Pageable pageable) {
        if (categoryId == -1) {
            return productRepository.findByActiveTrueOrderByNameAsc(pageable);
        }
        return productRepository.findByCategory_IdAndActiveTrueOrderByNameAsc(categoryId, pageable);
    }

//
//    @GetMapping("products-by-category/{categoryId}")
//    public Page<Product> getProductsByCategory(@PathVariable Long categoryId, Pageable pageable) {
//        return productRepository.findByCategory_IdAndActiveTrueOrderByNameAsc(categoryId, pageable);
//    }


    //List<Product> tooted = new ArrayList<>(Arrays.asList(
    //        new Product("Coca", 1.1, true, 150),
    //        new Product("Fanta", 1.0, true, 100),
    //       new Product("Sprite", 1.2, false, 0)
    //));

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("products")
    public List<Product> getProducts() {
        return productRepository.findByOrderByNameAsc();
    }

    // default values db: number = 0, bool = false, string = null, Object(key) = null

    // 4xx error - frontend error:
    // 400 - üldine viga
    // 401, 403 - auth error
    // 404 - url valesti
    // 405 - vale method
    // 415 - valel kujul body

    // 5xx - backend error
    // exception


    // localhost:8080/add-product?newProduct=
    @PostMapping("products")
    public List<Product> addProducts(@RequestBody Product newProduct) {
        String email = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        if (newProduct.getName() == null || newProduct.getName().isEmpty()) {
            log.error("Wanted to add product but name was empty by user {} :{}",email, newProduct);
            throw new RuntimeException("Nimi on puudu");
        }

        if (newProduct.getPrice() < 0 ) {
            log.error("Wanted to add product but price was negative by user {} :{}",email, newProduct);
            throw new RuntimeException("Hind on negatiivne");
        }

        if (newProduct.getCategory() == null ||
            newProduct.getCategory().getId() == null ||
            newProduct.getCategory().getId() <= 0) {
            log.error("Wanted to add category but name was empty by user {} :{}",email, newProduct);

            throw new RuntimeException("Kategooria on puudu");

        }

        if (productRepository.findById(newProduct.getName()).isEmpty()) {
            newProduct.setActive(true);
            if (newProduct.getStock() < 0) {
                log.error("Wanted to add product with negative stock, changed it to 0 by user {} :{}",email, newProduct);

                newProduct.setStock(0);
            }
            productRepository.save(newProduct);
            log.info("Product saved by user {} :{}",email, newProduct);

        }
        return productRepository.findByOrderByNameAsc();
    }

    @DeleteMapping("products/{productName}")
    public List<Product> deleteProducts(@PathVariable String productName) {
        try {
            productRepository.deleteById(productName);
        } catch (Exception e) {
            throw new RuntimeException("Ei saa kustutada  toodet, mis on tellimuses");
        }
        return productRepository.findAll();

    }

    @PutMapping("products")
    public List<Product> editProducts(@RequestBody Product newProduct) {
        if (productRepository.findById(newProduct.getName()).isPresent()) {
            productRepository.save(newProduct);
        }
        return productRepository.findByOrderByNameAsc();
    }

    @PatchMapping("change-active")
    public List<Product> changeActive(@RequestParam String productName, @RequestParam boolean active) {

        Product product = productRepository.findById(productName).orElseThrow();
        product.setActive(active);
        productRepository.save(product);
        return productRepository.findByOrderByNameAsc();

    }


    // Patch - ühe kindla välja muutmine
    @PatchMapping("change-stock")
    public List<Product> changeStock(@RequestBody Product changedProduct) {
                                            // võtan andmebaasist primary key
        Product product = productRepository.findById(changedProduct.getName())
                .orElseThrow();
        product.setStock(changedProduct.getStock());
        productRepository.save(product);

        //Optional<Product> productOptional = productRepository.findById(changedProduct.getName());
        //Product product = productOptional.get();
        //product.setStock(product.getStock());
        //productRepository.save(product);

        return productRepository.findByOrderByNameAsc();
    }

    @PatchMapping("increase-stock")
    public List<Product> increaseStock(@RequestParam String name) {
        Product product = productRepository.findById(name).orElseThrow();
        product.setStock(product.getStock() + 1);
        productRepository.save(product);
        return productRepository.findByOrderByNameAsc();
    }

    @PatchMapping("decrease-stock")
    public List<Product> decreaseStock(@RequestParam String name) {
        Product product = productRepository.findById(name).orElseThrow();
        if (product.getStock() <= 0) {
            throw new RuntimeException("Ei saa vähendada kui kogus on 0");

        }
        product.setStock(product.getStock() - 1);
        productRepository.save(product);
        return productRepository.findByOrderByNameAsc();
    }

    @GetMapping("all-stock/{personId}")
    public int allStock() {
        List<Product> products = productRepository.findAll();
        int sum = 0;
        for (Product product : products) {
            sum += product.getStock();
        }
        return sum;
    }

    @GetMapping("products-proteins")
    public List<ProductNutrientsumDTO> productProteins() {
        List<Product> products = productRepository.findAll();
        return productService.calculateNutrients(products);
    }

    @PatchMapping("product-category")
    public List<Product> updateProductCategory(@RequestParam String productName, Long categoryId) {
        Product newProduct = productRepository.findById(productName).orElseThrow();
        Category category = categoryRepository.findById(categoryId).orElseThrow();

        newProduct.setCategory(category);
        productRepository.save(newProduct);
        return productRepository.findByOrderByNameAsc();
    }




}
