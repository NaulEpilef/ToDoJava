package dev.naul.todolist.task;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    @Autowired
    private ITaskRepository taskRepository;

    @PostMapping
    public ResponseEntity create(@RequestBody TaskModel taskModel, HttpServletRequest request) {
        var userId = request.getAttribute("userId");
        taskModel.setUserId((UUID) userId);

        var currentDate = LocalDateTime.now();

        if (currentDate.isAfter(taskModel.getStartTime()) || currentDate.isAfter(taskModel.getFinishTime())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A data de início ou data de término deve ser maior que a data atual.");
        }

        if (taskModel.getStartTime().isAfter(taskModel.getFinishTime())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A data de início deve vir depois da data de fim.");
        }

        var task = this.taskRepository.save(taskModel);
        return ResponseEntity.status(HttpStatus.OK).body(task);
    }
}
