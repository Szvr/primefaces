package org.imageupload.beans;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.imageupload.service.ImageService;
import org.springframework.context.annotation.Scope;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author Csaba Szever
 */
@ViewScoped
@ManagedBean(name = "gallery")
public class GalleryBean {

    private final String title = "Gallery";
    private final List<String> images;

    private List<String> imageExtensions = new ArrayList<String>();

    @Inject
    private ImageService imageService;

    public GalleryBean() {
        imageExtensions.add(".jpg");
        imageExtensions.add(".jpeg");
        imageExtensions.add(".gif");
        imageExtensions.add(".png");
        imageExtensions.add(".bmp");
        imageService = new ImageService();
        this.images = initializeImages();

    }

    public String getTitle() {
        return title;
    }

    public List<String> getImages() {
        return images;
    }

    private List<String> initializeImages() {
        List<String> imageNames = new ArrayList<String>();

        imageService.getImages();

        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String imageFolder = servletContext.getRealPath("") + File.separator + "resources" + File.separator + "images";

        File folder = new File(imageFolder);
        if (folder.isDirectory()) {
            for(File file: folder.listFiles()) {
                String fileExtension = file.getName().substring(file.getName().lastIndexOf("."));
                if (imageExtensions.contains(fileExtension)) {
                    imageNames.add(file.getName());
                }
            }
        }

        return imageNames;
    }

    public StreamedContent getImage() {
       return new DefaultStreamedContent(new ByteArrayInputStream(imageService.getImages().get(3).getFile()), "image/jpeg");
    }

}
