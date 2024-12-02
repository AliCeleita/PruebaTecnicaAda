package com.ada.controller;

import com.ada.dto.ProductoDto;
import com.ada.model.Producto;
import com.ada.service.IProductoService;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/productos")
public class ProductoController {

    private final IProductoService productoService;

    @GetMapping()
    private ResponseEntity<Page<ProductoDto>> getAllProductos(@QuerydslPredicate(root = Producto.class) Predicate predicate,
                                                          Pageable pageable){
        Page<ProductoDto> productos = productoService.getProductos(predicate, pageable);
        return new ResponseEntity<>(productos, HttpStatus.OK);
    }

    @PostMapping()
    private ResponseEntity<ProductoDto> createProducto(@RequestBody ProductoDto productoDto){
        productoService.createProducto(productoDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    private ResponseEntity<ProductoDto> updateProducto(@RequestBody ProductoDto productoDto, @PathVariable Long id){
        productoService.updateProducto(productoDto, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<ProductoDto> deleteProducto(@PathVariable Long id){
        productoService.deleteProducto(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
