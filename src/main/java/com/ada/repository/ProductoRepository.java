package com.ada.repository;

import com.ada.model.Producto;
import com.ada.model.QProducto;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long>, QuerydslPredicateExecutor<Producto>, QuerydslBinderCustomizer<QProducto> {

    @Override
    default void customize(QuerydslBindings bindings, QProducto root) {
        bindings.bind(String.class).first(
                (StringPath path, String value) -> path.containsIgnoreCase(value));
        bindings.excluding(root.id);
    }

}
