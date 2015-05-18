package org.imageupload.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.imageupload.model.ImageVO;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.hal.Jackson2HalModule;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @Author Csaba Szever
 */
@Service
public class ImageService {
    String repoUrlForGet = "http://192.168.1.127:{port}/images?page={page}&size={size}";
    String repoUrlForPost = "http://192.168.1.127:8080/images";

    public List<ImageVO> getImages() {
        RestTemplate restTemplate = restTemplate();
        ResponseEntity<PagedResources<ImageVO>> responseEntity = restTemplate.exchange(
                repoUrlForGet, HttpMethod.GET, null,
                new ParameterizedTypeReference<PagedResources<ImageVO>>() {},
                8080, 0, 100);
        PagedResources<ImageVO> resources = responseEntity.getBody();
        List<ImageVO> imageVOs = new ArrayList(resources.getContent());
        List<ImageVO> indexedImageVOs = indexImages(imageVOs);

        return indexedImageVOs;
    }

    public void postImage(String author, String fileName, byte[] image) {
        ImageVO imageVO = new ImageVO(author, fileName, image);
        RestTemplate restTemplate = restTemplate();
        restTemplate.postForLocation(repoUrlForPost, imageVO);
    }

    private RestTemplate restTemplate() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.registerModule(new Jackson2HalModule());

        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(MediaType.parseMediaTypes("application/hal+json"));
        converter.setObjectMapper(mapper);

        RestTemplate template = new RestTemplate(Collections.<HttpMessageConverter<?>> singletonList(converter));
        return template;
    }

    private List<ImageVO> indexImages(List<ImageVO> imageVOs) {
        List<ImageVO> indexedImages = new ArrayList<ImageVO>(imageVOs.size());
        int index = 0;

        for (ImageVO imageVO: imageVOs) {
            imageVO.setId(index);
            indexedImages.add(imageVO);
            index++;
        }

        return indexedImages;
    }

}
