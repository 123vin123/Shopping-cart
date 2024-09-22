package com.dailycodework.dream_shops.service.image;

import com.dailycodework.dream_shops.dto.ImageDto;
import com.dailycodework.dream_shops.model.Image;
import com.dailycodework.dream_shops.model.Product;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IImageService {

    Image getImageById(Long id);

    List<ImageDto> saveImages(List<MultipartFile> files , Long productId);
    void deleteImageById(Long id);
    void updateImage( MultipartFile file ,Long imageId);
}
