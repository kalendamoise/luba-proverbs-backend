package com.focusandcode.rest.webservices.restfulwebservices.user;

import com.focusandcode.rest.webservices.restfulwebservices.user.exceptions.PostAlreadyExistException;
import com.focusandcode.rest.webservices.restfulwebservices.user.exceptions.PostNotFoundException;
import com.focusandcode.rest.webservices.restfulwebservices.user.exceptions.UserAlreadyExistException;
import com.focusandcode.rest.webservices.restfulwebservices.user.exceptions.UserNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class PostDaoService {
    private static List<Post> posts = new ArrayList<>();
    private static int postCount = 8;

    {
        posts.add(new Post(1, 1, "I love what I am seeing", new Date()));
        posts.add(new Post(2, 1, "Coding past midnight and listening to music is awesome", new Date()));

        posts.add(new Post(3, 2, "Live is awesome, and I am glad to be alive",  new Date()));
        posts.add(new Post(4, 3, "I think I might actually do animation",  new Date()));
        posts.add(new Post(5, 4, "I think Old American girl dolls are better and American girl dolls",  new Date()));
        posts.add(new Post(6, 5, "Spider Man is better than Black Panther",  new Date()));
        posts.add(new Post(7, 5, "Iron Man is better than most",  new Date()));
        posts.add(new Post(8, 5, "Dad that's bad, I think that even you should not be watching that.",  new Date()));
    }

    public static Post save(Post post) {
        try{
            // check if the user exist
            User user = UserDaoService.findOne(post.getUserId());
            Post oldPost = findOne(post.getId(), post.getUserId());
            throw new PostAlreadyExistException(String.format("Post with id-%s already exist", oldPost.getId()));
        } catch (UserNotFoundException ue) {
            throw new UserNotFoundException("There are no user with id-" + post.getUserId() + " to link the post to.");
        } catch (PostNotFoundException pe) {
            post.setId(++postCount);
            posts.add(post);
        }
        return post;
    }

    public static List<Post> findAll(Integer userId) {
        List<Post> results = new ArrayList<>();
        for(Post post : posts) {
            if (post.getUserId() == userId) {
                results.add(post);
            }
        }
        return  results;
    }

    public static Post findOne(Integer id, Integer userId) {
        for(Post post : posts) {
            if (post.getId() == id && post.getUserId() == userId) {
                return post;
            }
        }
        throw new PostNotFoundException("id-" + id + " userId-" + userId);
    }

    public static Post delete(Integer id, Integer userId) {
        Post deletedUser = findOne(id, userId);
        boolean remove = posts.remove(deletedUser);
        return deletedUser;
    }
}
