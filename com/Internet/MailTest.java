package com_second.Internet;


import java.io.Console;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;


/**
 * This program needs to download javaMail and Java Activation Framework
 * javaMail: https://javaee.github.io/javamail
 * Java Activation Framework: http://www.oracle.com/technetwork/java/javase/jaf-135115.html
 * or u can get it from maven Central, then run :
 * java -classpath .:javax.mail.jar:activation-1.1.1.jar path/to/message.txt
 */
public class MailTest {
    public static void main(String[] args) throws IOException {
        Properties prop = new Properties();
        try (InputStream in = Files.newInputStream(Paths.get("mail","mail.properties"))){
            prop.load(in);
        }
        List<String> lines = Files.readAllLines(Paths.get(args[0]), StandardCharsets.UTF_8);
        String from = lines.get(0);
        String to = lines.get(1);
        String subject = lines.get(2);

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 3; i < lines.size();i++){
            stringBuilder.append(lines.get(i));
            stringBuilder.append("\n");
        }

        Console console = System.console();
        String password = new String(console.readPassword("Password: "));

        Session mailSession = Session.getDefaultInstance(props);
        var message = new MimeMessage(mailSession);
        message.setFrom(new InternetAddress(from));
        message.addReceipt(RecipientType.T0,new InternetAddress(to));
        message.setSubject(subject);
        message.setText(stringBuilder.toString());
        Transport tr = mailSession.getTransport();
        try {
            tr.connect(null,password);
            tr.sendMessage(message,message.getAllRecipients);
        }
        finally {
            tr.close();
        }

    }
}
