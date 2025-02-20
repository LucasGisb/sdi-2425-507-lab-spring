package com.uniovi.notaneitor.controllers;

import com.uniovi.notaneitor.entities.Teacher;
import com.uniovi.notaneitor.services.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

@Controller
public class TeacherController {

    @Autowired  //Inyectar el servicio (el cual crea Bean)
    private TeacherService teacherService;

    //Metodo por cada URL a la que responde el controlador

    @RequestMapping("/teacher/list")
    public String getList(Model model) { //IMP Modelo-Vista: Meto Model para pasar datos a la vista
        //return "Getting List";
        //return teacherService.getTeachers().toString();
        model.addAttribute("teacherList", teacherService.getTeachers()); //esto lo recibe y lo itera la vista de list
        return "professor/list";
    }


    @RequestMapping(value = "/teacher/add", method = RequestMethod.POST)
    public String setTeacher(@ModelAttribute Teacher teacher) {
        teacherService.addTeacher(teacher);

        return "redirect:/teacher/list";  //al anadir pasa a LA PETICION de la lista
    }

    @RequestMapping(value = "/teacher/add")
    public String setTeacher() {
        return "professor/add";
    }

    //ESTE CON DNI
    @RequestMapping("/teacher/details/{dni}")
    public String getDetail(Model model, @PathVariable String dni) { //IMP Modelo-Vista: Meto Model para pasar datos a la vista
        //return teacherService.getTeacher(dni).toString();
        model.addAttribute("teacher", teacherService.getTeacher(dni)); //esto lo recibe y lo itera la vista de details
        return "professor/details";
    }

    @RequestMapping("/teacher/delete/{id}")
    public String deleteTeacher(@PathVariable Long id) { //No POST pues va a ser un boton que al darle ya se registre el ID del que borrar, por lo que no hay que enviar nada POST
        teacherService.deleteTeacher(id);
        //return "Deleted teacher with =>" + id;
        return "redirect:/teacher/list";  //al borrar pasa a LA PETICION de lista
    }


    @RequestMapping(value = "/teacher/edit")
    public String setEditTeacher(@ModelAttribute Teacher teacherModelAtrib) {
        teacherService.editTeacher(teacherModelAtrib);
        return "Profesor editado de dni: => " + teacherModelAtrib.getDni();
    }




}