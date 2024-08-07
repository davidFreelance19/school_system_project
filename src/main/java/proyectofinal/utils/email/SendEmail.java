package proyectofinal.utils.email;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

import proyectofinal.config.EnvsAdapter;
import proyectofinal.utils.http.ExceptionHandler;

public class SendEmail {

    public static HtmlEmail transport() {
        HtmlEmail mail = new HtmlEmail();
        mail.setHostName(EnvsAdapter.MAIL_HOST);
        mail.setSmtpPort(EnvsAdapter.MAIL_PORT);
        mail.setAuthenticator(new DefaultAuthenticator(EnvsAdapter.MAIL_AUTH_USER, EnvsAdapter.MAIL_AUTH_PASSWORD));
        return mail;
    }

    public static void sendEmailRegisterAccount(String userEmail, String password, String boleta)
            throws ExceptionHandler {
        HtmlEmail mail = transport();
        try {
            mail.setFrom("noreply@schoolsystem.edu");
            mail.addTo(userEmail);
            mail.setSubject("Welcome to Our School System.");

            String htmlMsg = """
                                <h1>Welcome to Our School System</h1>
                                <p>Your login information:</p>

                                <div class="info">
                                    <p><strong>Username:</strong> %s</p>
                                    <p><strong>Password:</strong> %s</p>
                                </div>

                               <p>To access your account, please follow these steps:</p>
                               <ol>
                                   <li>Visit our login page at <a href="#">https://login.schoolsystem.edu</a></li>
                                   <li>Enter your username and password</li>
                               </ol>

                               <div class="warning">
                                   <p><strong>Important:</strong></p>
                                   <ul>
                                       <li>For security reasons, please change your password after your first login.</li>
                                       <li>If you didn't request this account, please contact our IT support immediately at support@schoolsystem.edu.</li>
                                       <li>Never share your login information with anyone.</li>
                                   </ul>
                               </div>

                               <p>If you have any questions or need assistance, please don't hesitate to contact our support team.</p>

                               <p>Best regards,<br>The School System Team</p>
                    """;

            mail.setHtmlMsg(String.format(htmlMsg, boleta, password));
            mail.send();
        } catch (EmailException e) {
            throw new ExceptionHandler("Failed to send email", 500);
        }
    }
}
