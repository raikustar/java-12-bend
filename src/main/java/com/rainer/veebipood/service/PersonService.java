package com.rainer.veebipood.service;

import com.rainer.veebipood.dto.AuthToken;
import com.rainer.veebipood.dto.EmailPassword;
import com.rainer.veebipood.dto.PersonDTO;
import com.rainer.veebipood.entity.Person;
import com.rainer.veebipood.repository.PersonRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.*;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Value("${auth.secret-key}")
    private String secretKey;

    // AuthToken
    public AuthToken signUp(Person person) {
        if (person.getFirstName().length() < 2 || person.getFirstName().length() > 20) {
            throw new RuntimeException("FIRST_NAME_LENGTH_ERROR");
        }

        if (person.getLastName().length() < 2 || person.getLastName().length() > 20) {
            throw new RuntimeException("LAST_NAME_LENGTH_ERROR");
        }

        if (person.getPassword().length() < 6 || person.getPassword().length() > 20) {
            throw new RuntimeException("PASSWORD_LENGTH_ERROR");
        }

        if (person.getEmail().length() < 6 || person.getEmail().length() > 40) {
            throw new RuntimeException("EMAIL_LENGTH_ERROR");
        }

        Optional<Person> optionalPersonEmail = personRepository.findById(person.getEmail());
        if (optionalPersonEmail.isPresent()) {
            throw new RuntimeException("EMAIL_EXISTS_ERROR");
        };

        person.setAdmin(false);

        personRepository.save(person);
        AuthToken token = new AuthToken();
        token.setToken(person.getEmail());
        token.setExpiration(new Date());

        return token;
    }

    public AuthToken login(EmailPassword emailPassword) {
        Optional<Person> optionalPerson = personRepository.findById(emailPassword.getEmail());
        if (optionalPerson.isEmpty()) {
            throw new RuntimeException("EMAIL_WRONG_ERROR");
        }
        if (!optionalPerson.get().getPassword().equals(emailPassword.getPassword())) {
            throw new RuntimeException("PASSWORD_WRONG_ERROR");
        }
//        AuthToken token = new AuthToken();
//        Person person = optionalPerson.get();
//
//        token.setToken(generateToken(person));
//        token.setExpiration(new Date());
//        token.setAdmin(person.isAdmin());
        return generateToken(optionalPerson.get());
    }

    private AuthToken generateToken(Person person) {
        Date expiration = new Date((new Date()).getTime() + 3 * 60 * 60 * 1000);
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode((secretKey)));

        Map<String, String> claims = new HashMap<>();
        claims.put("email", person.getEmail());
        claims.put("admin", String.valueOf(person.isAdmin()));

        String token = Jwts.builder()
                .expiration(expiration)
                .signWith(key) // ** tokeni valideerimine (kas on meie token)
                .claims(claims) // tokeni küljes olevad andme
                .compact();

        AuthToken authToken = new AuthToken();
        authToken.setToken(token);
        authToken.setExpiration(expiration); // expiration aeg
        authToken.setAdmin(person.isAdmin());
        return authToken;
    }


    public List<Person> returnAllPerson() {
        return personRepository.findAll();
    }

    public Person getPerson() {
        String id = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        return personRepository.findById(id).orElseThrow();
    }

    public Person editPerson(Person person) {
        Optional<Person> optionalPerson = personRepository.findById(person.getEmail());
        if (optionalPerson.isEmpty()) {
            throw new RuntimeException("E-maili ei saa muuta kuna sellist e-maili pole");
        }
        if (person.getPassword().length() < 5) {
            throw new RuntimeException("Parool on liiga lühike");
        }

        if (person.getFirstName().length() < 2) {
            throw new RuntimeException("Eesnimi on liiga lühike");
        }
        if (person.getLastName().length() < 2) {
            throw new RuntimeException("Perekonnanimi on liiga lühike");
        }


        return personRepository.save(person);
    }

//    public List<PersonDTO> getPublicPersons() {
//        List<Person> persons = personRepository.findAll();
//        List<PersonDTO> personDTOs = new ArrayList<>();
//        for (Person p: persons) {
//            PersonDTO newPersonDTO = new PersonDTO();
//            newPersonDTO.setFirstName(p.getFirstName());
//            newPersonDTO.setLastName(p.getLastName());
//            newPersonDTO.setEmail(p.getEmail());
//            personDTOs.add(newPersonDTO);
//        }
//
//        return personDTOs;
//    }

    public List<PersonDTO> getPublicPersons() {
        //ModelMapper modelMapper = new ModelMapper(); <-- @Autowired asemel

        System.out.println(modelMapper);
        return Arrays.asList(modelMapper.map(personRepository.findAll(), PersonDTO[].class));
    }
}
