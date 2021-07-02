package poly.userservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import poly.userservice.config.AuthConfig;
import poly.userservice.dto.MailDto;
import poly.userservice.util.CmmUtil;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


import java.util.Date;
import java.util.Properties;

@Service
@Slf4j
public class MailService {

    final String host = "smtp.gmail.com";

    public int doSendMail(MailDto pDto) {

        log.info("domail Start!");

        final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
        // 이메일 객체생성하기
        Properties props = System.getProperties();


        int res = 1;

        if (pDto == null) {
            pDto = new MailDto();
        }


        String toMail = CmmUtil.nvl(pDto.getToMail());

        props.setProperty("mail.smtp.host", "smtp.gmail.com");
        props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.port", "465");
        props.setProperty("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.auth", "true");

        props.put("mail.debug", "true");
        props.put("mail.store.protocol", "pop3");
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.ssl.trust","smtp.office365.com");

        final String username = AuthConfig.ID; // 구글이메일
        final String password = AuthConfig.PW; // 구글비번


        try{
            Session session = Session.getDefaultInstance(props,
                    new Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(username, password);
                        }
                    });

            //메세지 설정
            Message msg = new MimeMessage(session);

            //보내는사람 받는사람 설정
            msg.setFrom(new InternetAddress(username));
            msg.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(CmmUtil.nvl(pDto.getToMail()), false));
            msg.setSubject(pDto.getTitle());
            msg.setText(pDto.getContents());
            msg.setSentDate(new Date());
            Transport.send(msg);
            System.out.println("발신성공!");

            res = 1;
        } catch (MessagingException error) {
            System.out.println("에러가 발생했습니다(메일 전송 실패!) " + error);
            error.printStackTrace();

            res = 0;
        }
        log.info(".doSendMail end!!");

        return res;
    }
}
