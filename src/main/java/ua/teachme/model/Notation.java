package ua.teachme.model;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "notations")
public class Notation extends EntityName{

    //todo set annotations to correct mapping
    @Transient()
    private User user;

    @Column(name = "url")
    @NotEmpty
    @URL
    private String url;

    @Column(name = "description")
    @NotEmpty
    private String description;

    @Column(name = "hours")
    @NotEmpty
    private int hours;

    @Column(name = "created_date_and_time")
    private LocalDateTime createdDateAndTime;

    public Notation(){
        super();
    }

    public Notation(Notation notation){
        this(notation.getName(), notation.getUrl(), notation.getDescription(), notation.getHours(), notation.getCreatedDateAndTime());
    }

    public Notation(String name, String url, String description, int hours, LocalDateTime createdDateAndTime){
        this(null, name, url, description, hours, createdDateAndTime);
    }

    public Notation(Integer id, String name, String url, String description, int hours, LocalDateTime createdDateAndTime) {
        super(id, name);
        this.url = url;
        this.description = description;
        this.hours = hours;
        this.createdDateAndTime = createdDateAndTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public LocalDateTime getCreatedDateAndTime() {
        return createdDateAndTime;
    }

    public void setCreatedDateAndTime(LocalDateTime createdDateAndTime) {
        this.createdDateAndTime = createdDateAndTime;
    }

    public LocalDate getDate(){
        return createdDateAndTime.toLocalDate();
    }

    public LocalTime getTime(){
        return createdDateAndTime.toLocalTime();
    }

    @Override
    public String toString() {
        return "Notation{" +
                "name=" + name +
                ", url='" + url + '\'' +
                ", description='" + description + '\'' +
                ", hours=" + hours +
                ", createdDateAndTime=" + createdDateAndTime +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Notation)) return false;

        Notation notation = (Notation) o;

        return url.equals(notation.url);

    }

    @Override
    public int hashCode() {
        return url.hashCode();
    }
}
