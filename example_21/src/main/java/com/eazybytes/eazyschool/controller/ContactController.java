package com.eazybytes.eazyschool.controller;

import com.eazybytes.eazyschool.model.Contact;
import com.eazybytes.eazyschool.service.ContactService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Slf4j
public class ContactController {

    //private static Logger log = LoggerFactory.getLogger(ContactController.class);
    private final ContactService contactService;

    @Autowired
    public ContactController( ContactService contactService){
        this.contactService = contactService;
    }

    @RequestMapping("/contact")
    public String displayContactPage(){
        return "contact.html";
    }

//    @RequestMapping(value = "/saveMsg" , method = RequestMethod.POST )
//    public ModelAndView saveMessage(@RequestParam(name="name") String name , @RequestParam(name="mobileNum") String mobileNumber , @RequestParam String email,
//                                    @RequestParam String subject , @RequestParam String message){
//        log.info("Name : " , name);
//        log.info("Mobile Number : " + mobileNumber);
//        log.info("Email Address : " + email);
//        log.info("Subject : " + subject);
//        log.info("Messsage : "+message);
//        return new ModelAndView("redirect:/contact");
//    }

    @PostMapping(value="/saveMsg")
    public ModelAndView saveMessage(Contact contact){
        contactService.saveMessageDetails(contact);
        return new ModelAndView("redirect:/contact");
    }
}
