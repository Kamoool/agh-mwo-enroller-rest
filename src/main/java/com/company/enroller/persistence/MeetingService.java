package com.company.enroller.persistence;

import java.util.Collection;

import com.company.enroller.model.Participant;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;

import com.company.enroller.model.Meeting;

@Component("meetingService")
public class MeetingService {

    Session session;

    public MeetingService() {
        session = DatabaseConnector.getInstance().getSession();
    }

    public Collection<Meeting> getAll() {
        String hql = "FROM Meeting";
        Query query = session.createQuery(hql);
        return query.list();
//        return session.createCriteria(Meeting.class).list();
    }


    public Meeting findById(long id) {
        return (Meeting) session.get(Meeting.class, id);//podajemy typ i klucz
    }

    public void add(Meeting meeting) {
        Transaction transaction = this.session.beginTransaction();
        session.save(meeting);
        transaction.commit();
    }

    public void delete(Meeting meeting) {
        Transaction transaction = this.session.beginTransaction();
        session.delete(meeting);
        transaction.commit();
    }

    public void update(Meeting meeting) {
        Transaction transaction = this.session.beginTransaction();
        session.merge(meeting);
        transaction.commit();
    }


}
