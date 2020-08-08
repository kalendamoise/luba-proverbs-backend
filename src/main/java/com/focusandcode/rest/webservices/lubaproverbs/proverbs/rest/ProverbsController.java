package com.focusandcode.rest.webservices.lubaproverbs.proverbs.rest;

import com.focusandcode.rest.webservices.lubaproverbs.proverbs.domain.Category;
import com.focusandcode.rest.webservices.lubaproverbs.proverbs.domain.Proverb;
import com.focusandcode.rest.webservices.lubaproverbs.proverbs.exceptions.ProverbNotFoundException;
import com.focusandcode.rest.webservices.lubaproverbs.proverbs.services.CategoryDaoService;
import com.focusandcode.rest.webservices.lubaproverbs.proverbs.exceptions.CategoryNotFoundException;
import com.focusandcode.rest.webservices.lubaproverbs.proverbs.services.ProverbDaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.LinkRelation;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
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
public class ProverbsController {

    private ProverbDaoService proverbsDaoService;

    private CategoryDaoService categoryDaoService;

    @Autowired
    public ProverbsController(ProverbDaoService proverbsDaoService, CategoryDaoService categoryDaoService) {
        Assert.notNull(proverbsDaoService, "The Prover service cannot be null");
        Assert.notNull(categoryDaoService, "The Category service cannnot be null");
        this.proverbsDaoService = proverbsDaoService;
        this.categoryDaoService = categoryDaoService;
    }

    @GetMapping(path = "/proverbs")
    public CollectionModel<EntityModel<Proverb>> retrieveAllProverbs() {
        List<Proverb> proverbs = this.proverbsDaoService.findAll();
        if (null == proverbs || proverbs.size() == 0) {
            throw new ProverbNotFoundException("There are no proverbs in the system");
        }

        List<EntityModel<Proverb>> myProverbs = StreamSupport.stream(proverbs.spliterator(), false)
                .map(proverb -> {
                    EntityModel<Proverb> model = new EntityModel<Proverb>(proverb,
                            linkTo(methodOn(ProverbsController.class).retrieveProverb(proverb.getProverbId())).withSelfRel());
                    for(Category category : proverb.getCategories()) {
                        model.add(linkTo(methodOn(ProverbsController.class).retrieveCategory(category.getCategoryId())).withRel("/categories"));
                    }
                    return model;
                }).collect(Collectors.toList());


        return new CollectionModel<>(myProverbs,
                linkTo(methodOn(ProverbsController.class).retrieveAllProverbs()).withSelfRel());

    }


    @GetMapping(path = "/proverbs/{id}")
    public EntityModel<Proverb> retrieveProverb(@PathVariable String id) {
        Proverb proverb = this.proverbsDaoService.findOne(id);

        EntityModel<Proverb> model = new EntityModel(proverb);
        model.add(linkTo(methodOn(ProverbsController.class).retrieveProverb(id)).withSelfRel());
        model.add(linkTo(methodOn(ProverbsController.class).retrieveAllProverbs()).withRel(LinkRelation.of("all-Proverbs")));
        return model;

    }

    @PostMapping(path = "/proverbs")
    public ResponseEntity<Object> createProverb(@Valid @RequestBody Proverb proverb) {
        Proverb newProverb = this.proverbsDaoService.save(proverb);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newProverb.getProverbId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping(path = "/proverbs/{id}")
    public void deleteProverb(@PathVariable String id) {
        this.proverbsDaoService.delete(id);
    }

    @GetMapping(path = "/categories")
    public CollectionModel<EntityModel<Category>> retrieveAllCategories() {
        List<Category> categories = this.categoryDaoService.findAll();
        if (null == categories || categories.size() == 0) {
            throw new CategoryNotFoundException("There are no category in the system");
        }

        List<EntityModel<Category>> myCategories = StreamSupport.stream(categories.spliterator(), false)
                .map(category -> {
                    return new EntityModel<Category>(category,
                            linkTo(methodOn(ProverbsController.class).retrieveCategory(category.getCategoryId())).withSelfRel());
                }).collect(Collectors.toList());

        return new CollectionModel<>(myCategories,
                linkTo(methodOn(ProverbsController.class).retrieveAllCategories()).withSelfRel());

    }

    @GetMapping(path = "categories/{id}")
    public EntityModel<Category> retrieveCategory(@PathVariable String id) {
        Category proverb = this.categoryDaoService.findOne(id);

        EntityModel<Category> model = new EntityModel(proverb);
        model.add(linkTo(methodOn(ProverbsController.class).retrieveCategory(id)).withSelfRel());
        model.add(linkTo(methodOn(ProverbsController.class).retrieveAllProverbs()).withRel(LinkRelation.of("all-categories")));
        return model;
    }

    @DeleteMapping(path = "/categories/{id}")
    public void deleteCategory(@PathVariable String id) {
        this.categoryDaoService.delete(id);
    }


    @PostMapping(path = "/categories")
    public ResponseEntity<Object> createCategory(@Valid @RequestBody Category category) {
        Category newCategory = this.categoryDaoService.save(category);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newCategory.getCategoryId()).toUri();
        return ResponseEntity.created(location).build();
    }
}
