package org.imageupload.model;

/**
 * @author Paul Silaghi
 */
public class ImageVO {

    private String author;

    private String name;

    private int id;

    private byte[] file;


    public ImageVO() {

    }

    public ImageVO(String author, String name) {
        this.author = author;
        this.name = name;
    }

    public ImageVO(String author, String name, byte[] file) {
        this.author = author;
        this.name = name;
        this.file = file;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
