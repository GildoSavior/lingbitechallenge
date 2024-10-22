package com.linqbitechallenge.taskManager.domain.repository;

import com.linqbitechallenge.taskManager.domain.enums.StatusTask;
import com.linqbitechallenge.taskManager.domain.models.Task;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public interface ITaskRepository {

    Task save(Task task);

     List<Task> getAll();

     Task getById(UUID id);

     Task update(UUID id, Task taskUpdated);

    void delete(UUID id) ;

     List<Task> getByStatus(StatusTask statusTask);
}
