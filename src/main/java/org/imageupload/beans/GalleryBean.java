package org.imageupload.beans;

import org.imageupload.model.ImageVO;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.imageupload.service.ImageService;
import org.springframework.context.annotation.Scope;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
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
@RequestScoped
@ManagedBean(name = "gallery")
public class GalleryBean {

    private final String title = "Gallery";
    private final List<ImageVO> images;

    @Inject
    private ImageService imageService;

    public GalleryBean() {
        imageService = new ImageService();
        this.images = imageService.getImages();
    }

    public String getTitle() {
        return title;
    }

    public List<ImageVO> getImages() {
        return images;
    }

    public StreamedContent getImage() {
       FacesContext context = FacesContext.getCurrentInstance();

        if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
            // So, we're rendering the HTML. Return a stub StreamedContent so that it will generate right URL.
            return new DefaultStreamedContent();
        }
        else {
            // So, browser is requesting the image. Return a real StreamedContent with the image bytes.
            String id = context.getExternalContext().getRequestParameterMap().get("id");
            ImageVO image = getImageById(Integer.parseInt(id));
            return new DefaultStreamedContent(new ByteArrayInputStream(image.getFile()));
        }
    }

    private ImageVO getImageById(int id) {
        for (ImageVO imageVO: images) {
            if (imageVO.getId() == id) {
                return imageVO;
            }
        }
        return null;
    }

}
