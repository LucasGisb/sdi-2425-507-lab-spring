package com.uniovi.notaneitor.services;

import com.uniovi.notaneitor.entities.Mark;
import com.uniovi.notaneitor.repositories.MarksRepository;
//import jakarta.annotation.PostConstruct; //cambio de version

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.*;

@Service
public class MarksService {

    /*  Ahora cuento con MarksRepository, de donde se obtienen los datos de Marks
    private final List<Mark> marksList = new LinkedList<>();
    @PostConstruct
    public void init() {
        marksList.add(new Mark(1L, "Ejercicio 1", 10.0));
        marksList.add(new Mark(2L, "Ejercicio 2", 9.0));
    }
     */
    @Autowired
    private MarksRepository marksRepository;

    /* Inyección de dependencias basada en constructor (opción recomendada)*/
    private final HttpSession httpSession;

    @Autowired
    public MarksService(HttpSession httpSession) {
        this.httpSession = httpSession;
    }

    public List<Mark> getMarks() {
        List<Mark> marks = new ArrayList<Mark>();
        marksRepository.findAll().forEach(marks::add); //obtendo las Marks del repository
        return marks;
    }

    public Mark getMark(Long id) {
        //return marksRepository.findById(id).get();
        //Ahora tambien almacena info de la nota en la sesion
        Set<Mark> consultedList = (Set<Mark>) httpSession.getAttribute("consultedList");
        if (consultedList == null) {
            consultedList = new HashSet<>();
        }
        Mark mark = marksRepository.findById(id).isPresent() ? marksRepository.findById(id).get() : new Mark();
        consultedList.add(mark);
        httpSession.setAttribute("consultedList", consultedList);
        return mark;
    }

    public void addMark(Mark mark) {
        /*
        if (mark.getId() == null) {
            //mark.setId(marksList.get(marksList.size() - 1).getId() + 1);
            mark.setId(marksList.getLast().getId() + 1);
        }
        marksList.add(mark);
         */

        // Si en Id es null le asignamos el último + 1 de la lista
        marksRepository.save(mark);
    }
    public void deleteMark(Long id) {
        //marksList.removeIf(mark -> mark.getId().equals(id));
        marksRepository.deleteById(id);
    }
}

