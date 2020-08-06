package com.recruit.entities;

import javax.persistence.*;
import com.recruit.enumerator.ApplicationStatus;

@Entity
@Table(name="ApplicationMaster") 
public class Applications {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;    //primary
	private String relatedOffer;  
	@Column(unique = true)
	private String email;   //unique
	@Transient
	private String resumeText;  //not persist
	@Enumerated(EnumType.STRING)
	private ApplicationStatus status;

    public Applications() {
		super();
	}
	public Applications(String relatedOffer, String email, ApplicationStatus status) {
		super();
		this.relatedOffer = relatedOffer;
		this.email = email;
		this.status = status;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getRelatedOffer() {
		return relatedOffer;
	}
	public void setRelatedOffer(String relatedOffer) {
		this.relatedOffer = relatedOffer;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getResumeText() {
		return resumeText;
	}
	public void setResumeText(String resumeText) {
		this.resumeText = resumeText;
	}
	public ApplicationStatus getStatus() {
		return status;
	}
	public void setStatus(ApplicationStatus status) {
		this.status = status;
	}

}
