package com.uniovi.notaneitor.controllers;

import com.uniovi.notaneitor.entities.Mark;
import com.uniovi.notaneitor.services.MarksService;
import com.uniovi.notaneitor.services.UsersService;
import com.uniovi.notaneitor.validators.AddMarkValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

//@RestController  Para que pueda Responder con VIstas, cambiar a @Controller
@Controller
public class MarksController {

    //@Autowired  //Inyectar el servicio (el cual crea Bean)
    //private MarksService marksService;

    // Inyectamos el servicio por inyección basada en constructor
    private final MarksService marksService;
    private final UsersService usersService; //los dos servicios, pues ahora las Marks van relacionadas con los Users

    private final AddMarkValidator addMarkValidator;

    public MarksController(MarksService marksService, UsersService usersService, AddMarkValidator addMarkValidator) {
        this.marksService = marksService;
        this.usersService = usersService;

        this.addMarkValidator = addMarkValidator;
    }

    //Metodos para cada URL a la que responde el controlador:

    @RequestMapping("/mark/list")
    public String getList(Model model) { //clase Model, que sirver para pasar datos a la vista
        model.addAttribute("markList", marksService.getMarks()); //asigna la lista de marcas al modelo como atributo
        return "mark/list"; //devuelve la vista mark/list.html
    }

    @RequestMapping("/mark/list/update")
    public String updateList(Model model){
        model.addAttribute("markList", marksService.getMarks() );
        return "mark/list :: marksTable";  //EN LUGAR DE RETORNAR TODA LA VISTA, SOLO DEVUELVE EL FRAGMENTO marksTable (declarado en list.html)
    }



    @RequestMapping(value = "/mark/add", method = RequestMethod.POST)
    public String setMark(@ModelAttribute Mark mark, BindingResult result, Model model) { //MODEL PARA QUE, AL HABER ERRORES, PUEDA RECARGAR LA LISTA DE USUARIOS
        addMarkValidator.validate(mark, result);
        if (result.hasErrors()) {
            model.addAttribute("usersList", usersService.getUsers());  //En caso de errores, recarga la lista de usuarios

            return "mark/add";
        }
        marksService.addMark(mark);
        return "redirect:/mark/list";
    }


    @RequestMapping(value = "/mark/add")
    public String getMark(Model model){
        model.addAttribute("usersList", usersService.getUsers());

        model.addAttribute("mark", new Mark()); //resuelve los errores del addMarkValidator, pues identifica el objeto Mark en los

        return "mark/add";
    }


    @RequestMapping("/mark/details/{id}")
    public String getDetail(Model model, @PathVariable Long id) { //anadimos el Model, para pasar datos a la vista
        model.addAttribute("mark", marksService.getMark(id)); //guardamos la mark en el modelo
        return "mark/details"; //devolvemos la vista mark/details.html
    }

    @RequestMapping("/mark/delete/{id}")
    public String deleteMark(@PathVariable Long id) {
        marksService.deleteMark(id);
        //return "Ok"; //Asi, con el Model, intenta pasacar un fichero Ok, no un string y ya
        return "redirect:/mark/list";
    }

    @RequestMapping(value = "/mark/edit/{id}")
    public String getEdit(Model model, @PathVariable Long id) {
        model.addAttribute("mark", marksService.getMark(id));
        model.addAttribute("usersList", usersService.getUsers());
        return "mark/edit";
    }

    //Metodo que responde a la PETICION POST enviada por el formulario de edit.html de la Mark en cuestion
    @RequestMapping(value="/mark/edit/{id}", method=RequestMethod.POST)
    public String setEdit(@ModelAttribute Mark mark, @PathVariable Long id){
        Mark originalMark = marksService.getMark(id);
        // modificar solo score y description
        originalMark.setScore(mark.getScore());
        originalMark.setDescription(mark.getDescription());
        marksService.addMark(originalMark);

        return "redirect:/mark/details/"+id;  //tras editar y guardar, redirige a la vista de detalles de la Mark
    }
}