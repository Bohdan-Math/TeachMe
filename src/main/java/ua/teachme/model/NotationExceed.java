package ua.teachme.model;

import java.time.LocalDateTime;

public class NotationExceed {

    protected final String name;
    protected final String url;
    protected final String description;
    protected final int hours;
    protected final LocalDateTime dateTime;
    protected final boolean exceed;

    public NotationExceed(String name, String url, String description, int hours, LocalDateTime dateTime, boolean exceed) {
        this.name = name;
        this.url = url;
        this.description = description;
        this.hours = hours;
        this.dateTime = dateTime;
        this.exceed = exceed;
    }

    public NotationExceed(Notation notation, boolean exceed){
        this.name = notation.getName();
        this.url = notation.getUrl();
        this.description = notation.getDescription();
        this.hours = notation.getHours();
        this.dateTime = notation.getDateTime();
        this.exceed = exceed;
    }

    @Override
    public String toString() {
        return "NotationExceed{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", description='" + description + '\'' +
                ", hours=" + hours +
                ", dateTime=" + dateTime +
                ", exceed=" + exceed +
                '}';
    }
}
