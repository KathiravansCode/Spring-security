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

    public Note createNoteForUser(String ownerUsername,String content){
        Note note=new Note();
        note.setContent(content);
        note.setOwnerUsername(ownerUsername);
        Note createdNote=noteRepository.save(note);
        return createdNote;
    }
    public Note updateNote(Long Id,String content){
        Note note=noteRepository.findById(Id).orElseThrow(()->new RuntimeException("Note not found"));
        note.setContent(content);
        Note updatedNote=noteRepository.save(note);
        return updatedNote;
    }

    public List<Note> getNotes(String ownerUsername){
        List<Note> notesByUser=noteRepository.findByownerUsername(ownerUsername);
        return notesByUser;
    }
    public String deleteNote(Long Id){
        noteRepository.deleteById(Id);
        return "Deleted Successfully";
    }


}

