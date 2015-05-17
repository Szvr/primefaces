package model;

/**
 * @author Paul Silaghi
 */
public class ImageVO {

    private String author;

    private String name;

    private Byte[] file;



    public ImageVO(String author, String name) {
        this.author = author;
        this.name = name;
    }

    public ImageVO(String author, String name, Byte[] file) {
        this.author = author;
        this.name = name;
        this.file = file;
    }

    public Byte[] getFile() {
        return file;
    }

    public void setFile(Byte[] file) {
        this.file = file;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
