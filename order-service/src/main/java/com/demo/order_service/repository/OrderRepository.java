package com.demo.order_service.repository;

import com.demo.order_service.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    public Optional<Order> findByOrderNumber(String orderNumber);
    public Optional<Order> findByOrderNumberAndIsCancelledFalseAndIsDeletedFalse(String orderNumber);
    public Optional<Order> findByIdAndIsCancelledFalseAndIsDeletedFalse(Long id);
    List<Order> findAllByIsCancelledFalseAndIsDeletedFalse();

}
