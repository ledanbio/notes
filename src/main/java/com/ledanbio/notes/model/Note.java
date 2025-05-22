package com.ledanbio.notes.model;


import com.ledanbio.notes.model.Users.User;
import jakarta.persistence.*;

@Entity
@Table(name = "notes")
public class Note {
    @Id
    @SequenceGenerator(
            name = "note_sequence",
            sequenceName = "note_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "note_sequence"
    )
    private Integer id;
    private String title;
    private String note;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;


    public Note(String title, String note, User user) {
        this.title = title;
        this.note = note;
        this.user = user;
    }

    public Note(Integer id, String title, String note) {
        this.id = id;
        this.title = title;
        this.note = note;
    }

    public Note(String title, String note) {
        this.title = title;
        this.note = note;
    }

    public Note() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
