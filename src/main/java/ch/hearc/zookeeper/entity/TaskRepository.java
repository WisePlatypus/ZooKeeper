package ch.hearc.zookeeper.entity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository <Task, Long> {

}