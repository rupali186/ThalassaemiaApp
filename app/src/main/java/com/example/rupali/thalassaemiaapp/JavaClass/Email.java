package com.example.rupali.thalassaemiaapp.JavaClass;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;


public class Email
{

    private static final String username = Constants.MAIL_EMAIL;
    private static final String password = Constants.MAIL_PASSWORD;
    private   String emailid = "rupalichawla186@gmail.com";
    private   String subject = "Photo";
    private   String message = "Hello";
    private Multipart multipart = new MimeMultipart();
    private MimeBodyPart messageBodyPart = new MimeBodyPart();
    public File mediaFile;


    public Email(String receiverMail,String subject,String messageBody) {
        emailid = receiverMail;
        this.subject=subject;
        this.message=messageBody;
        sendMail(emailid,subject,messageBody);
    }

    private void sendMail(String email, String subject, String messageBody)
    {
        Session session = createSessionObject();

        try {
            Message message = createMessage(email, subject, messageBody, session);
            new SendMailTask().execute(message);
        }
        catch (AddressException e)
        {
            e.printStackTrace();
        }
        catch (MessagingException e)
        {
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
    }


    private Session createSessionObject()
    {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.port", "587");

        return Session.getInstance(properties, new javax.mail.Authenticator()
        {
            protected PasswordAuthentication getPasswordAuthentication()
            {
                return new PasswordAuthentication(username, password);
            }
        });
    }

    private Message createMessage(String email, String subject, String messageBody, Session session) throws

            MessagingException, UnsupportedEncodingException
    {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(Constants.MAIL_EMAIL, "Foundation Against Thalassaemia"));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(email, email));
        message.setSubject(subject);
        message.setText(messageBody);
        return message;
    }



    public class SendMailTask extends AsyncTask<Message, Void, Void>
    {
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            //progressDialog = ProgressDialog.show(Email.this, "Please wait", "Sending mail", true, false);
        }

        @Override
        protected void onPostExecute(Void aVoid)
        {
            super.onPostExecute(aVoid);
            Log.d("EmailNoti","mail sent successful");
           // progressDialog.dismiss();
        }

        protected Void doInBackground(javax.mail.Message... messages)
        {
            try
            {
                Transport.send(messages[0]);
            } catch (MessagingException e)
            {
                e.printStackTrace();
            }
            return null;
        }
    }
}

