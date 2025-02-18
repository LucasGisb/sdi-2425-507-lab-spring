package com.uniovi.notaneitor.repositories;

import com.uniovi.notaneitor.entities.Mark;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Query;

                                    //Esta Crud es una interfaz que se encarga de las operaciones de los datos CRUD
public interface MarksRepository extends CrudRepository<Mark, Long> {
    @Modifying
    @Transactional
    @Query("UPDATE Mark SET resend = ?1 WHERE id = ?2") //reenvio de las notas de los alumnos
    void updateResend(Boolean resend, Long id);
}