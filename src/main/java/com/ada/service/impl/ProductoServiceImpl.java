package com.ada.service.impl;

import com.ada.dto.ProductoDto;
import com.ada.error.Errors;
import com.ada.exception.ResourceNotFoundException;
import com.ada.model.Producto;
import com.ada.repository.ProductoRepository;
import com.ada.service.IProductoService;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * <p>
 * Esta clase proporciona métodos para crear, actualizar, eliminar
 * y consultar productos. Utiliza {@link ProductoRepository} para
 * interactuar con la base de datos.
 * </p>
 */
@Service
public class ProductoServiceImpl implements IProductoService {

    /**
     * Repositorio para interactuar con la base de datos de productos.
     */
    private ProductoRepository productoRepository;

    /**
     * Constructor para inicializar el servicio de productos con un repositorio.
     *
     * @param productoRepository Repositorio de productos.
     */
    public ProductoServiceImpl(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    /**
     * Metodo para crear un producto
     * @param dto
     */
    @Override
    public void createProducto(ProductoDto dto) {
        productoRepository.save(new Producto(dto));
    }

    /**
     * Metodo para actualizar un producto existente
     * @param dto
     * @param id
     */
    @Override
    public void updateProducto(ProductoDto dto, Long id) {
        Producto producto = productoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(Errors.NO_EXISTE.getMessage()));

        if(!Objects.equals(dto.getNombre(), producto.getNombre()))
            producto.setNombre(dto.getNombre());

        if(!Objects.equals(dto.getDescripcion(), producto.getDescripcion()))
            producto.setDescripcion(dto.getDescripcion());

        if(!Objects.equals(dto.getPrecio(), producto.getPrecio()))
            producto.setPrecio(dto.getPrecio());

        if(!Objects.equals(dto.getStock(), producto.getStock()))
            producto.setStock(dto.getStock());

        productoRepository.save(producto);
    }

    /**
     * Metodo para eliminar un producto existente
     * @param id
     */
    @Override
    public void deleteProducto(Long id) {
        Producto producto = productoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(Errors.NO_EXISTE.getMessage()));
        productoRepository.delete(producto);
    }

    /**
     * Metodo para obtener todos los productos de la base de datos
     * @param predicate
     * @param pageable
     * @return
     */
    @Override
    public Page<ProductoDto> getProductos(Predicate predicate, Pageable pageable) {
        return productoRepository.findAll(predicate, pageable).map(ProductoDto::new);
    }

}
