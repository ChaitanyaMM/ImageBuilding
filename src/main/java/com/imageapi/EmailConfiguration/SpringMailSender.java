package com.imageapi.EmailConfiguration;

 

import java.util.Arrays;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.context.IContext;

 
 @Configuration
 public class SpringMailSender {
	@Autowired
    private JavaMailSender mailSender;
	
   // @Autowired(required=true)
	private TemplateEngine textTemplateEngine;
	
	
	private String vmpathSample ="/WEB-INF/txt/sample.txt";
	
	
	
	

 

    public void setTextTemplateEngine(TemplateEngine textTemplateEngine) {
		this.textTemplateEngine = textTemplateEngine;
	}

	 public void sendEmail(final String subject, final String message,
            final String mailIds,final String vmpath,final Map<String, Object> model) {
    	System.out.println(mailIds.indexOf(0));
        MimeMessagePreparator preparator = new MimeMessagePreparator() {
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
                message.setTo(mailIds);
                message.setFrom(new InternetAddress("krishnachaitu.17@gmail.com"));
//                 User user = new User();
//                Map<String, Object> userModel = new HashMap<String, Object>();
//               userModel.put("user", user);
                  Context ctx = new Context( );
                  String text = "hai this is sample testing for image Building trying out themeleaf";
                  System.out.println("textTemplateEngine<><><><><"+textTemplateEngine);
				  System.out.println("textTemplateEngine<><><><><"+textTemplateEngine);

//                  TemplateEngine textTemplateEngine =new TemplateEngine();
//                  System.out.println("textTemplateEngine<><><><><"+textTemplateEngine);

  				//  String text =textTemplateEngine.process(vmpath,ctx);

//                final Context ctx = new Context();
//
//                  String text = textTemplateEngine.process(vmpath, ctx);
 				 System.out.println(text);
             //   String text ="chaitanya";
                message.setSubject(subject);
               message.setText(text, true);
            }
        }; 
        try {
             //mailSender.send(mailMessage);
            mailSender.send(preparator);
            System.out.println("Email sending complete.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    } 
   /* public void sendEmail(
    		  final String recipientName, final String recipientEmail, final String imageResourceName,
    		  final byte[] imageBytes, final String imageContentType, final Locale locale)
    		  throws MessagingException {

    		    // Prepare the evaluation context
    		    final Context ctx = new Context(locale);
    		    ctx.setVariable("name", recipientName);
    		    ctx.setVariable("subscriptionDate", new Date());
    		    ctx.setVariable("hobbies", Arrays.asList("Cinema", "Sports", "Music"));
    		    ctx.setVariable("imageResourceName", imageResourceName); // so that we can reference it from HTML

    		    // Prepare message using a Spring helper
    		    final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
    		    final MimeMessageHelper message =
    		        new MimeMessageHelper(mimeMessage, true, "UTF-8"); // true = multipart
    		    message.setSubject("Example HTML email with inline image");
    		    message.setFrom("krishachaitu.17@gmail.com");
    		    message.setTo(recipientEmail);

    		    // Create the HTML body using Thymeleaf
    		    final String htmlContent = textTemplateEngine.process("inliningExample.html", ctx);
    		    message.setText(htmlContent, true); // true = isHtml

    		    // Add the inline image, referenced from the HTML code as "cid:${imageResourceName}"
    		    final InputStreamSource imageSource = new ByteArrayResource(imageBytes);
    		    message.addInline(imageResourceName, imageSource, imageContentType);

    		    // Send mail
    		    this.mailSender.send(mimeMessage);

    		}*/
    
    public void setMailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }
    
 	public void sample()  {
 		System.out.println("Sample is Calling ");
        Locale currentLocale = LocaleContextHolder.getLocale();

 			sendEmail("Sample Testing Email - ImageBuilding..", "ImageBuildingSample","krishnachaitu.17@gmail.com", "inliningExample.html",null);
		 
     	  
      }
    
    
    

}
