package org.wildcodeschool.myblog.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.wildcodeschool.myblog.dto.AuthorDTO;
import org.wildcodeschool.myblog.dto.ArticleAuthorDTO;
import org.wildcodeschool.myblog.model.Author;
import org.wildcodeschool.myblog.repository.AuthorRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/authors")
public class AuthorController {
    private final AuthorRepository authorRepository;

    public AuthorController(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    private AuthorDTO convertToDTO(Author author) {
        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setId(author.getId());
        authorDTO.setFirstname(author.getFirstname());
        authorDTO.setLastname(author.getLastname());
        authorDTO.setArticleAuthors(author.getArticleAuthors().stream().map(articleAuthor -> {
            ArticleAuthorDTO articleAuthorDTO = new ArticleAuthorDTO();
            articleAuthorDTO.setArticleId(articleAuthor.getArticle().getId());
            articleAuthorDTO.setAuthorId(articleAuthor.getAuthor().getId());
            articleAuthorDTO.setContribution(articleAuthor.getContribution());
            return articleAuthorDTO;
        }).collect(Collectors.toList()));
        return authorDTO;
    }

    @GetMapping
    public ResponseEntity<List<AuthorDTO>> getAllAuthors() {
        List<Author> authors = authorRepository.findAll();
        List<AuthorDTO> authorDTOs = authors.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(authorDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorDTO> getAuthorById(@PathVariable Long id) {
        Author author = authorRepository.findById(id).orElse(null);
        if (author != null) {
            return ResponseEntity.ok(convertToDTO(author));
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping
    public ResponseEntity<AuthorDTO> createAuthor(@RequestBody Author author) {
        Author createdAuthor = authorRepository.save(author);
        AuthorDTO authorDTO = convertToDTO(createdAuthor);

        return ResponseEntity.status(HttpStatus.CREATED).body(authorDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AuthorDTO> updateAuthor(@PathVariable Long id, @RequestBody Author author) {
        // Vérifier si l'auteur existe dans la base de données
        Optional<Author> existingAuthor = authorRepository.findById(id);
        if (existingAuthor.isPresent()) {
            Author existing = existingAuthor.get();
            // Mettre à jour les informations de l'auteur avec celles envoyées
            existing.setFirstname(author.getFirstname());
            existing.setLastname(author.getLastname());

            // Sauvegarder les modifications dans la base de données
            Author updatedAuthor = authorRepository.save(existing);

            // Convertir l'entité mise à jour en DTO et renvoyer la réponse
            return ResponseEntity.ok(convertToDTO(updatedAuthor));
        } else {
            // Si l'auteur n'existe pas, renvoyer une réponse 404
            return ResponseEntity.notFound().build();
        }
    }

}