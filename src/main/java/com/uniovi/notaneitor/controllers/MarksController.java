package com.uniovi.notaneitor.controllers;

import com.uniovi.notaneitor.entities.Mark;
import com.uniovi.notaneitor.services.MarksService;
import com.uniovi.notaneitor.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.*;
import com.uniovi.notaneitor.entities.User;


//@RestController  Para que pueda Responder con VIstas, cambiar a @Controller
@Controller
public class MarksController {

    //@Autowired  //Inyectar el servicio (el cual crea Bean)
    //private MarksService marksService;

    // Inyectamos el servicio por inyección basada en constructor
    private final MarksService marksService;
    private final UsersService usersService;

    private final HttpSession httpSession;


    public MarksController(MarksService marksService, UsersService usersService,
                           HttpSession httpSession) {
        this.marksService = marksService;
        this.usersService = usersService;

        this.httpSession = httpSession;
    }

    //Metodos para cada URL a la que responde el controlador:

    @RequestMapping("/mark/list")
    public String getList(Model model, Principal principal,
                          @RequestParam(value="", required=false) String searchText) { //clase Model, que sirver para pasar datos a la vista
        //model.addAttribute("markList", marksService.getMarks()); //asigna la lista de marcas al modelo como atributo
        String dni = principal.getName(); // DNI es el name de la autenticación
        User user = usersService.getUserByDni(dni);

        if(searchText != null && !searchText.isEmpty()){
            model.addAttribute("markList", marksService.searchMarksByDescriptionAndNameForUser(searchText, user));
        }else{
            model.addAttribute("markList", marksService.getMarksForUser(user));
        }

        //model.addAttribute("markList", marksService.getMarksForUser(user) );
        return "mark/list"; //devuelve la vista mark/list.html
    }

    @RequestMapping("/mark/list/update")
    public String updateList(Model model, Principal principal){
        //model.addAttribute("markList", marksService.getMarks() );
        String dni = principal.getName(); // DNI es el name de la autenticación
        User user = usersService.getUserByDni(dni);
        model.addAttribute("markList", marksService.getMarksForUser(user));
        return "mark/list :: marksTable";  //EN LUGAR DE RETORNAR TODA LA VISTA, SOLO DEVUELVE EL FRAGMENTO marksTable (declarado en list.html)
    }



    @RequestMapping(value = "/mark/add", method = RequestMethod.POST) //ESTA EN CONCRETO especificamos que use POST
    public String setMark(@ModelAttribute Mark mark) { //hacemos uso de la clase Mark de Entities

        marksService.addMark(mark); //anadimos la mark pasada por parametro (ModelAttribute)
        //return "Ok"; //Asi, con el Model, intenta pasacar un fichero Ok, no un string y ya
        return "redirect:/mark/list";
    }
    //add normal con GET
    @RequestMapping(value = "/mark/add")
    public String getMark(Model model){
        model.addAttribute("usersList", usersService.getUsers());
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
        marksService.addMark(originalMark); //EDIT USER: AQUI SAVE SEGUN PELAYO.

        return "redirect:/mark/details/"+id;  //tras editar y guardar, redirige a la vista de detalles de la Mark
    }


    @RequestMapping(value = "/mark/{id}/resend", method = RequestMethod.GET)
    public String setResendTrue(@PathVariable Long id) {
        marksService.setMarkResend(true, id);
        return "redirect:/mark/list";
    }
    @RequestMapping(value = "/mark/{id}/noresend", method = RequestMethod.GET)
    public String setResendFalse(@PathVariable Long id) {
        marksService.setMarkResend(false, id);
        return "redirect:/mark/list";
    }
}