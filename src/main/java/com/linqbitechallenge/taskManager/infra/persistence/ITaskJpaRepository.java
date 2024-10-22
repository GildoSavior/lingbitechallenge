package com.linqbitechallenge.taskManager.infra.persistence;

import com.linqbitechallenge.taskManager.domain.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ITaskJpaRepository extends JpaRepository<Task, UUID> {
}
