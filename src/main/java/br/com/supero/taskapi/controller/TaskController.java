package br.com.supero.taskapi.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.supero.taskapi.model.Task;
import br.com.supero.taskapi.repository.TaskRepository;

@RestController
public class TaskController {
  
  @Autowired
  private TaskRepository taskRepository;

  /**
   * Get method to return all tasks
   * @return all tasks
   */
  @CrossOrigin
  @RequestMapping(value = "/tasks", method = RequestMethod.GET)
  public List<Task> get(){
    return taskRepository.findAll();
  }

  /**
   * Get method to return only one task by id
   * @param id task id
   * @return only one task
   * @return Http status NOT_FOUND to a task not founded
   */
  @CrossOrigin
  @RequestMapping(value = "/tasks/{id}", method = RequestMethod.GET)
  public ResponseEntity<Task> getbyId(@PathVariable(value = "id") long id){
    Optional<Task> task = taskRepository.findById(id);

    if (task.isPresent())
      return new ResponseEntity<Task>(task.get(), HttpStatus.OK);
    else
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  /**
   * Post method to insert a new task
   * @param task task object
   * @return new inserted task
   * @return Http status FORBIDDEN to internal error
   */
  @CrossOrigin
  @RequestMapping(value = "/tasks", method =  RequestMethod.POST)
  public ResponseEntity post(@RequestBody Task task)
  {
    try {
      task.setCreatedAt(new Date());
      task.setUpdatedAt(new Date());
      return new ResponseEntity<Task>(taskRepository.save(task), HttpStatus.OK);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
    }
  }

  /**
   * Put method to update an existing task
   * @param id task id
   * @param newTask object with updates
   * @return task updated
   * @return Http status NOT_FOUND to a task not founded
   * @return Http status FORBIDDEN to internal error
   */
  @CrossOrigin
  @RequestMapping(value = "/tasks/{id}", method =  RequestMethod.PUT)
  public ResponseEntity put(@PathVariable(value = "id") long id, @RequestBody Task newTask)
  {
    try {
      
      Optional<Task> oldTask = taskRepository.findById(id);
      if(oldTask.isPresent()){
          Task task = oldTask.get();
          if (newTask.getTitle() != null)
            task.setTitle(newTask.getTitle());
          if (newTask.getDescription() != null)  
            task.setDescription(newTask.getDescription());
          if (newTask.getStatus() != null)  
            task.setStatus(newTask.getStatus());
          task.setUpdatedAt(new Date());
          taskRepository.save(task);
          return new ResponseEntity<Task>(task, HttpStatus.OK);
      }
      else
          return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not foound");
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
    }
  }

  /**
   * Delete method to delete an existing task
   * @param id task id
   * @return Http status OK to task successfully deleted
   * @return Http status NOT_FOUND to a task not founded
   * @return Http status FORBIDDEN to internal error
   */
  @CrossOrigin
  @RequestMapping(value = "/tasks/{id}", method = RequestMethod.DELETE)
  public ResponseEntity delete(@PathVariable(value = "id") long id)
  {
    try {
      
      Optional<Task> task = taskRepository.findById(id);
        if(task.isPresent()){
          taskRepository.delete(task.get());
          return ResponseEntity.status(HttpStatus.OK).body("Ok");
        }
        else
          return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not foound");
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
    }
  }
}