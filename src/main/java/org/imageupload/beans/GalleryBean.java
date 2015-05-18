package org.imageupload.beans;

import org.imageupload.model.ImageVO;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.imageupload.service.ImageService;
import org.springframework.context.annotation.Scope;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
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
@ApplicationScoped
@ManagedBean(name = "gallery")
public class GalleryBean {

    private final String title = "Gallery";
    private final List<ImageVO> images;

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
        this.images = imageService.getImages();

    }

    public String getTitle() {
        return title;
    }

    public List<ImageVO> getImages() {
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
       FacesContext context = FacesContext.getCurrentInstance();

        if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
            // So, we're rendering the HTML. Return a stub StreamedContent so that it will generate right URL.
            return new DefaultStreamedContent();
        }
        else {
            // So, browser is requesting the image. Return a real StreamedContent with the image bytes.

            return new DefaultStreamedContent(new ByteArrayInputStream(imageService.getImages().get(1).getFile()));
        }
    }

}
