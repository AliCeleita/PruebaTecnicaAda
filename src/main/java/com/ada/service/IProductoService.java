package com.ada.service;

import com.ada.dto.ProductoDto;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public interface IProductoService {

    void createProducto(ProductoDto dto);

    void updateProducto(ProductoDto dto, Long id);

    void deleteProducto(Long id);

    Page<ProductoDto> getProductos(Predicate predicate, Pageable pageable);
}
