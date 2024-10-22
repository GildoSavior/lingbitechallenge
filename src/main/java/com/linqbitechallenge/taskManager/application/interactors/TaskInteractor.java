package com.linqbitechallenge.taskManager.application.interactors;

import com.linqbitechallenge.taskManager.application.dto.Result;
import com.linqbitechallenge.taskManager.domain.enums.StatusTask;
import com.linqbitechallenge.taskManager.domain.repository.ITaskRepository;
import com.linqbitechallenge.taskManager.domain.models.Task;
import com.linqbitechallenge.taskManager.domain.usecases.ITaskUseCases;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TaskInteractor implements ITaskUseCases {

    private final ITaskRepository repository;

    public TaskInteractor(ITaskRepository repository) {
        this.repository = repository;
    }

    @Override
    public Result<Task> save(Task task) {
        try {

            if(task.getTitulo().isEmpty() || task.getDescricao().isEmpty()){
                return Result.failure("O titulo e descrição devem estar preenchidos");
            }

            if(task.getDataConclusao() != null && task.getDataConclusao().isBefore(task.getDataCriacao())){
                return Result.failure("A Data de Conclusão não pode ser inferior à Data de Crição");
            }

            if(!status_Task_Exists(task.getStatusTask().getCode())) {
                return Result.
                        failure("O estado que está a ser inserido não exite, deve colocar um dos seguintes estados: " +
                                "PENDENTE(0), EM_PROGRESSO(1), CONCLUIDA(2)");
            }

            Task createdTask =  repository.save(task);

            return Result.success(createdTask);

        } catch (Exception e){
            return Result.failure("Erro ao criar tarefa: " + e.getMessage());
        }
    }

    @Override
    public Result<List<Task>> getAll() {
        try {
            List<Task> tasks;

             tasks = repository.getAll();

             if(tasks.isEmpty()) {
                 return Result.failure("Não há nenhuma tarefa registada no sistema");
             }
             return  Result.success(tasks);

        }catch (Exception e) {
            return Result.failure("Erro ao buscar tarefas: " + e.getMessage());
        }
    }

    @Override
    public Result<Task> getById(String strId) {
        try {
            Task task = null;

            if(strId.isEmpty()) return Result.failure("O Id da tarefa deve estar preenchido");

            UUID id = UUID.fromString(strId);

            if(!existsTask(id)) return Result.failure("Não existe nenhuma tarefa com o Id inserido");

            task = repository.getById(id);

            return  Result.success(task);

        }catch (Exception e) {
            return Result.failure("Erro ao buscar tarefa por Id: " + e.getMessage());
        }
    }

    @Override
    public Result<Task> update(String strId, Task task) {
        try {
            UUID id = UUID.fromString(strId);

            if(!existsTask(id)) return Result.failure("Não existe nenhuma tarefa com o Id inserido");

            if(task.getTitulo().isEmpty() || task.getDescricao().isEmpty()){
                return Result.failure("O titulo e descrição devem estar preenchidos");
            }

            if(task.getDataConclusao() != null && task.getDataConclusao().isBefore(task.getDataCriacao())){
                return Result.failure("A Data de Conclusão não pode ser inferior à Data de Crição");
            }

            if(!status_Task_Exists(task.getStatusTask().getCode())) {
                return Result.
                        failure("O estado que está a ser inserido não exite, deve colocar um dos seguintes estados: " +
                                "PENDENTE(0), EM_PROGRESSO(1), CONCLUIDA(2)");
            }


            Task updatedTask =  repository.update(id, task);

            return Result.success(updatedTask);

        } catch (Exception e){
            return Result.failure("Erro ao actualizar tarefas: " + e.getMessage());
        }
    }

    @Override
    public Result<String> delete(String strId) {
        try {
            Task task = null;

            if(strId.isEmpty()) return Result.failure("O Id da tarefa deve estar preenchido");

            UUID id = UUID.fromString(strId);

            if(!existsTask(id)) return Result.failure("Não existe nenhuma tarefa com o Id inserido");

            repository.delete(id);

            return Result.success(null);

        }catch (Exception e) {
            return Result.failure("Erro ao buscar tarefa por Id: " + e.getMessage());
        }
    }

    @Override
    public Result<List<Task>> getByStatus(int statusTask) {
        try {
            List<Task> tasks;

            StatusTask code = fromInt(statusTask);

            tasks = repository.getByStatus(code);

            if(tasks.isEmpty()) {
                return Result.failure("Não há nenhuma tarefa no estado: " + code.name());
            }
            return  Result.success(tasks);

        }catch (Exception e) {
            return Result.failure("Erro ao buscar tarefas: " + e.getMessage());
        }
    }


    private boolean status_Task_Exists(int code) {
        for (StatusTask status : StatusTask.values()) {
            if (status.getCode() == code) {
                return true;
            }
        }
        return false;
    }

    private StatusTask fromInt(int value) {
        StatusTask code = null;
        for (StatusTask status : StatusTask.values()) {
            if (status.getCode() == value) {
                code = status;
            }
        }

        return code;
    }

    private boolean existsTask(UUID id){
        return repository.getById(id) != null;
    }
}
