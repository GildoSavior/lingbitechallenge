package com.linqbitechallenge.taskManager.domain.models;

import com.linqbitechallenge.taskManager.domain.enums.StatusTask;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "tasks")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Task {
    @Id
    private UUID id = UUID.randomUUID();
    private String titulo;
    private String descricao;
    private LocalDate dataCriacao;
    private LocalDate dataConclusao;
    private StatusTask statusTask;


    public Task(String titulo, String descricao, LocalDate dataCriacao, LocalDate dataConclusao,StatusTask statusTask) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.dataCriacao = dataCriacao;
        this.dataConclusao = dataConclusao;
        this.statusTask = statusTask;
    }

}
