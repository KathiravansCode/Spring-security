package com.projects.securenotes.service;

import com.projects.securenotes.model.Note;
import com.projects.securenotes.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    @Autowired
    private NoteRepository noteRepository;

    public Note createNoteForUser(String ownerUsername, String content) {
        Note note = new Note();
        note.setContent(content);
        note.setOwnerUsername(ownerUsername);
        return noteRepository.save(note);
    }

    public Note updateNoteForUser(Long id, String content, String username) {
        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Note not found"));

        // Security check: ensure the note belongs to the authenticated user
        if (!note.getOwnerUsername().equals(username)) {
            throw new RuntimeException("Access denied: Note does not belong to the current user");
        }

        note.setContent(content);
        return noteRepository.save(note);
    }

    // Keep the old method for backward compatibility but mark as deprecated
    @Deprecated
    public Note updateNote(Long id, String content) {
        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Note not found"));
        note.setContent(content);
        return noteRepository.save(note);
    }

    public List<Note> getNotes(String ownerUsername) {
        return noteRepository.findByownerUsername(ownerUsername);
    }

    public String deleteNoteForUser(Long id, String username) {
        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Note not found"));

        // Security check: ensure the note belongs to the authenticated user
        if (!note.getOwnerUsername().equals(username)) {
            throw new RuntimeException("Access denied: Note does not belong to the current user");
        }

        noteRepository.delete(note);
        return "Note deleted successfully";
    }

    // Keep the old method for backward compatibility but mark as deprecated
    @Deprecated
    public String deleteNote(Long id) {
        noteRepository.deleteById(id);
        return "Deleted Successfully";
    }
}