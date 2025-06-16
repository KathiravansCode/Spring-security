package com.projects.securenotes.controller;

import com.projects.securenotes.model.Note;
import com.projects.securenotes.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notes")
public class NotesController {

    @Autowired
    private NoteService noteService;

    @PostMapping
    public ResponseEntity<Note> createNote(@RequestBody NoteRequest noteRequest,
                                           @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        System.out.println("USER DETAILS: " + username);
        Note note = noteService.createNoteForUser(username, noteRequest.getContent());
        return ResponseEntity.ok(note);
    }

    @PutMapping("/{noteId}")
    public ResponseEntity<Note> updateNote(@PathVariable Long noteId,
                                           @RequestBody NoteRequest noteRequest,
                                           @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        Note note = noteService.updateNoteForUser(noteId, noteRequest.getContent(), username);
        return ResponseEntity.ok(note);
    }

    @GetMapping
    public ResponseEntity<List<Note>> getUserNotes(@AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        System.out.println("USER DETAILS: " + username);
        List<Note> notes = noteService.getNotes(username);
        return ResponseEntity.ok(notes);
    }

    @DeleteMapping("/{noteId}")
    public ResponseEntity<String> deleteNote(@PathVariable Long noteId,
                                             @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        String result = noteService.deleteNoteForUser(noteId, username);
        return ResponseEntity.ok(result + " for user " + username);
    }

    // Inner class for request body
    public static class NoteRequest {
        private String content;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}