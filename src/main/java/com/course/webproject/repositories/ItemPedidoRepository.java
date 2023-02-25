package com.course.webproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.course.webproject.domain.ItemPedido;
import com.course.webproject.domain.ItemPedidoPK;

public interface ItemPedidoRepository extends JpaRepository<ItemPedido, ItemPedidoPK> {

}
