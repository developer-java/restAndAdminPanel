package com.diplomthesis.app.persistence;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "COMENT")
public class Coment {
    @XmlTransient
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;
    @Column(name = "SIGHT_ID")
    private Long sightId;
    @Column(name = "MESSAGE")
    private String message;
    @Column(name = "DATE")
    private String date;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSightId() {
        return sightId;
    }

    public void setSightId(Long sightId) {
        this.sightId = sightId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
