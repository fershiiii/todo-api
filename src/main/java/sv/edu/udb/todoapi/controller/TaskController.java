package sv.edu.udb.todoapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sv.edu.udb.todoapi.model.Task;
import sv.edu.udb.todoapi.service.TaskService;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    // GET /api/tasks
    @GetMapping
    public List<Task> getAll() {
        return taskService.getAll();
    }

    // POST /api/tasks
    @PostMapping
    public Task create(@RequestBody Task task) {
        // si no mandan completed, por defecto false
        task.setCompleted(false);
        return taskService.create(task);
    }

    // GET /api/tasks/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Task> getById(@PathVariable Long id) {
        return taskService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // PATCH /api/tasks/{id}/complete
    @PatchMapping("/{id}/complete")
    public ResponseEntity<Task> complete(@PathVariable Long id) {
        return taskService.complete(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE /api/tasks/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean deleted = taskService.delete(id);
        return deleted ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}

