package com.makersacademy.acebook.controller;

import com.makersacademy.acebook.model.Like;
import com.makersacademy.acebook.model.Post;
import com.makersacademy.acebook.model.User;
import com.makersacademy.acebook.repository.LikeRepository;
import com.makersacademy.acebook.repository.PostRepository;
import com.makersacademy.acebook.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Controller
public class PostsController {

    public static final String UPLOAD_DIRECTORY = "src/main/resources/static/images";

    @Autowired
    PostRepository repository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    LikeRepository likeRepository;

    @GetMapping("/posts")
    public String index(Model model) {
        Iterable<Post> posts = repository.findAllByOrderByCreatedAtAsc();
        for (Post post: posts){
            post.setLikes(likeRepository.countByPost(post));
        }
        model.addAttribute("posts", posts);
        model.addAttribute("post", new Post());
        return "posts/index";
    }

    @PostMapping("/posts")
    public RedirectView create(@ModelAttribute Post post, Authentication auth, @RequestParam("image") MultipartFile file) throws IOException {
        post.setUser_id(userRepository.findByUsername(auth.getName()).getId());
        if (!file.isEmpty()) {
            StringBuilder fileNames = new StringBuilder("images/");
            Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY, file.getOriginalFilename());
            fileNames.append(file.getOriginalFilename());
            Files.write(fileNameAndPath, file.getBytes());
            post.setPhoto(fileNames.toString());
        }
        else {
            post.setPhoto(null);
        }
        repository.save(post);
        return new RedirectView("/posts");
    }

    @PostMapping("/posts/like")
    public RedirectView likePost(@RequestParam Long postId, Authentication auth) {
        Optional<Post> optionalPost = repository.findById(postId);
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            User user = userRepository.findByUsername(auth.getName());
            List<Like> likes = likeRepository.findLikeByPostIdAndUserId(post.getId(), user.getId());
            if (likes.size() > 0) {
                for (Like like : likes) {
                    likeRepository.deleteById(like.getId());
                }
                return new RedirectView("/posts");
            }
            Like like = new Like(post, user);
            likeRepository.save(like);
            // Increment the like count in the post
//            repository.save(post);
            System.out.println("Hello world");
            System.out.println(likeRepository.countByPost(post));
        }
        return new RedirectView("/posts");
    }
}
