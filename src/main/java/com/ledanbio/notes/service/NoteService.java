package com.ledanbio.notes.service;


import com.ledanbio.notes.model.Note;
import com.ledanbio.notes.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class NoteService {
    private final NoteRepository noteRepository;

    @Autowired
    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public String addNote(String title, String note){
        Note newNote = new Note(title, note);
        noteRepository.save(newNote);
        return "Ok";
    }

    public List<Note> getNotes(){
        return noteRepository.findAll();
    }

}
