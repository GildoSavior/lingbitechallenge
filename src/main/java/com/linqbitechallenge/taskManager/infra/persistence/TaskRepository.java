package com.linqbitechallenge.taskManager.infra.persistence;

import com.linqbitechallenge.taskManager.domain.enums.StatusTask;
import com.linqbitechallenge.taskManager.domain.repository.ITaskRepository;
import com.linqbitechallenge.taskManager.domain.models.Task;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class TaskRepository implements ITaskRepository {

    private final ITaskJpaRepository jpaRepository;

    public TaskRepository(ITaskJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Task save(Task task) {
        Task entity = new Task(
                task.getTitulo(),
                task.getDescricao(),
                task.getDataCriacao(),
                task.getDataConclusao(),
                task.getStatusTask()
        );

        return jpaRepository.save(entity);
    }

    @Override
    public List<Task> getAll() {
        return  jpaRepository.findAll();
    }

    @Override
    public Task getById(UUID id) {
        return jpaRepository.findById(id).orElse(null);
    }

    @Override
    public List<Task> getByStatus(StatusTask statusTask) {
        List<Task> tasks = jpaRepository.findAll();

        return tasks.stream()
                .filter(task -> task.getStatusTask().getCode() == statusTask.getCode())
                .collect(Collectors.toList());
    }

    @Override
    public Task update(UUID id, Task taskUpdated) {
        Optional<Task> optionalTask = jpaRepository.findById(id);
        Task task = optionalTask.orElse(new Task());

        task.setTitulo(taskUpdated.getTitulo());
        task.setDescricao(taskUpdated.getDescricao());
        task.setDataCriacao(taskUpdated.getDataCriacao());
        task.setDataConclusao(taskUpdated.getDataConclusao());
        task.setStatusTask(taskUpdated.getStatusTask());
        return jpaRepository.save(task);
    }

    @Override
    public void delete(UUID id) {
        Task task = jpaRepository.findById(id).orElse(null);

        assert task != null;
        jpaRepository.delete(task);
    }



}
