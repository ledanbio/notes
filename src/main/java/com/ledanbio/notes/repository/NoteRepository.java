package com.ledanbio.notes.repository;

import com.ledanbio.notes.model.Note;
import com.ledanbio.notes.model.Users.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NoteRepository
        extends JpaRepository<Note,Integer> {
    List<Note> findByUser(User user);
    Optional<Note> findByIdAndUser_Id(Integer id, Integer userId);
    boolean existsByTitle(String title);
}
