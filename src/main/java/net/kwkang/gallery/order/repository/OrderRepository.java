package net.kwkang.gallery.order.repository;

import net.kwkang.gallery.order.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Integer> {


    Page<Order> findAllByMemberIdOrderByIdDesc(Integer memberId, Pageable pageable);

    Optional<Order> findByIdAndMemberId(Integer id, Integer memberId);

}
