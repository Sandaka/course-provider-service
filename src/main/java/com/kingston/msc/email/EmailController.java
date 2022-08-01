package com.kingston.msc.email;

/**
 * Created by Sandaka Wijesinghe.
 * Date: 7/26/22
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessagingException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/cps")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/email")
    public String sendMail() throws MessagingException {

        Mail mail = new Mail();
        mail.setFrom("sandaka94@gmail.com");//replace with your desired email
        mail.setMailTo("travelduo94@gmail.com");//replace with your desired email
        mail.setSubject("Email with Spring boot and thymeleaf template!");

        Map<String, Object> model = new HashMap<String, Object>();
        model.put("name", "Developer!");
        model.put("location", "United States");
        model.put("sign", "Java Developer");
        model.put("type", "NEWSLETTER");
        mail.setProps(model);

        try {
            emailService.sendEmail(mail);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (javax.mail.MessagingException e) {
            e.printStackTrace();
        }
        return "Email Sent Successfully.!";
    }
}
