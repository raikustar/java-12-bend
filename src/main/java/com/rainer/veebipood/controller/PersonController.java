package com.rainer.veebipood.controller;


import com.rainer.veebipood.dto.AuthToken;
import com.rainer.veebipood.dto.EmailPassword;
import com.rainer.veebipood.dto.PersonDTO;
import com.rainer.veebipood.entity.Person;
import com.rainer.veebipood.models.EmailBody;
import com.rainer.veebipood.service.EmailService;
import com.rainer.veebipood.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
public class PersonController {

    @Autowired
    PersonService personService;

    @Autowired
    EmailService emailService;

    @GetMapping("public-persons")
    public List<PersonDTO> getPublicPersons() {
        return personService.getPublicPersons();
    }

    @GetMapping("email")
    public String sendEmail(@RequestBody EmailBody email) {
        emailService.sendEmail(email);
        return "Success";
    }

    @GetMapping("person")
    public Person getPerson() {
        return personService.getPerson();
    }

    @PutMapping("person")
    public Person editPerson(@RequestBody Person person) {
        return personService.editPerson(person);
    }


    @PostMapping("login")
    public AuthToken login(@RequestBody EmailPassword emailPassword) {
        return personService.login(emailPassword);
    }

    @PostMapping("signup")
    public AuthToken signup(@RequestBody Person person) {
        return personService.signUp(person);
    }

}
