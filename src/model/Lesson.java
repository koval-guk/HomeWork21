package model;

import java.util.Date;

public class Lesson {
    private Integer id;
    private String name;
    private Date updateAt;
    private Homework homework;

    public Lesson(String name, Homework homework) {
        this.name = name;
        this.homework = homework;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public Homework getHomework() {
        return homework;
    }

    public void setHomework(Homework homework) {
        this.homework = homework;
    }

    @Override
    public String toString() {
        return "Lesson{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", updateAt=" + updateAt +
                ", homework=" + homework +
                '}' + "\n";
    }
}
