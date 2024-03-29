package dev.naul.todolist.task;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name = "tb_task")
public class TaskModel {    
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    private UUID userId;
    @Column(length = 50)
    private String title;
    private String description;
    private String priority;

    private LocalDateTime startTime;
    private LocalDateTime finishTime;

    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public void setTitle(String title) throws Exception {
        System.out.println("INSIDE SET TITLE");
        System.out.println(title);
        if (title.length() > 50) {
            throw new Exception("O campo title deve conter no máximo 50 caracteres");
        }

        this.title = title;
    }
}
