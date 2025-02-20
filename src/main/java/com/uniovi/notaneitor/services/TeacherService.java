package com.uniovi.notaneitor.services;

import com.uniovi.notaneitor.entities.Teacher;
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import java.util.LinkedList;
import java.util.List;

@Service
public class TeacherService {

    private final List<Teacher> teacherList = new LinkedList<>();

    @PostConstruct
    public void init() {
        teacherList.add(new Teacher(1L, "1", "Paco", "Galaxias", "Profesor Titular"));
        teacherList.add(new Teacher(2L, "2", "Miguel", "Galaxias", "Catedrático"));
    }

    public List<Teacher> getTeachers() {
        return teacherList;
    }
    public Teacher getTeacher(String dni) {
        return teacherList.stream()
                .filter(teacher -> teacher.getDni().equals(dni)).findFirst().get();
    }
    public void addTeacher(Teacher teacher) {
        // Si en Id es null le asignamos el ultimo + 1 de la lista
        if (teacher.getId() == null) {
            //mark.setId(marksList.get(marksList.size() - 1).getId() + 1);
            teacher.setId(teacherList.get(teacherList.size() - 1).getId() + 1);
        }
        teacherList.add(teacher);
    }
    public void deleteTeacher(Long id) {
        teacherList.removeIf(teacher -> teacher.getId().equals(id));
    }


    public Teacher getTeacherById(Long id) {
        return teacherList.stream()
                .filter(teacher -> teacher.getId().equals(id)).findFirst().get();
    }

    public void editTeacher(Teacher teacher) {
        Teacher existingTeacher = getTeacherById(teacher.getId());
        if (existingTeacher != null) {
            existingTeacher.setDni(teacher.getDni());
            existingTeacher.setName(teacher.getName());
            existingTeacher.setSurname(teacher.getSurname());
            existingTeacher.setCategory(teacher.getCategory());
        }
    }

}