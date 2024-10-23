package com.linqbitechallenge.taskManager.domain.usecases;

import com.linqbitechallenge.taskManager.application.dto.Result;
import com.linqbitechallenge.taskManager.domain.models.Task;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface  ITaskUseCases {
    Result<Task> save(Task task);

    Result<List<Task>> getAll();

    Result<Task> getById(String id);

    Result<List<Task>> getByStatus(int statusTask);

    Result<Task> update(String strId, Task task);

    Result<String> delete(String id);
}
