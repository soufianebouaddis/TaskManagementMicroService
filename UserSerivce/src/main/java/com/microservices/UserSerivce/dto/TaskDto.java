package com.microservices.UserSerivce.dto;

import java.time.LocalDate;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskDto {
    private int id;
    @NotNull(message = "Date Debut cannot be null")
    private LocalDate date_debut;
    @NotNull(message = "Date Fin cannot be null")
    private LocalDate date_fin;
    @NotNull(message = "Task status cannot be null")
    @Enumerated(EnumType.STRING)
    private TaskStatus status;
    @NotNull(message = "Label cannot be null")
    private String label;
    @NotNull(message = "Description cannot be null")
    private String description;

}