package com.focusandcode.rest.webservices.lubaproverbs.user;

import com.focusandcode.rest.webservices.lubaproverbs.user.exceptions.PostNotFoundException;
import com.focusandcode.rest.webservices.lubaproverbs.user.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.LinkRelation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.methodOn;

@RestController
public class UserController {

    @Autowired
    private UserDaoService usersDaoService;

    @Autowired
    private PostDaoService postDaoService;

    @GetMapping(path = "/users")
    public CollectionModel<EntityModel<User>> retrieveAllUsers() {
        List<User> users = this.usersDaoService.findAll();
        if (null == users || users.size() == 0) {
            throw new UserNotFoundException("There are no users in the system");
        }

        List<EntityModel<User>> myUsers = StreamSupport.stream(users.spliterator(), false)
                .map(user -> {
                    return new EntityModel<User>(user,
                            linkTo(methodOn(UserController.class).retrieveUser(user.getId())).withSelfRel(),
                            linkTo(methodOn(UserController.class).retrievePostsForUser(user.getId())).withRel(LinkRelation.of("all-users-posts")));
                }).collect(Collectors.toList());


        return new CollectionModel<>(myUsers,
                linkTo(methodOn(UserController.class).retrieveAllUsers()).withSelfRel());

    }


    @GetMapping(path = "/users/{id}")
    public EntityModel<User> retrieveUser(@PathVariable Integer id) {
        User user = this.usersDaoService.findOne(id);

        EntityModel<User> model = new EntityModel(user);
        model.add(linkTo(methodOn(UserController.class).retrieveUser(id)).withSelfRel());
        model.add(linkTo(methodOn(UserController.class).retrievePostsForUser(id)).withRel(LinkRelation.of("all-users-posts")));
        model.add(linkTo(methodOn(UserController.class).retrieveAllUsers()).withRel(LinkRelation.of("all-users")));
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

    @GetMapping(path = "/users/{id}/posts")
    public CollectionModel<EntityModel<Post>> retrievePostsForUser(@PathVariable Integer id) {
        List<Post> posts = this.postDaoService.findAll(id);
        if (null == posts || posts.size() == 0) {
            throw new PostNotFoundException("There are no posts in the system for user with id: " + id);
        }

        List<EntityModel<Post>> myUsers = StreamSupport.stream(posts.spliterator(), false)
                .map(post -> {
                    return new EntityModel<Post>(post,
                            linkTo(methodOn(UserController.class).retrievePost(post.getUserId(), post.getId())).withSelfRel(),
                            linkTo(methodOn(UserController.class).retrieveUser(post.getUserId())).withRel(LinkRelation.of("user")));
                }).collect(Collectors.toList());


        return new CollectionModel<>(myUsers,
                linkTo(methodOn(UserController.class).retrievePostsForUser(id)).withSelfRel());


    }

    @GetMapping(path = "/users/{id}/posts/{post_id}")
    public EntityModel<Post> retrievePost(@PathVariable Integer id, @PathVariable Integer post_id) {
        Post user = this.postDaoService.findOne(post_id, id);

        EntityModel<Post> model = new EntityModel(user);
        model.add(linkTo(methodOn(UserController.class).retrievePost(id, post_id)).withSelfRel());
        model.add(linkTo(methodOn(UserController.class).retrieveAllUsers()).withRel(LinkRelation.of("all-users")));
        return model;
    }

    @PostMapping(path = "/users/{id}/posts")
    public ResponseEntity<Object> createPost(@PathVariable Integer id, @Valid @RequestBody Post post) {
        Post newPost = this.postDaoService.save(post);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{post_id}").buildAndExpand(newPost.getId()).toUri();
        return ResponseEntity.created(location).build();
    }
}
