package com.uniovi.notaneitor.controllers;

import com.uniovi.notaneitor.entities.Mark;
import com.uniovi.notaneitor.services.MarksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

//@RestController  Para que pueda Responder con VIstas, cambiar a @Controller
@Controller
public class MarksController {

    @Autowired  //Inyectar el servicio (el cual crea Bean)
    private MarksService marksService;


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



    @RequestMapping(value = "/mark/add", method = RequestMethod.POST) //ESTA EN CONCRETO especificamos que use POST
    public String setMark(@ModelAttribute Mark mark) { //hacemos uso de la clase Mark de Entities

        marksService.addMark(mark); //anadimos la mark pasada por parametro (ModelAttribute)
        //return "Ok"; //Asi, con el Model, intenta pasacar un fichero Ok, no un string y ya
        return "redirect:/mark/list";
    }
        //add normal con GET
    @RequestMapping(value = "/mark/add")
    public String getMark() {
        return "mark/add";
    }

    //DOS FORMAS DE PASAR PARAMS GET (RequestParam y PathVariable)
        //RequestParam: http://localhost:8090/mark/details?id=4 o localhost:8090/mark/details?anotherParam=45&id=4
        //PathVariable: http://localhost:8090/mark/details/5/  (directamente poner el param de seguido)
    /*
    @RequestMapping("/mark/details")
    public String getDetail(@RequestParam Long id) {  //Param GET: paso de parametro clave adicional id adicional
        return "Getting Details =>" + id;
    }
    */
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
        return "mark/edit";
    }

    //Metodo que responde a la PETICION POST enviada por el formulario de edit.html de la Mark en cuestion
    @RequestMapping(value="/mark/edit/{id}", method=RequestMethod.POST)
    public String setEdit(@ModelAttribute Mark mark, @PathVariable Long id){
        mark.setId(id);
        marksService.addMark(mark);
        return "redirect:/mark/details/"+id;  //tras editar y guardar, redirige a la vista de detalles de la Mark
    }
}