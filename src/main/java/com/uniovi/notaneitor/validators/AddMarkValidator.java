package com.uniovi.notaneitor.validators;

import com.uniovi.notaneitor.entities.Mark;
import com.uniovi.notaneitor.services.MarksService;
import com.uniovi.notaneitor.services.UsersService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class AddMarkValidator implements Validator {

    private final MarksService marksService;

    public AddMarkValidator(MarksService marksService) {
        this.marksService = marksService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Mark.class.equals(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Mark mark = (Mark) target;

        //Primero los errores clasicos de que se dejen los campos vacios
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "score", "Error.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "Error.empty");

        //Ahora los errores nuevos propios de este validador
        //Nota (score) con valor no valido
        if (mark.getScore() < 0 || mark.getScore() > 10) {
            errors.rejectValue("score", "Error.addMark.score.range");
        }

        //Descripcion de la nota (description) que sea como minimo de 20
        if (mark.getDescription().length() < 20) {
            errors.rejectValue("description", "Error.addMark.description.length");
        }
    }
}