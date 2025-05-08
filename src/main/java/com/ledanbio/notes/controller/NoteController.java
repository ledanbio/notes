package com.ledanbio.notes.controller;


import com.ledanbio.notes.model.Note;
import com.ledanbio.notes.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/note")
public class NoteController {
    private final NoteService noteService;

    @Autowired
    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping
    public String getList(Model model){
        List<Note> Notes = noteService.getNotes();
        model.addAttribute("notes", Notes);
        return "listNotes";
    }

    @PostMapping(value = "/create")
    public String addNote(
            @RequestParam String title,
            @RequestParam String note,
            Model model){
        String status = noteService.addNote(title,note);

        return "redirect:/note";
    }

    @GetMapping("/{id}")
    public String getNote(
        @PathVariable Integer id,
        Model model
    ){
        Note note = noteService.getNote(id);
        model.addAttribute("note", note);
        return "note";
    }

    @PutMapping(value = "/{id}")
    public String editNote(
        @RequestParam String newTitle,
        @RequestParam String newNote,
        @PathVariable Integer id
    ){
        noteService.updateNote(id,newTitle,newNote);
        return "redirect:/note/{id}";
    }

    @DeleteMapping(value = "/{id}")
    public String deleteNote(
            @PathVariable Integer id
    ){
        noteService.deleteNote(id);
        return "redirect:/note";
    }
}
