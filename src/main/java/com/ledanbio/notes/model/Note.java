package com.ledanbio.notes.model;


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
            generator = "note_generator"
    )
    private Integer id;
    private String title;
    private String note;

    public Note(Integer id, String title,String note) {
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
}
