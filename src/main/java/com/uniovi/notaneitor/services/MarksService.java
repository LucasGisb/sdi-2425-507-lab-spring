package com.uniovi.notaneitor.services;

import com.uniovi.notaneitor.entities.Mark;
import com.uniovi.notaneitor.entities.User;
import com.uniovi.notaneitor.repositories.MarksRepository;
//import jakarta.annotation.PostConstruct; //cambio de version

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.*;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

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

    public Page<Mark> getMarks(Pageable pageable) {
        Page<Mark> marks = marksRepository.findAll(pageable); //obtendo las Marks del repository
        return marks;
    }

    public Mark getMark(Long id) {
        Mark mark = marksRepository.findById(id).isPresent() ? marksRepository.findById(id).get() : new Mark();
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

    public void setMarkResend(boolean revised, Long id) {
        //Antes de reenviar, se comprueba que el usuario NO este modificando una nota de otro usuario distinto
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String dni = auth.getName();
        Mark mark = marksRepository.findById(id).get();
        if(mark.getUser().getDni().equals(dni) ) {
            marksRepository.updateResend(revised, id);
        }

    }

    public Page<Mark> getMarksForUser(Pageable pageable, User user) {
        Page<Mark> marks = new PageImpl<Mark>(new LinkedList<Mark>());
        if (user.getRole().equals("ROLE_STUDENT")) {
            marks = marksRepository.findAllByUser(pageable, user);
        }
        if (user.getRole().equals("ROLE_PROFESSOR")) {
            marks = getMarks(pageable); //es profesor: devuelve TODAS las notas
        }
        return marks;
    }


    public Page<Mark> searchMarksByDescriptionAndNameForUser(Pageable pageable,
                                                             String searchText, User user) {
        Page<Mark> marks = new PageImpl<Mark>(new LinkedList<Mark>());

        searchText = "%"+searchText+"%";

        if (user.getRole().equals("ROLE_STUDENT")) {
            marks = marksRepository.searchByDescriptionNameAndUser(pageable, searchText, user);
        }
        if (user.getRole().equals("ROLE_PROFESSOR")) {
            marks = marksRepository.searchByDescriptionAndName(pageable, searchText);
        }
        return marks;
    }
}

