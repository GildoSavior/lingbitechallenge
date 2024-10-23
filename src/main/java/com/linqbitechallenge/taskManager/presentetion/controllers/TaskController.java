package com.linqbitechallenge.taskManager.presentetion.controllers;

import com.linqbitechallenge.taskManager.application.dto.HttpResponse;
import com.linqbitechallenge.taskManager.application.dto.Result;
import com.linqbitechallenge.taskManager.domain.enums.HttpStatusCode;
import com.linqbitechallenge.taskManager.domain.models.Task;
import com.linqbitechallenge.taskManager.domain.usecases.ITaskUseCases;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final ITaskUseCases taskUseCases;

    public TaskController(ITaskUseCases taskUseCases) {
        this.taskUseCases = taskUseCases;
    }

    @PostMapping("/create")
    public ResponseEntity<HttpResponse<Task>> create(@RequestBody Task task) {
        HttpResponse<Task> httpResponse;
        HttpStatusCode status;

        try {
            Result<Task> result = taskUseCases.save(task);

            if(result.isOk()){
                status = HttpStatusCode.CREATED;
                httpResponse = new HttpResponse<>(result.getMessage(), status.getCode(), result.getData());
            } else {
                status = HttpStatusCode.BAD_REQUEST;
                httpResponse = new HttpResponse<>(result.getMessage(), status.getCode(),null);
            }
        } catch (Exception e) {
            status = HttpStatusCode.INTERNAL_SERVER_ERROR;
            httpResponse = new HttpResponse<>("Erro do servidor: " + e.getMessage(), status.getCode(), null);
        }

        return ResponseEntity.status(status.getCode()).body(httpResponse);
    }

    @GetMapping
    public ResponseEntity<HttpResponse<List<Task>>> findAll() {
        HttpResponse<List<Task>> httpResponse;
        HttpStatusCode status;

        try {
            Result<List<Task>> result = taskUseCases.getAll();

            if(result.isOk()){
                status = HttpStatusCode.OK;
                httpResponse = new HttpResponse<>(result.getMessage(), status.getCode(),result.getData());
            } else {
                status = HttpStatusCode.BAD_REQUEST;
                httpResponse = new HttpResponse<>(result.getMessage(), status.getCode(),null);
            }
        } catch (Exception e) {
            status = HttpStatusCode.INTERNAL_SERVER_ERROR;
            httpResponse = new HttpResponse<>("Erro do servidor:" + e.getMessage(), status.getCode(), null);
        }

        return ResponseEntity.status(status.getCode()).body(httpResponse);
    }

    @GetMapping("/findById")
    public ResponseEntity<HttpResponse<Task>> findById(@RequestParam String id) {
        HttpResponse<Task> httpResponse;
        HttpStatusCode status;

        try {
            Result<Task> result = taskUseCases.getById(id);

            if(result.isOk()){
                status = HttpStatusCode.OK;
                httpResponse = new HttpResponse<>(result.getMessage(), status.getCode(),result.getData());
            } else {
                status = HttpStatusCode.BAD_REQUEST;
                httpResponse = new HttpResponse<>(result.getMessage(), status.getCode(),null);
            }
        } catch (Exception e) {
            status = HttpStatusCode.INTERNAL_SERVER_ERROR;
            httpResponse = new HttpResponse<>("Erro do servidor:" + e.getMessage(), status.getCode(), null);
        }

        return ResponseEntity.status(status.getCode()).body(httpResponse);
    }

    @GetMapping("/findByStatus")
    public ResponseEntity<HttpResponse<List<Task>>> findByStatus(@RequestParam int taskStatus) {
        HttpResponse<List<Task>> httpResponse;
        HttpStatusCode status;

        try {
            Result<List<Task>> result = taskUseCases.getByStatus(taskStatus);

            if(result.isOk()){
                status = HttpStatusCode.OK;
                httpResponse = new HttpResponse<>(result.getMessage(), status.getCode(),result.getData());
            } else {
                status = HttpStatusCode.BAD_REQUEST;
                httpResponse = new HttpResponse<>(result.getMessage(), status.getCode(),null);
            }
        } catch (Exception e) {
            status = HttpStatusCode.INTERNAL_SERVER_ERROR;
            httpResponse = new HttpResponse<>("Erro do servidor:" + e.getMessage(), status.getCode(), null);
        }

        return ResponseEntity.status(status.getCode()).body(httpResponse);
    }

    @PutMapping("/update")
    public ResponseEntity<HttpResponse<Task>> update(@RequestParam String id, @RequestBody Task task) {
        HttpResponse<Task> httpResponse;
        HttpStatusCode status;

        try {
            Result<Task> result = taskUseCases.update(id, task);

            if(result.isOk()){
                status = HttpStatusCode.CREATED;
                httpResponse = new HttpResponse<>(result.getMessage(), status.getCode(),result.getData());
            } else {
                status = HttpStatusCode.BAD_REQUEST;
                httpResponse = new HttpResponse<>(result.getMessage(), status.getCode(),null);
            }
        } catch (Exception e) {
            status = HttpStatusCode.INTERNAL_SERVER_ERROR;
            httpResponse = new HttpResponse<>("Erro do servidor: " + e.getMessage(), status.getCode(), null);
        }

        return ResponseEntity.status(status.getCode()).body(httpResponse);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<HttpResponse<String>> delete(@RequestParam String id) {
        HttpResponse<String> httpResponse;
        HttpStatusCode status;

        try {
            Result<String> result = taskUseCases.delete(id);

            if(result.isOk()){
                status = HttpStatusCode.OK;
            } else {
                status = HttpStatusCode.BAD_REQUEST;
            }
            httpResponse = new HttpResponse<>(result.getMessage(), status.getCode(),null);
        } catch (Exception e) {
            status = HttpStatusCode.INTERNAL_SERVER_ERROR;
            httpResponse = new HttpResponse<>("Erro do servidor:" + e.getMessage(), status.getCode(), null);
        }

        return ResponseEntity.status(status.getCode()).body(httpResponse);
    }


}
