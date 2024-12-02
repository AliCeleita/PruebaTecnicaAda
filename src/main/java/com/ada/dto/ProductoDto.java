package com.ada.dto;

import com.ada.model.Producto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class ProductoDto {

    private Long id;

    private String nombre;

    private String descripcion;

    private BigDecimal precio;

    private Long stock;

    public ProductoDto() {}

    public ProductoDto(Producto producto) {
        this(producto.getId(), producto.getNombre(),
                producto.getDescripcion(), producto.getPrecio(), producto.getStock());
    }
}
