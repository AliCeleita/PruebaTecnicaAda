package com.ada.service.impl;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.ada.dto.ProductoDto;
import com.ada.exception.ResourceNotFoundException;
import com.ada.model.Producto;
import com.ada.repository.ProductoRepository;
import com.querydsl.core.types.Predicate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;

class ProductoServiceImplTest {

    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private ProductoServiceImpl productoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateProducto() {
        ProductoDto productoDto = new ProductoDto();
        productoDto.setNombre("Producto 1");
        productoDto.setDescripcion("Descripción");
        productoDto.setPrecio(BigDecimal.valueOf(100.0));
        productoDto.setStock(10L);

        productoService.createProducto(productoDto);

        verify(productoRepository, times(1)).save(any(Producto.class));
    }

    @Test
    void testUpdateProducto_Success() {

        Long id = 1L;
        Producto existingProducto = new Producto();
        existingProducto.setId(id);
        existingProducto.setNombre("Original");
        existingProducto.setDescripcion("Original Descripción");
        existingProducto.setPrecio(BigDecimal.valueOf(50.0));
        existingProducto.setStock(5L);

        ProductoDto productoDto = new ProductoDto();
        productoDto.setNombre("Nuevo");
        productoDto.setDescripcion("Nueva Descripción");
        productoDto.setPrecio(BigDecimal.valueOf(100.0));
        productoDto.setStock(10L);

        when(productoRepository.findById(id)).thenReturn(Optional.of(existingProducto));

        productoService.updateProducto(productoDto, id);

        verify(productoRepository, times(1)).findById(id);
        verify(productoRepository, times(1)).save(any(Producto.class));
        assertEquals("Nuevo", existingProducto.getNombre());
        assertEquals("Nueva Descripción", existingProducto.getDescripcion());
        assertEquals(BigDecimal.valueOf(100.0), existingProducto.getPrecio());
        assertEquals(10L, existingProducto.getStock());
    }

    @Test
    void testUpdateProducto_ResourceNotFound() {
        Long id = 1L;
        ProductoDto productoDto = new ProductoDto();

        when(productoRepository.findById(id)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> productoService.updateProducto(productoDto, id)
        );
        assertEquals("Este producto no existe", exception.getMessage());
        verify(productoRepository, times(1)).findById(id);
        verify(productoRepository, never()).save(any());
    }

    @Test
    void testDeleteProducto_Success() {

        Long id = 1L;
        Producto producto = new Producto();
        producto.setId(id);

        when(productoRepository.findById(id)).thenReturn(Optional.of(producto));

        productoService.deleteProducto(id);

        verify(productoRepository, times(1)).findById(id);
        verify(productoRepository, times(1)).delete(producto);
    }

    @Test
    void testDeleteProducto_ResourceNotFound() {
        Long id = 1L;

        when(productoRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> productoService.deleteProducto(id)
        );
        assertEquals("Este producto no existe", exception.getMessage());
        verify(productoRepository, times(1)).findById(id);
        verify(productoRepository, never()).delete(any());
    }

    @Test
    void testGetProductos() {
        Predicate predicate = mock(Predicate.class);
        Pageable pageable = mock(Pageable.class);
        Producto producto = new Producto();
        producto.setId(1L);
        producto.setNombre("Producto 1");

        Page<Producto> productos = new PageImpl<>(Arrays.asList(producto));
        when(productoRepository.findAll(predicate, pageable)).thenReturn(productos);

        Page<ProductoDto> result = productoService.getProductos(predicate, pageable);

        verify(productoRepository, times(1)).findAll(predicate, pageable);
        assertEquals(1, result.getTotalElements());
        assertEquals("Producto 1", result.getContent().get(0).getNombre());
    }
}
