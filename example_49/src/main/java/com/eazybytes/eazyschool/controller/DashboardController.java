package com.eazybytes.eazyschool.controller;

import com.eazybytes.eazyschool.model.Contact;
import com.eazybytes.eazyschool.model.Person;
import com.eazybytes.eazyschool.repository.PersonRepository;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
public class DashboardController {

    @Autowired
    private PersonRepository personRepository;

    @Value("${eazyschool.pageSize}")
    private int defaultPageSize;

    @Value("${eazyschool.contact.successMsg}")
    private String message;

    @Autowired
    private Environment environment;

    @RequestMapping("/dashboard")
    public String displayDashboard(Model model, Authentication authentication , HttpSession httpSession) {
        Person person = personRepository.readByEmail(authentication.getName());
        model.addAttribute("username", person.getName());
        model.addAttribute("roles", authentication.getAuthorities().toString());
        if( person.getEazyClass() != null && person.getEazyClass().getName() != null ){
            model.addAttribute("enrolledClass" , person.getEazyClass().getName());
        }
        httpSession.setAttribute("loggedInPerson" , person);
        logMessages();
        //throw new RuntimeException("It's been a bad day!!");
        return "dashboard.html";
    }

    private void logMessages() {
        log.error("Error message from the Dashboard page");
        log.warn("Warning message from the Dashboard page");
        log.info("Info message from the Dashboard page");
        log.debug("Debug message from the Dashboard page");
        log.trace("Trace message from the Dashboard page");

        log.error("defaultPageSize value with @Value annotation is : "+defaultPageSize);
        log.error("successMsg value with @Value annotation is : "+message);

        log.error("defaultPageSize value with Environment is : " + environment.getProperty("eazyschool.pageSize") );
        log.error("successMsg value with Environment is : " + environment.getProperty("eazyschool.contact.successMsg"));
        log.error("Java Home Environment variable using environment is : " + environment.getProperty("JAVA_HOME"));
    }

}
