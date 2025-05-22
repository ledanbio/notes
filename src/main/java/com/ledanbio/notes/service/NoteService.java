package com.ledanbio.notes.service;


import com.ledanbio.notes.model.Note;
import com.ledanbio.notes.model.Users.User;
import com.ledanbio.notes.payroll.Note.NoteForbiddenAccess;
import com.ledanbio.notes.payroll.Note.NoteNotFound;
import com.ledanbio.notes.payroll.Note.TitleAlreadyExists;
import com.ledanbio.notes.repository.NoteRepository;
import com.ledanbio.notes.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class NoteService {
    @Autowired
    private final NoteRepository noteRepository;

    @Autowired
    private final UserRepository userRepository;

    public NoteService(NoteRepository noteRepository, UserRepository userRepository) {
        this.noteRepository = noteRepository;
        this.userRepository = userRepository;
    }

    public String addNote(String title, String note, String username){
        Optional<User> currentUser = userRepository.findByUsername(username);

        if(currentUser.isEmpty()){
            throw new UsernameNotFoundException("...");
        }

        if(noteRepository.existsByTitle(title)){
            throw new TitleAlreadyExists(title);
        }


        Note newNote = new Note(title, note, currentUser.get());
        noteRepository.save(newNote);
        return "Ok";
    }

    public List<Note> getNotesByUser(String username){
        Optional<User> currentUser = userRepository.findByUsername(username);

        if (currentUser.isEmpty()){
            throw new UsernameNotFoundException("Kakova huya");
        }

        List<Note> notesByUser = noteRepository.findByUser(currentUser.get());

        if(notesByUser.isEmpty()){
            return Collections.emptyList();
        }

        return notesByUser;
    }

    public Note getNoteByUser(Integer id, String username){
        Optional<Note> note =
                noteRepository.findById(id);
        if (note.isEmpty()){
            throw new NoteNotFound("Note with id " + id + " not found");
        }

        if (userRepository.existsByUsername(username) &&
                Objects.equals(username, note.get().getUser().getUsername())){
            return note.get();
        }
        else {
            throw new NoteForbiddenAccess("Forbidden");
        }

    }

    public void updateNote(Integer id,
                           String newTitle,
                           String newNote,
                           String username){

        Optional<Note> note = noteRepository.findById(id);
        if (note.isEmpty()){
            throw new NoteNotFound("Note with id " + id + " is not found ");
        }
        if(userRepository.existsByUsername(username) &&
                Objects.equals(username, note.get().getUser().getUsername())
        ){
            if(newTitle!=null &&
                    !newTitle.isEmpty() &&
                    !Objects.equals(newTitle,note.get().getTitle())
            ){
                note.get().setTitle(newTitle);
            }
            if(newNote!=null &&
                    !newNote.isEmpty() &&
                    !Objects.equals(newNote,note.get().getNote())
            ) {
                note.get().setNote(newNote);
            }

            noteRepository.save(note.get());
        }
        else {
            throw new NoteForbiddenAccess("Forbidden");
        }


    }

    public void deleteNote(Integer id,String username) {

        Optional<Note> note = noteRepository.findById(id);
        if (note.isEmpty()){
            throw new NoteNotFound("Note with " + id + " not found" );
        }

        if(userRepository.existsByUsername(username) &&
                Objects.equals(username,note.get().getUser().getUsername())){
            noteRepository.delete(note.get());
        }
        else{
            throw new NoteForbiddenAccess("Forbidden");
        }
    }
}
