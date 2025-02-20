package com.uniovi.notaneitor.repositories;

import com.uniovi.notaneitor.entities.Mark;
import com.uniovi.notaneitor.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

//Esta Crud es una interfaz que se encarga de las operaciones de los datos CRUD
public interface MarksRepository extends CrudRepository<Mark, Long> {
    @Modifying
    @Transactional
    @Query("UPDATE Mark SET resend = ?1 WHERE id = ?2") //reenvio de las notas de los alumnos
    void updateResend(Boolean resend, Long id);

    @Query("Select  r FROM Mark r WHERE (LOWER(r.description) LIKE LOWER(?1) OR LOWER(r.user.name) LIKE LOWER(?1))")
    Page<Mark> searchByDescriptionAndName(Pageable pageable, String searchText);
    @Query("Select  r FROM Mark r WHERE (LOWER(r.description) LIKE LOWER(?1) OR LOWER(r.user.name) LIKE LOWER(?1)) AND r.user=?2")
    Page<Mark> searchByDescriptionNameAndUser(Pageable pageable, String searchText, User user);

    @Query("SELECT r FROM Mark r WHERE r.user = ?1 ORDER BY r.id ASC")  //para que JPA no altere el orden al modificar las notas desde la vista
    Page<Mark> findAllByUser(Pageable pageable, User user); //para mostrar todas las notas de un user

    Page<Mark> findAll(Pageable pageable);
}