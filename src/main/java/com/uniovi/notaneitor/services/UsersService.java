package com.uniovi.notaneitor.services;
import java.util.*;

import com.uniovi.notaneitor.repositories.UsersRepository;
//import jakarta.annotation.PostConstruct; //cambio de version
import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.uniovi.notaneitor.entities.*;
import com.uniovi.notaneitor.services.UsersService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Service
public class UsersService {

    private final UsersRepository usersRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UsersService(UsersRepository usersRepository, BCryptPasswordEncoder
            bCryptPasswordEncoder) {
        this.usersRepository = usersRepository;

        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @PostConstruct
    public void init() {
    }
    public List<User> getUsers() {
        List<User> users = new ArrayList<User>();
        usersRepository.findAll().forEach(users::add);
        return users;
    }
    public User getUser(Long id) {
        return usersRepository.findById(id).get();
    }

    public void addUser(User user) {
        if(user.getPassword() != null){
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        }

        usersRepository.save(user);
    }
    public void deleteUser(Long id) {
        usersRepository.deleteById(id);
    }

    public User getUserByDni(String dni) {
        //llama al metodo implementado de la interfaz UsersRepository
        return usersRepository.findByDni(dni);
    }



    public void editUser(User user) {
        /*
        if(user.getPassword() != null){
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        }
         */  //Al ser editar, no modifico la contrasena
        usersRepository.save(user);
    }
}