package com.star.sud.notification.service;
/*@Author Sudarshan*/
public interface StarEmailConfigurationService {

	public String sendMail( String from,  String password, String to, String subject, String message);

}
