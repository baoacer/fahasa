package com.whilewework.fahasa.mapper;

import com.whilewework.fahasa.dto.ProductDto;
import com.whilewework.fahasa.entity.Product;

public class ProductMapper {

    public static ProductDto toDto(Product product) {
        if (product == null) {
            return null;
        }

        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setTitle(product.getTitle());
        productDto.setPublisher(product.getPublisher());
        productDto.setPublisherBy(product.getPublisherBy());
        productDto.setAuthor(product.getAuthor());
        productDto.setCoverType(product.getCoverType());
        productDto.setPrice(product.getPrice());
        productDto.setOriginalPrice(product.getOriginalPrice());
        productDto.setDiscount(product.getDiscount());
        productDto.setCurrency(product.getCurrency());
        productDto.setPublishingYear(product.getPublishingYear());
        productDto.setSupplier(product.getSupplier());
        productDto.setLanguage(product.getLanguage());
        productDto.setPackagingSize(product.getPackagingSize());
        productDto.setNumberOfPages(product.getNumberOfPages());
        productDto.setHeaderDescription(product.getHeaderDescription());
        productDto.setAboutTheAuthor(product.getAboutTheAuthor());
        productDto.setDescription(product.getDescription());
        productDto.setImg(product.getImg());
        productDto.setCategoryId(product.getCategory().getId());

        return productDto;
    }

    public static Product toEntity(ProductDto productDto) {
        if (productDto == null) {
            return null;
        }

        Product product = new Product();
        product.setId(productDto.getId());
        product.setTitle(productDto.getTitle());
        product.setPublisher(productDto.getPublisher());
        product.setPublisherBy(productDto.getPublisherBy());
        product.setAuthor(productDto.getAuthor());
        product.setCoverType(productDto.getCoverType());
        product.setPrice(productDto.getPrice());
        product.setOriginalPrice(productDto.getOriginalPrice());
        product.setDiscount(productDto.getDiscount());
        product.setCurrency(productDto.getCurrency());
        product.setPublishingYear(productDto.getPublishingYear());
        product.setSupplier(productDto.getSupplier());
        product.setLanguage(productDto.getLanguage());
        product.setPackagingSize(productDto.getPackagingSize());
        product.setNumberOfPages(productDto.getNumberOfPages());
        product.setHeaderDescription(productDto.getHeaderDescription());
        product.setAboutTheAuthor(productDto.getAboutTheAuthor());
        product.setDescription(productDto.getDescription());
        product.setImg(productDto.getImg());

        return product;
    }
}
