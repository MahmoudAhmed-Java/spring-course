package com.eazybytes.eazyschool.service;

import com.eazybytes.eazyschool.constants.EazySchoolConstants;
import com.eazybytes.eazyschool.controller.ContactController;
import com.eazybytes.eazyschool.model.Contact;
import com.eazybytes.eazyschool.repository.ContactRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.ApplicationScope;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.context.annotation.SessionScope;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
//@RequestScope
//@SessionScope
//@ApplicationScope
public class ContactService {

    //private static Logger log = LoggerFactory.getLogger(ContactService.class);
    //private int counter = 0;
    @Autowired
    private ContactRepository contactRepository;

    public ContactService(){
        System.out.println("Contact Service Bean Initialized");
    }

    public boolean saveMessageDetails(Contact contact){
        boolean isSaved = false;
        contact.setStatus(EazySchoolConstants.OPEN);
        contact.setCreatedBy(EazySchoolConstants.ANONYMOUS);
        contact.setCreatedAt(LocalDateTime.now());
        int result = contactRepository.saveContactMsg(contact);
        if( result > 0 ){
            isSaved = true;
        }
        log.info(contact.toString());
        return isSaved;
    }

//    public int getCounter() {
//        return counter;
//    }
//
//    public void setCounter(int counter) {
//        this.counter = counter;
//    }
    public List<Contact> findMsgsWithOpenStatus(){
        List<Contact> contactMsgs = contactRepository.findMsgsWithStatus(EazySchoolConstants.OPEN);
        return contactMsgs;
    }

    public boolean updateMsgStatus(int id , String updatedBy){
        boolean isUpdated = false;
        int result = contactRepository.updateMsgStatus(id,EazySchoolConstants.CLOSE,updatedBy);
        if( result > 0 ){
            isUpdated = true;
        }
        return isUpdated;
    }
}
