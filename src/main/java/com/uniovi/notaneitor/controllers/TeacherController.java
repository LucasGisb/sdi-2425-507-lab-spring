package com.uniovi.notaneitor.controllers;

import com.uniovi.notaneitor.entities.Teacher;
import com.uniovi.notaneitor.services.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
public class TeacherController {

    @Autowired  //Inyectar el servicio (el cual crea Bean)
    private TeacherService teacherService;

    //Metodo por cada URL a la que responde el controlador
    @RequestMapping("/teacher/list")
    public String getList() {
        //return "Getting List";
        return teacherService.getTeachers().toString();
    }

    /*
    @RequestMapping(value = "/teacher/add", method = RequestMethod.POST) //Especificamos que use POST
    public String setTeacher(@ModelAttribute Teacher teacher) {
        teacherService.addTeacher(teacher);
        return "added: Teacher with dni: " + teacher.getDni()
                + " with name : " + teacher.getName()
                + " surname: " + teacher.getSurname()
                + " category: " + teacher.getCategory();
    }
     */

    @RequestMapping(value = "/teacher/add")
    public String setTeacher(@ModelAttribute Teacher teacher) {
        teacherService.addTeacher(teacher);
        return "added: Teacher with dni: " + teacher.getDni()
                + " with name : " + teacher.getName()
                + " surname: " + teacher.getSurname()
                + " category: " + teacher.getCategory();
    }

    //ESTE CON DNI
    @RequestMapping("/teacher/details/{dni}")
    public String getDetail(@PathVariable String dni) {
        return teacherService.getTeacher(dni).toString();
    }

    @RequestMapping("/teacher/delete/{id}")
    public String deleteTeacher(@PathVariable Long id) {
        teacherService.deleteTeacher(id);
        return "Deleted teacher with =>" + id;
    }


    @RequestMapping(value = "/teacher/edit/{dni}")
    public String setEditTeacher(@ModelAttribute Teacher teacher, @PathVariable String dni) {
        Teacher existingTeacher = teacherService.getTeacher(dni);
        if (existingTeacher != null) {
            existingTeacher.setName(teacher.getName());
            existingTeacher.setSurname(teacher.getSurname());
            existingTeacher.setCategory(teacher.getCategory());
            return "Profesor editado de dni: => " + existingTeacher.getDni();
        } else {
            return "No existe el profesor con dni => " + dni;
        }
    }




}
