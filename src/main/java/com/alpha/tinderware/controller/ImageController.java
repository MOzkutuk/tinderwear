//package com.alpha.tinderware.controller;
//
//import org.springframework.core.io.Resource;
//import org.springframework.core.io.UrlResource;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.nio.file.Path;
//import java.nio.file.Paths;
//
//@RestController
//public class ImageController {
//
//    private final String IMAGES_DIRECTORY = "images";  // Adjust this path to your images directory
//
//    @GetMapping("/image/{imageName:.+}")
//    public ResponseEntity<Resource> getImage(@PathVariable String imageName) {
//
//        try {
//            Path imagePath = Paths.get(IMAGES_DIRECTORY, imageName).normalize();
//            Resource resource = new UrlResource(imagePath.toUri());
//
//            if (resource.exists() && resource.isReadable()) {
//                return ResponseEntity.ok().body(resource);
//            } else {
//                return ResponseEntity.notFound().build();
//            }
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().build();
//        }
//    }
//}