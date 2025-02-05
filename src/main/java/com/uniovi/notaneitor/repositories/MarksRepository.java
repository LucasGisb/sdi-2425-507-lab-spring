package com.uniovi.notaneitor.repositories;

import com.uniovi.notaneitor.entities.Mark;
import org.springframework.data.repository.CrudRepository;

                                    //Esta Crud es una interfaz que se encarga de las operaciones de los datos CRUD
public interface MarksRepository extends CrudRepository<Mark, Long> {

}