package com.company.controller;

import com.company.model.ProductItem;
import com.company.service.ProductItemService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

  private ProductItemService productService;

  @GetMapping
  public List<ProductItem> findAll() {
    return productService.findAll();
  }

}
