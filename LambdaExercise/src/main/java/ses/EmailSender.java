package ses;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.simpleemail.*;
import com.amazonaws.services.simpleemail.model.*;
import com.amazonaws.regions.Regions;
import org.joda.time.LocalDate;

public class EmailSender {
    public EmailResult handleRequest(EmailRequest request, Context context) {

        LambdaLogger logger = context.getLogger();
        logger.log("Entering send_email");

        try {
            AmazonSimpleEmailService client =
                    AmazonSimpleEmailServiceClientBuilder.standard()

                            // Replace US_WEST_2 with the AWS Region you're using for
                            // Amazon SES.
                            .withRegion(Regions.US_WEST_2).build();

            // TODO:
            // Use the AmazonSimpleEmailService object to send an email message
            // using the values in the EmailRequest parameter object

            SendEmailRequest emailRequest = new SendEmailRequest()
                    .withDestination(
                            new Destination().withToAddresses(request.to))
                    .withMessage(new Message()
                            .withBody(new Body()
                                    .withHtml(new Content()
                                            .withCharset("UTF-8").withData(request.htmlBody))
                                    .withText(new Content()
                                            .withCharset("UTF-8").withData(request.textBody)))
                            .withSubject(new Content()
                                    .withCharset("UTF-8").withData(request.subject)))
                    .withSource(request.from);
            client.sendEmail(emailRequest);


            logger.log("Email sent!");
        } catch (Exception ex) {
            logger.log("The email was not sent. Error message: " + ex.getMessage());
            throw new RuntimeException(ex);
        }
        finally {
            logger.log("Leaving send_email");
        }

        EmailResult emailResult = new EmailResult();
        emailResult.message = "Email successfully sent!";
        emailResult.timestamp = LocalDate.now().toString();

        return emailResult;
    }

}