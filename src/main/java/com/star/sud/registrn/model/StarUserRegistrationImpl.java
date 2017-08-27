package com.star.sud.registrn.model;
/*@Author Sudarshan*/
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.star.sud.security.model.StarUser;
import com.star.sud.security.model.StarUserImpl;

@Entity
@Table(name = "STAR_USER_REGISTRN")
public class StarUserRegistrationImpl implements StarUserRegistration {

	@Id
	@GenericGenerator(name = "genericId", strategy = "increment")
	@GeneratedValue(generator = "genericId")
	@Column(name = "STAR_ID")
	protected Long id;

	@Column(name = "FIRST_NAME")
	protected String firstName;

	@Column(name = "LAST_NAME")
	protected String lastName;

	@Column(name = "GENDER")
	protected String gender;

	@Column(name = "DOB")
	protected Date dob;

	@Column(name = "CONTACT_NUM")
	protected String contactNum;

	@Column(name = "EMAIL")
	protected String email;

	@MapsId
	@OneToOne(targetEntity = StarUserImpl.class, mappedBy = "starUserRegistration", fetch = FetchType.LAZY)
	@JoinColumn(name = "STAR_ID")
	protected StarUser user;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getContactNum() {
		return contactNum;
	}

	public void setContactNum(String contactNum) {
		this.contactNum = contactNum;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public StarUser getUser() {
		return user;
	}

	public void setUser(StarUser user) {
		this.user = user;
	}

}
