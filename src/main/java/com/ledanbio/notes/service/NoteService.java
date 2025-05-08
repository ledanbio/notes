package com.ledanbio.notes.service;


import com.ledanbio.notes.model.Note;
import com.ledanbio.notes.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

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

    public Note getNote(Integer id){
        return noteRepository.findById(id)
                .orElseThrow(()-> new IllegalStateException(
                        "note with id " + id + "doesn't exist"));
    }

    public void updateNote(Integer id, String newTitle, String newNote){
        Note note = noteRepository.findById(id)
                .orElseThrow(()-> new IllegalStateException(
                        "note with id " + id + "doesn't exist"));
        if(newTitle!=null &&
                !newTitle.isEmpty() &&
                !Objects.equals(newTitle,note.getTitle())
        ){
            note.setTitle(newTitle);
        }
        if(newNote!=null &&
                !newNote.isEmpty() &&
                !Objects.equals(newNote,note.getNote())
        ) {
            note.setNote(newNote);
        }

        noteRepository.save(note);
    }

    public void deleteNote(Integer id) {
        Note note = noteRepository.findById(id)
                .orElseThrow(()-> new IllegalStateException(
                "note with id " + id + "doesn't exist"));
        noteRepository.delete(note);
    }
}
