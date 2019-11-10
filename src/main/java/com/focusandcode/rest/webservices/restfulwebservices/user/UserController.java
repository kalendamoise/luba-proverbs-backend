package com.focusandcode.rest.webservices.restfulwebservices.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.LinkRelation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.methodOn;

@RestController
public class UserController {

    @Autowired
    private UserDaoService usersDaoService;

    @GetMapping(path = "/users")
    public List<User> retrieveAllUsers() {
        List<User> users = this.usersDaoService.findAll();
        if (null == users || users.size() == 0) {
            throw new UserNotFoundException("There are no users in the system");
        }
        return users;

    }


    @GetMapping(path = "/users/{id}")
    public EntityModel<User> retrieveUser(@PathVariable Integer id) {
        User user = this.usersDaoService.findOne(id);

        EntityModel<User> model = new EntityModel(user);
        model.add(linkTo(methodOn(UserController.class).retrieveAllUsers()).withRel(LinkRelation.of("all-users")));
        model.add(linkTo(methodOn(UserController.class).retrieveUser(id)).withSelfRel());
        return model;

    }

    @PostMapping(path = "/users")
    public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {
        User newUser = this.usersDaoService.save(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newUser.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping(path = "/users/{id}")
    public void deleteUser(@PathVariable Integer id) {
        this.usersDaoService.delete(id);
    }
}