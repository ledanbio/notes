package com.ledanbio.notes.controller;


import com.ledanbio.notes.model.Note;
import com.ledanbio.notes.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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
    public String getList(
            @AuthenticationPrincipal UserDetails userDetails,
            Model model
    ){
        List<Note> Notes = noteService.getNotesByUser(userDetails.getUsername());
        model.addAttribute("notes", Notes);
        return "listNotes";
    }

    @PostMapping(value = "/create")
    public String addNote(
            @RequestParam String title,
            @RequestParam String note,
            @AuthenticationPrincipal UserDetails userDetails,
            Model model){
        String status;
        status = noteService.addNote(title,note,userDetails.getUsername());

        return "redirect:/note";
    }

    @GetMapping("/{id}")
    public String getNote(
        @PathVariable Integer id,
        @AuthenticationPrincipal UserDetails userDetails,
        Model model
    ){
        Note note = noteService.getNoteByUser(id, userDetails.getUsername());
        model.addAttribute("note", note);
        return "note";
    }

    @PutMapping(value = "/{id}")
    public String editNote(
            @PathVariable Integer id,
            @RequestParam String newTitle,
            @RequestParam String newNote,
            @AuthenticationPrincipal UserDetails userDetails
    ){
        noteService.updateNote(id,newTitle,newNote, userDetails.getUsername());

        return "redirect:/note/{id}";
    }

    @DeleteMapping(value = "/{id}")
    public String deleteNote(
            @PathVariable Integer id,
            @AuthenticationPrincipal UserDetails userDetails
    ){
        noteService.deleteNote(id, userDetails.getUsername());
        return "redirect:/note";
    }
}
