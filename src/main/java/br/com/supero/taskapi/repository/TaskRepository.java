package br.com.supero.taskapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.supero.taskapi.model.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {}