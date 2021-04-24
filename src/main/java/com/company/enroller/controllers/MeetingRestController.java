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
@RequestMapping("/meetings")
public class MeetingRestController {

    @Autowired
    ParticipantService participantService;

    @Autowired
    MeetingService meetingService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    // GET http://localhost:8080/participants
    public ResponseEntity<?> getMeetings() {
        Collection<Meeting> meetings = meetingService.getAll();
        return new ResponseEntity<Collection<Meeting>>(meetings, HttpStatus.OK);
    }

    // GET http://localhost:8080/participants/user2
    @RequestMapping(value = "/{id}", method = RequestMethod.GET) //zmienna id
    public ResponseEntity<?> getMeeting(@PathVariable("id") long id) { //tutaj zmienna wstrzykiwania tutaj
        Meeting meeting = meetingService.findById(id);
        if (meeting == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Meeting>(meeting, HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.POST) //wymaga metody POST, na ścieżce participants
    public ResponseEntity<?> registerMeeting(@RequestBody Meeting meeting) {
        if (meetingService.findById(meeting.getId()) != null)
            return new ResponseEntity("Unable to create. A meeting with login " + meeting.getId() + " already exist.", HttpStatus.CONFLICT);//zwraca tekst i status

        meetingService.add(meeting);
        return new ResponseEntity<Meeting>(meeting, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE) //zmienna id
    public ResponseEntity<?> deleteMeeting(@PathVariable("id") long id) { //tutaj zmienna wstrzykiwania tutaj
        Meeting meeting = meetingService.findById(id);
        if (meeting == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        meetingService.delete(meeting);
        return new ResponseEntity<Meeting>(meeting, HttpStatus.OK);
//        return new ResponseEntity<Participant>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateMeeting(@PathVariable("id") long id, @RequestBody Meeting updatedMeeting) {
        Meeting foundMeeting = meetingService.findById(id);
        if (foundMeeting == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        foundMeeting.setTitle(updatedMeeting.getTitle());
        foundMeeting.setDate(updatedMeeting.getDate());
        foundMeeting.setDescription(updatedMeeting.getDescription());

        meetingService.update(foundMeeting);
        return new ResponseEntity<Meeting>(foundMeeting, HttpStatus.OK);
    }

}
