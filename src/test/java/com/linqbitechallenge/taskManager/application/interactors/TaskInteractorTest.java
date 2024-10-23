package com.linqbitechallenge.taskManager.application.interactors;

import com.linqbitechallenge.taskManager.application.dto.Result;
import com.linqbitechallenge.taskManager.domain.enums.StatusTask;
import com.linqbitechallenge.taskManager.domain.models.Task;
import com.linqbitechallenge.taskManager.infra.persistence.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaskInteractorTest {

    @InjectMocks
    private TaskInteractor taskInteractor;

    @Mock
    private TaskRepository taskRepository;

    private Task task;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        task =  new Task("Título", "Descrição", LocalDate.now(), LocalDate.now(), StatusTask.PENDENTE);
    }

    //Deve retornar erro se o titulo ou a descrição estiveram vazios
    @Test
    void saveTaskWithEmptyTitleOrDescription_shouldFail() {
        task.setTitulo("");
        Result<Task> result = taskInteractor.save(task);

        assertEquals("O titulo e descrição devem estar preenchidos", result.getMessage());
    }


    //Deve retornar erro se a Data de Conclusão fôr inferior a Data de criação
    @Test
    void saveTaskWithInvalidDate_shouldFail() {
        task.setDataConclusao(LocalDate.now().minusDays(1));
        Result<Task> result = taskInteractor.save(task);

        assertEquals("A Data de Conclusão não pode ser inferior à Data de Crição", result.getMessage());
    }

    //Deve criar uma tarefa
    @Test
    public  void save() {
        when(taskRepository.save(task)).thenReturn(task);
        Task createdTask = taskInteractor.save(task).getData();
        assertNotNull(createdTask);
        assertEquals(task.getTitulo(), createdTask.getTitulo());
        assertEquals(task.getDescricao(), createdTask.getDescricao());
        assertEquals(task.getDataCriacao(), createdTask.getDataCriacao());
        assertEquals(task.getDataConclusao(), createdTask.getDataConclusao());
        assertEquals(task.getStatusTask(), createdTask.getStatusTask());
    }

    //Deve carregar todas as tarefas
    @Test
    void getAll() {
        List<Task> taskList = List.of(task);
        when(taskRepository.getAll()).thenReturn(taskList);
        List<Task> tasks = taskInteractor.getAll().getData();
        assertFalse(tasks.isEmpty());
        assertEquals(1, tasks.size());
    }

    //Deve retonar erro se parametro id estiver vazio
    @Test
    void getByIdWithEmptyId_shouldFail() {
        Result<Task> result = taskInteractor.getById("");

        assertEquals("O Id da tarefa deve estar preenchido", result.getMessage());
    }

    //Deve retorna erro se não existir nenhma tarefa com Id inserido
    @Test
    void getByIdNonExistentTask_shouldFail() {
        when(taskRepository.getById(any(UUID.class))).thenReturn(null);

        Result<Task> result = taskInteractor.getById(task.getId().toString());

        assertEquals("Não existe nenhuma tarefa com o Id inserido", result.getMessage());
    }


    //Deve carregar uma tarefa pelo ID
    @Test
    void getById() {
        UUID taskId = task.getId();
        when(taskRepository.getById(taskId)).thenReturn(task);
        Task foundTask = taskInteractor.getById(taskId.toString()).getData();
        assertNotNull(foundTask);
        assertEquals(task.getId(), foundTask.getId());
    }


    //Deve carregar as tarefas filtradas pelo status
    @Test
    void getByStatus() {
        List<Task> taskList = List.of(task);
        when(taskRepository.getByStatus(StatusTask.PENDENTE)).thenReturn(taskList);
        List<Task> tasks = taskInteractor.getByStatus(StatusTask.PENDENTE.getCode()).getData();
        assertFalse(tasks.isEmpty());
        assertEquals(1, tasks.size());
    }

    //Deve eliminar uma tarefa
    @Test
    void delete() {
        UUID taskId = task.getId();
        when(taskRepository.getById(taskId)).thenReturn(task);
        taskInteractor.delete(taskId.toString());
        verify(taskRepository, times(1)).delete(taskId);
    }
}