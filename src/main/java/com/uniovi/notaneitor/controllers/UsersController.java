package com.uniovi.notaneitor.controllers;
import com.uniovi.notaneitor.services.SecurityService;
import org.springframework.beans.factory.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.uniovi.notaneitor.entities.*;
import com.uniovi.notaneitor.services.UsersService;

@Controller
public class UsersController {

    private final UsersService usersService;

    private final SecurityService securityService;

    public UsersController(UsersService usersService, SecurityService securityService) {
        this.usersService = usersService;

        this.securityService = securityService;
    }

    @RequestMapping("/user/list")
    public String getListado(Model model) {
        model.addAttribute("usersList", usersService.getUsers());
        return "user/list";
    }

    @RequestMapping(value = "/user/add")
    public String getUser(Model model) {
        model.addAttribute("usersList", usersService.getUsers());
        return "user/add";
    }

    @RequestMapping(value = "/user/add", method = RequestMethod.POST)
    public String setUser(@ModelAttribute User user) {
        usersService.addUser(user);
        return "redirect:/user/list";
    }

    @RequestMapping("/user/details/{id}")
    public String getDetail(Model model, @PathVariable Long id) {
        model.addAttribute("user", usersService.getUser(id));
        return "user/details";
    }

    @RequestMapping("/user/delete/{id}")
    public String delete(@PathVariable Long id) {
        usersService.deleteUser(id);
        return "redirect:/user/list";
    }

    @RequestMapping(value = "/user/edit/{id}")
    public String getEdit(Model model, @PathVariable Long id) {
        User user = usersService.getUser(id);
        model.addAttribute("user", user);
        return "user/edit";
    }

    @RequestMapping(value = "/user/edit/{id}", method = RequestMethod.POST)
    public String setEdit(@PathVariable Long id, @ModelAttribute User user) {
        usersService.addUser(user);
        return "redirect:/user/details/" + id;
    }



    @RequestMapping(value = "/signup", method = RequestMethod.POST) //registrar un nuevo usuario
    public String signup(@ModelAttribute("user") User user, Model model) {
        usersService.addUser(user);
        securityService.autoLogin(user.getDni(), user.getPasswordConfirm());
        return "redirect:home";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping(value = {"/home"}, method = RequestMethod.GET)
    public String home(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();  //Obtiene la Autentificacion del usuario actual
        String dni = auth.getName(); //obtenemos IDENTIFICADOR (dni) del usuario autentificado
        User activeUser = usersService.getUserByDni(dni); //sacamos el Usuario en base a lo anterior
        model.addAttribute("markList", activeUser.getMarks()); //Sacamos la lista de notas del usuario registrado
        return "home";
    }


}
