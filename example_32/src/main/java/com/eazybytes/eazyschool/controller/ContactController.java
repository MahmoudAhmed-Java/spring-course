package com.eazybytes.eazyschool.controller;

import com.eazybytes.eazyschool.model.Contact;
import com.eazybytes.eazyschool.service.ContactService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

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
    public String displayContactPage(Model model){
        model.addAttribute("contact" , new Contact());
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
    public String saveMessage(@Valid @ModelAttribute("contact") Contact contact , Errors errors){
        if( errors.hasErrors() ){
            log.error("Contact form validation failed due to : " + errors.toString());
            return "contact.html";
        }
        contactService.saveMessageDetails(contact);
//        contactService.setCounter(contactService.getCounter()+1);
//        log.info("Number of times the contact form is submitted : " + contactService.getCounter());
        return "redirect:/contact";
    }

    @RequestMapping("/displayMessages")
    public ModelAndView displayMessage(Model model){
        List<Contact> contactMsgs = contactService.findMsgsWithOpenStatus();
        ModelAndView modelAndView = new ModelAndView("messages.html");
        modelAndView.addObject("contactMsgs" , contactMsgs);
        return modelAndView;
    }

    @RequestMapping(value = "/closeMsg" , method = RequestMethod.GET)
    public String closeMsg(@RequestParam(name = "id") int id , Authentication authentication){
        contactService.updateMsgStatus(id,authentication.getName());
        return "redirect:/displayMessages";
    }
}
