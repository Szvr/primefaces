package com.mkyong.beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
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

    public GalleryBean() {
        imageExtensions.add(".jpg");
        imageExtensions.add(".jpeg");
        imageExtensions.add(".gif");
        imageExtensions.add(".png");
        imageExtensions.add(".bmp");
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

}
