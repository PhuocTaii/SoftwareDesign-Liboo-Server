package com.btv.app.features.image.services;

import com.btv.app.features.image.model.Image;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final ImageRepository imageRepository;

    public void saveImage(Image image){
        this.imageRepository.save(image);
    }
}
