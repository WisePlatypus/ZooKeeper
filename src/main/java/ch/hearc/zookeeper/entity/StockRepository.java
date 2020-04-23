package ch.hearc.zookeeper.entity;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository <Stock, Long> {
}