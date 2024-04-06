package bilingual.emailSender;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class JavaMailService {
    private final JavaMailSender javaMailSender;
    private String senderEmail;

    @Value("${spring.mail.username}")
    public void setSenderEmail(String senderEmail) {
        this.senderEmail = senderEmail;
    }

    @Autowired
    public JavaMailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendVerificationCode(String to, String subject, String message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(senderEmail);
        mailMessage.setTo(to);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);
        javaMailSender.send(mailMessage);
    }

    public void sendVerificationCodeHtml(String to, String subject, String message) {
        try {
            MimeMessage messages = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(messages, true, "UTF-8");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(message, true);
            javaMailSender.send(messages);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}

