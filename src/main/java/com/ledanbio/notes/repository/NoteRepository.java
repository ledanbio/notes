package com.ledanbio.notes.repository;

import com.ledanbio.notes.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NoteRepository
        extends JpaRepository<Note,Integer> {



}
