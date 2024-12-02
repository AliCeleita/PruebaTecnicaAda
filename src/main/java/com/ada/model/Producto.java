package com.ada.model;

import com.ada.dto.ProductoDto;
import com.querydsl.core.annotations.QueryEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@QueryEntity
@Entity
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String nombre;

    @NotNull
    private String descripcion;

    @NotNull
    private BigDecimal precio;

    @NotNull
    private Long stock;

    public Producto() {
    }

    public Producto(ProductoDto productoDto) {
        this(productoDto.getId(), productoDto.getNombre(),
                productoDto.getDescripcion(), productoDto.getPrecio(), productoDto.getStock());
    }
}
