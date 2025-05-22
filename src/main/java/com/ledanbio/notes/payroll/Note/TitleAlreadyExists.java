package com.ledanbio.notes.payroll.Note;

public class TitleAlreadyExists extends RuntimeException {
    public TitleAlreadyExists(String title) {
        super("Title '" + title + "'  already exists");
    }
}
