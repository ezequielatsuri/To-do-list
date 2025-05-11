/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package datos;

/**
 *
 * @author ezequ
 */
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Task implements Serializable {
    private String title;
    private String description;
    private List<Tag> tags;
    private String encargado; 

    public Task() {
        this.tags = new ArrayList<>();
    }

    public Task(String title,String description) {
        this.title = title;
        this.description = description;
        this.tags = new ArrayList<>();
         this.encargado = "";
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public void addTag(Tag tag) {
        this.tags.add(tag);
    }
    public String getEncargado() {
    return encargado;
}

public void setEncargado(String encargado) {
    this.encargado = encargado;
}

    @Override
    public String toString() {
        return "Task{" +
               "description='" + description + '\'' +
               ", tags=" + tags +
               '}';
    }
}