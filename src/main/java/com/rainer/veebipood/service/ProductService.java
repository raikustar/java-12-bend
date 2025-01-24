package com.rainer.veebipood.service;

import com.rainer.veebipood.dto.ProductNutrientsumDTO;
import com.rainer.veebipood.entity.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
    public List<ProductNutrientsumDTO> calculateNutrients(List<Product> products) {
        List<ProductNutrientsumDTO> list = new ArrayList<>();
        for (Product product : products) {
            ProductNutrientsumDTO prod = new ProductNutrientsumDTO();
            prod.setName(product.getName());

            if (product.getNutrients() != null) {
                int sum = 0;
                sum += product.getNutrients().getProteins();
                sum += product.getNutrients().getCarbohydrates();
                sum += product.getNutrients().getFats();
                prod.setSum(sum);
            } else {
                prod.setSum(0);
            }
            list.add(prod);

        }
        return list;
    }
}
