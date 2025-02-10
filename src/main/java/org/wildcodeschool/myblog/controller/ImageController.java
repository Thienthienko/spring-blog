package org.wildcodeschool.myblog.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.wildcodeschool.myblog.dto.ImageDTO;
import org.wildcodeschool.myblog.model.Image;
import org.wildcodeschool.myblog.model.Article;
import org.wildcodeschool.myblog.repository.ImageRepository;
import org.wildcodeschool.myblog.repository.ArticleRepository;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/images")
public class ImageController {
    private final ImageRepository imageRepository;
    private final ArticleRepository articleRepository;

    public ImageController(ImageRepository imageRepository, ArticleRepository articleRepository) {
        this.imageRepository = imageRepository;
        this.articleRepository = articleRepository;
    }

    @GetMapping
    public ResponseEntity<List<ImageDTO>> getAllImages() {
        List<Image> images = imageRepository.findAll();
        if (images.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        List<ImageDTO> imageDTOs = images.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(imageDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ImageDTO> getImageById(@PathVariable Long id) {
        Image image = imageRepository.findById(id).orElse(null);
        if (image == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(convertToDTO(image));
    }

//    @PostMapping
//    public ResponseEntity<ImageDTO> createImage(@RequestBody Image image) {
//        Image savedImage = imageRepository.save(image);
//        return ResponseEntity.status(201).body(convertToDTO(savedImage));
//    }

    // ✅ Associer des articles à une image lors de la création
    @PostMapping
    public ResponseEntity<ImageDTO> createImage(@RequestBody ImageDTO imageDTO) {
        Image image = new Image();
        image.setUrl(imageDTO.getUrl());

        if (imageDTO.getArticleIds() != null && !imageDTO.getArticleIds().isEmpty()) {
            List<Article> articles = articleRepository.findAllById(imageDTO.getArticleIds());
            image.setArticles(articles);
        }

        Image savedImage = imageRepository.save(image);
        return ResponseEntity.status(201).body(convertToDTO(savedImage));
    }

    // ✅ Modifier une image en mettant à jour sa liste d'articles
    @PutMapping("/{id}")
    public ResponseEntity<ImageDTO> updateImage(@PathVariable Long id, @RequestBody ImageDTO imageDTO) {
        Image image = imageRepository.findById(id).orElse(null);
        if (image == null) {
            return ResponseEntity.notFound().build();
        }

        image.setUrl(imageDTO.getUrl());

        if (imageDTO.getArticleIds() != null) {
            List<Article> articles = articleRepository.findAllById(imageDTO.getArticleIds());
            image.setArticles(articles);
        } else {
            image.getArticles().clear(); // Si pas d'articles fournis, on vide la liste
        }

        Image updatedImage = imageRepository.save(image);
        return ResponseEntity.ok(convertToDTO(updatedImage));
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<ImageDTO> updateImage(@PathVariable Long id, @RequestBody Image imageDetails) {
//        Image image = imageRepository.findById(id).orElse(null);
//        if (image == null) {
//            return ResponseEntity.notFound().build();
//        }
//        image.setUrl(imageDetails.getUrl());
//        Image updatedImage = imageRepository.save(image);
//        return ResponseEntity.ok(convertToDTO(updatedImage));
//    }
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteImage(@PathVariable Long id) {
//        Image image = imageRepository.findById(id).orElse(null);
//        if (image == null) {
//            return ResponseEntity.notFound().build();
//        }
//        imageRepository.delete(image);
//        return ResponseEntity.noContent().build();
//    }

    // ✅ Détacher une image des articles avant suppression
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteImage(@PathVariable Long id) {
        Image image = imageRepository.findById(id).orElse(null);
        if (image == null) {
            return ResponseEntity.notFound().build();
        }

        // On enlève l'image des articles avant de la supprimer
        if (image.getArticles() != null && !image.getArticles().isEmpty()) {
            for (Article article : image.getArticles()) {
                article.getImages().remove(image);
            }
            articleRepository.saveAll(image.getArticles()); // On sauvegarde les articles modifiés
        }

        imageRepository.delete(image);
        return ResponseEntity.noContent().build();
    }


    private ImageDTO convertToDTO(Image image) {
        ImageDTO imageDTO = new ImageDTO();
        imageDTO.setId(image.getId());
        imageDTO.setUrl(image.getUrl());
        if (image.getArticles() != null) {
            imageDTO.setArticleIds(image.getArticles().stream().map(Article::getId).collect(Collectors.toList()));
        }
        return imageDTO;
    }
}