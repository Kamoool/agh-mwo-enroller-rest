package com.company.enroller.controllers;

import java.util.Collection;

import com.company.enroller.model.Meeting;
import com.company.enroller.persistence.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.company.enroller.model.Participant;
import com.company.enroller.persistence.ParticipantService;

@RestController
@RequestMapping("/participants")
public class ParticipantRestController {

    @Autowired
    ParticipantService participantService;

    @Autowired
    MeetingService meetingService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    // GET http://localhost:8080/participants
    public ResponseEntity<?> getParticipants() {
        Collection<Participant> participants = participantService.getAll();
        return new ResponseEntity<Collection<Participant>>(participants, HttpStatus.OK);
    }

    // GET http://localhost:8080/participants/user2
    @RequestMapping(value = "/{id}", method = RequestMethod.GET) //zmienna id
    public ResponseEntity<?> getParticipant(@PathVariable("id") String login) { //tutaj zmienna wstrzykiwania tutaj
        Participant participant = participantService.findByLogin(login);
        if (participant == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Participant>(participant, HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.POST) //wymaga metody POST, na ścieżce participants
    public ResponseEntity<?> registerParticipant(@RequestBody Participant participant) {
        if (participantService.findByLogin(participant.getLogin()) != null)
            return new ResponseEntity("Unable to create. A participant with login " + participant.getLogin() + " already exist.", HttpStatus.CONFLICT);//zwraca tekst i status

        participantService.add(participant);
        return new ResponseEntity<Participant>(participant, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE) //zmienna id
    public ResponseEntity<?> deleteParticipant(@PathVariable("id") String login) { //tutaj zmienna wstrzykiwania tutaj
        Participant participant = participantService.findByLogin(login);
        if (participant == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        participantService.delete(participant);
        return new ResponseEntity<Participant>(participant, HttpStatus.OK);
//        return new ResponseEntity<Participant>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateParticipant(@PathVariable("id") String login, @RequestBody Participant updatedParticipant) {
        Participant foundParticipant = participantService.findByLogin(login);
        if (foundParticipant == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        foundParticipant.setPassword(updatedParticipant.getPassword());
        participantService.update(foundParticipant);
        return new ResponseEntity<Participant>(foundParticipant, HttpStatus.OK);
    }

}
