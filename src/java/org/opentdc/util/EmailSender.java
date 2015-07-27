/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Arbalo AG
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.opentdc.util;

import java.util.Properties;
import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletContext;

import org.opentdc.service.exception.InternalServerErrorException;

public class EmailSender {
	private static final Logger logger = Logger.getLogger(EmailSender.class.getName());
	private static Properties config = null;
	private static String smtpUser = null;
	private static String smtpPwd = null;
   
	public EmailSender(
			ServletContext context) 
	{
		logger.info("EmailSender(" + context.toString() + ")");
		if (config == null) {
			config = new Properties();
			config.put("mail.smtp.host", context.getInitParameter("smtpHost"));
			config.put("mail.smtp.auth", "true");
			smtpUser = context.getInitParameter("smtpUser");
			smtpPwd = context.getInitParameter("smtpPassword");
			logger.info("EmailSender() -> initialized config");
		}
	}
	
	public void sendMessage(
		String toAddress,
		String fromAddress,
		String subject,
		String body)
		throws InternalServerErrorException
	{
		logger.info("sendMessage(" + toAddress + ", " + fromAddress + ", " + subject + ", " + body + ")");
		Session _session = Session.getDefaultInstance(
				config,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(smtpUser, smtpPwd);
					}
				});
		try {
			MimeMessage _message = new MimeMessage(_session);
			_message.setFrom(new InternetAddress(fromAddress));
			_message.setRecipient(Message.RecipientType.BCC, new InternetAddress("admin@arbalo.ch"));
			_message.addRecipient(Message.RecipientType.TO, new InternetAddress(toAddress));
			_message.setSubject(subject);
			_message.setText(body);
			
			Transport.send(_message);
			logger.info("email message sent successfully");
		}
		catch (MessagingException _ex) {
			throw new InternalServerErrorException("could not send the email message: " + _ex.getMessage());
		}
	}	
}
