package com.uniovi.notaneitor.controllers;
import com.uniovi.notaneitor.services.RolesService;
import com.uniovi.notaneitor.services.SecurityService;
import org.springframework.beans.factory.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.uniovi.notaneitor.entities.*;
import com.uniovi.notaneitor.services.UsersService;
import com.uniovi.notaneitor.validators.SignUpFormValidator;

@Controller
public class UsersController {

    private final SignUpFormValidator signUpFormValidator;

    private final UsersService usersService;

    private final SecurityService securityService;

    private final RolesService rolesService;

    public UsersController(UsersService usersService, SecurityService securityService,
                           SignUpFormValidator signUpFormValidator, RolesService rolesService) {
        this.usersService = usersService;

        this.securityService = securityService;

        this.signUpFormValidator = signUpFormValidator;

        this.rolesService= rolesService;
    }

    @RequestMapping("/user/list")
    public String getListado(Model model) {
        model.addAttribute("usersList", usersService.getUsers());
        return "user/list";
    }

    //Para el boton de Actualizar de la vitsa list
    @RequestMapping("/user/list/update")
    public String updateList(Model model){
        model.addAttribute("usersList", usersService.getUsers() );
        return "user/list :: usersTable";  //EN LUGAR DE RETORNAR TODA LA VISTA, SOLO DEVUELVE EL FRAGMENTO marksTable (declarado en list.html)
    }


    @RequestMapping(value = "/user/add")
    public String getUser(Model model) {
        //model.addAttribute("usersList", usersService.getUsers());

        model.addAttribute("rolesList", rolesService.getRoles());

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
        //usersService.addUser(user);
        usersService.editUser(user); //identico a addUser, pero que no actualice su contrasena
        return "redirect:/user/details/" + id;
    }



    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String signup(Model model) {
        model.addAttribute("user", new User()); //esto se solicita en el th:object="${user}" del form de la vista signup
        return "signup";
    }
    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String signup(@Validated User user, BindingResult result) {
        //Se rellena el formulario de signup
        signUpFormValidator.validate(user, result); //se llama al metodo del validator
        if (result.hasErrors()) { //si hay errores, devuelve a la pagina
            return "signup";
        }

        user.setRole(rolesService.getRoles()[0]);

        //Si no hay errores, se añade el usuario y se logea, y se envia al home
        usersService.addUser(user);
        securityService.autoLogin(user.getDni(), user.getPasswordConfirm());
        return "redirect:/home";
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
