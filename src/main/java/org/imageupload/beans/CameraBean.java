package org.imageupload.beans;
import java.io.File;
import java.io.IOException;
import javax.faces.FacesException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.imageio.stream.FileImageOutputStream;
import javax.servlet.ServletContext;

import org.imageupload.service.ImageService;
import org.primefaces.event.CaptureEvent;

@ViewScoped
@ManagedBean(name = "camera")
public class CameraBean {

    private final String title = "Camera";
    private String filename;
    private String author;

    public String getFilename() {
        return filename;
    }
    public String getTitle() {
        return title;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void oncapture(CaptureEvent captureEvent) {
        byte[] data = captureEvent.getData();

        ImageService imageService = new ImageService();
        imageService.postImage(author, filename, data);

        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String newFileName = servletContext.getRealPath("") + File.separator + "resources" + File.separator + "images" +
                 File.separator + filename + ".jpeg";

        FileImageOutputStream imageOutput;
        try {
            imageOutput = new FileImageOutputStream(new File(newFileName));
            imageOutput.write(data, 0, data.length);
            imageOutput.close();
        }
        catch(IOException e) {
            throw new FacesException("Error in writing captured image.", e);
        }
    }
}