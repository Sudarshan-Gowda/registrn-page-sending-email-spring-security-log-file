package com.star.sud.registrn.model;
/*@Author Sudarshan*/
import java.util.Date;

import com.star.sud.security.model.StarUser;

public interface StarUserRegistration {

	public Long getId();

	public void setId(Long id);

	public String getFirstName();

	public void setFirstName(String firstName);

	public String getGender();

	public void setGender(String gender);

	public Date getDob();

	public void setDob(Date dob);

	public String getContactNum();

	public void setContactNum(String contactNum);

	public String getEmail();

	public void setEmail(String email);

	public String getLastName();

	public void setLastName(String lastName);

	public StarUser getUser();

	public void setUser(StarUser user);

}
