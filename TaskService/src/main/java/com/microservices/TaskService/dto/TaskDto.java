package com.microservices.TaskService.dto;

import java.time.LocalDate;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TaskDto {
    private int id;
    @NotNull(message = "Date Debut cannot be null")
    private LocalDate date_debut;
    @NotNull(message = "Date Fin cannot be null")
    private LocalDate date_fin;
    @NotNull(message = "Label cannot be null")
    private String label;
    @NotNull(message = "Description cannot be null")
    private String description;

}