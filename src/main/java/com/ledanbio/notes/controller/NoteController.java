package com.ledanbio.notes.controller;


import com.ledanbio.notes.model.Note;
import com.ledanbio.notes.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class NoteController {
    private final NoteService noteService;

    @Autowired
    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @RequestMapping("/")
    public String getHome(){
        return "redirect:/note";
    }

    @GetMapping(value = "/note")
    public String getList(Model model){
        List<Note> Notes = noteService.getNotes();
        model.addAttribute("notes", Notes);
        return "notes";
    }

    @PostMapping(value = "/note/create")
    public String addNote(
            @RequestParam String title,
            @RequestParam String note,
            Model model){
        String status = noteService.addNote(title,note);

        return "redirect:/note";
    }
}
