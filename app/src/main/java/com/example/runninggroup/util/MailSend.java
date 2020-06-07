package com.example.runninggroup.util;

import com.sun.mail.util.MailSSLSocketFactory;

import java.security.GeneralSecurityException;
import java.util.Properties;
import java.util.UUID;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailSend {
    public static boolean sendMssage(String title,String msg,String mail){

        try {
            Properties prop = new Properties();
            prop.setProperty("mail.host","smtp.qq.com");
            prop.setProperty("mail.transport.protocol","smtp");
            prop.setProperty("mail.smtp.auth","true");
            //qq邮箱，设置SSL加密
            MailSSLSocketFactory sf = new MailSSLSocketFactory();
            sf.setTrustAllHosts(true);
            prop.put("mail.smtp.ssl.enable","true");
            prop.put("mail.smtp.ssl.socketFactory",sf);
            //使用JavaMail发送邮件的5个步骤
            //1.创建定义整个应用程序所需的环境信息的session对象
            //qq邮箱需要加一个Authenticator
            Session session = Session.getDefaultInstance(prop, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("1449931670@qq.com","qjbnhohbalqljahf");
                }
            });
//            session.setDebug(true);

            //2.通过Session活得transport对象
            Transport transport = session.getTransport();

            //3.使用邮箱的用户名和授权码连接邮件服务器
            transport.connect("1449931670@qq.com","qjbnhohbalqljahf");

            //4.创建邮件
            MimeMessage message = new MimeMessage(session);
            //指定发件人
            message.setFrom(new InternetAddress("1449931670@qq.com"));
            //指定收件人
            message.setRecipient(Message.RecipientType.TO,new InternetAddress(mail));
            //邮件标题
            message.setSubject(title);
            //邮件正文
            message.setContent(msg,  "text/html;charset=gbk");
            //5.发邮件
            transport.sendMessage(message,message.getAllRecipients());

            //6关闭
            transport.close();



            return true;
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
            return false;
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
            return false;
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }



}
