package com.uniovi.notaneitor.controllers;

import com.uniovi.notaneitor.entities.Mark;
import com.uniovi.notaneitor.services.MarksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class MarksController {

    @Autowired  //Inyectar el servicio (el cual crea Bean)
    private MarksService marksService;


    //Metodos para cada URL a la que responde el controlador:

    @RequestMapping("/mark/list")
    public String getList() {
        return marksService.getMarks().toString(); //devuelve la lista de marcas obtenidas
    }

    @RequestMapping(value = "/mark/add", method = RequestMethod.POST) //Especificamos que use POST
    public String setMark(@ModelAttribute Mark mark) { //hacemos uso de la clase Mark de Entities

        marksService.addMark(mark); //anadimos la mark pasada por parametro (ModelAttribute)
        return "Ok";
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
    public String getDetail(@PathVariable Long id) {
        return marksService.getMark(id).toString();
    }

    @RequestMapping("/mark/delete/{id}")
    public String deleteMark(@PathVariable Long id) {
        marksService.deleteMark(id);
        return "Ok";
    }

}