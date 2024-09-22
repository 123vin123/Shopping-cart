package com.dailycodework.dream_shops.service.image;

import com.dailycodework.dream_shops.dto.ImageDto;
import com.dailycodework.dream_shops.exceptions.ResourceNotFounException;
import com.dailycodework.dream_shops.model.Image;
import com.dailycodework.dream_shops.model.Product;
import com.dailycodework.dream_shops.repository.ImageRepository;
import com.dailycodework.dream_shops.service.product.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService implements IImageService{

    private final ImageRepository imageRepository;
    private final IProductService productService;
    @Override
    public Image getImageById(Long id) {
        return imageRepository.findById(id).orElseThrow(() -> new ResourceNotFounException("Image not found with id:" + id));
    }

    @Override
    public List<ImageDto> saveImages(List<MultipartFile> files, Long productId) {

        Product product = productService.getProductById(productId);
        List<ImageDto>savedImageDto = new ArrayList<>();
        try {
            for (MultipartFile file : files) {
                Image image = new Image();
                image.setFileName(file.getOriginalFilename());
                image.setImage(new SerialBlob(file.getBytes()));
                image.setProduct(product);
                String buildDownloadUrl = "/api/v1/images/image/download/";
                String downloadUrl = "/api/v1/images/image/download/" + image.getId();
                image.setDownloadUrl(downloadUrl);
                Image savedImage = imageRepository.save(image);
                savedImage.setDownloadUrl("/api/v1/images/image/download/"  + savedImage.getId());
                imageRepository.save(savedImage);
                ImageDto imageDto = new ImageDto();
                imageDto.setId(savedImage.getId());
                imageDto.setFileName(savedImage.getFileName());
                imageDto.setDownloadUrl(savedImage.getDownloadUrl());
                savedImageDto.add(imageDto);
            }
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
        return savedImageDto;
    }

    @Override
    public void deleteImageById(Long id) {
      imageRepository.findById(id).ifPresentOrElse(imageRepository:: delete, () -> {
          throw new ResourceNotFounException("Image not found with id:" + id);
      });
    }

    @Override
    public void updateImage(MultipartFile file, Long imageId) {
            Image image = getImageById(imageId);
        try {
            image.setFileName(file.getOriginalFilename());
            image.setFileType(file.getContentType());
            image.setImage(new SerialBlob(file.getBytes()));
            imageRepository.save(image);
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
