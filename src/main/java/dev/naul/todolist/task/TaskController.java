package dev.naul.todolist.task;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/task")
public class TaskController {
    @Autowired
    private ITaskRepository taskRepository;

    @PostMapping
    public ResponseEntity create(@RequestBody TaskModel taskModel, @RequestAttribute UUID userId) {
        taskModel.setUserId(userId);
        var task = this.taskRepository.save(taskModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(task);
    }

    @GetMapping
    public ResponseEntity listByUser(@RequestAttribute UUID userId) {
        var tasks = this.taskRepository.findAllByUserId(userId);
        if (tasks == null) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Usuário não possuir tarefas");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(tasks);
    }
}
