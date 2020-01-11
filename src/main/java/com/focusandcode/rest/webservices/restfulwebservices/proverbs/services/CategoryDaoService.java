package com.focusandcode.rest.webservices.restfulwebservices.proverbs.services;

import com.focusandcode.rest.webservices.restfulwebservices.proverbs.domain.Category;
import com.focusandcode.rest.webservices.restfulwebservices.proverbs.exceptions.CategoryNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Component
public class CategoryDaoService {
    private static List<Category> posts = new ArrayList<>();
    private static int postCount = 8;

    {
        posts.add(new Category("1", "Age"));
        posts.add(new Category("2", "Alone"));
        posts.add(new Category("3", "Anger"));
        posts.add(new Category("4", "Anniversary"));
        posts.add(new Category("5", "Architecture"));
        posts.add(new Category("6", "Art Favorite"));
        posts.add(new Category("7", "Attitude"));
        posts.add(new Category("8", "Beauty"));
        posts.add(new Category("9", "Best"));
        posts.add(new Category("10", "Birthday"));
    }

    public static Category save(Category post) {
        post.setId(String.valueOf(++postCount));
        posts.add(post);
        return post;
    }

    public static List<Category> findAll() {
        List<Category> results = new ArrayList<>();

        Consumer<Category> action = entry ->
        {
            results.add(entry);
        };
        posts.forEach(action);
        return  results;
    }

    public static Category findOne(String id) {
        for(Category post : posts) {
            if (id.equals(post.getId())) {
                return post;
            }
        }
        throw new CategoryNotFoundException("id-" + id );
    }

    public static Category delete(String id) {
        Category category = findOne(id);
        boolean remove = posts.remove(category);
        return category;
    }
}
