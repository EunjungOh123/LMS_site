package com.example.coursesite_final.member.mail;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;
@RequiredArgsConstructor
@Component
public class MailSendService {

    private final JavaMailSender javaMailSender;
    private final String from = "qazwsxedc4084@gmail.com";

    public void sendMail (String email, String subject, String text) {
        final MimeMessagePreparator messagePreparator = new MimeMessagePreparator() {
            @Override
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
                messageHelper.setFrom(from);
                messageHelper.setTo(email);
                messageHelper.setSubject(subject);
                messageHelper.setText(text, true);
            }
        };
        try {
            javaMailSender.send(messagePreparator);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public String createRegisterTextMessage (String userId, String emailAuthKey) {
        String text = "";
        text += "<h2 style=\"color: #2e6c80;\">";
        text += userId + "님 회원가입을 축하드립니다!</h2>";
        text += "<h4>원활한 사이트 이용을 위해 이메일 인증을 완료해주세요.</h4>" +
                "<p>아래 링크를 클릭하면 인증이 완료됩니다.</p>";
        text += "<div><a href=\"http://localhost:8080/member/email-auth?id=";
        text += emailAuthKey +"\">가입 완료</a></div>";
        return text;
    }
    public String resetPasswordTextMessage (String userId, String resetPasswordKey) {
        String text = "";
        text += "<h2 style=\"color: #2e6c80;\">"+userId +"님 안녕하세요.</h2>";
        text += "<h3>비밀번호 재설정을 위한 인증 메일입니다.</h3>";
        text += "<h3>아래 링크를 클릭하여 비밀번호를 다시 설정 해주세요.</h3>";
        text += "<div><a target=\"_blank\" href=\"http://localhost:8080/member/reset-password?id=";
        text += resetPasswordKey + "\">비밀번호 재설정</a></div>";
        return text;
    }
}
