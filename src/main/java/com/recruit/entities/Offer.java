package com.recruit.entities;

import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.Past;
import org.joda.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name="OfferMaster") 
public class Offer {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;     //primary
	@Column(unique = true)
	private String jobTitle;  //unique
	@JsonFormat(pattern="yyyy-MM-dd")
	@Past
	private LocalDate startDate;
	private int numberOfApplications;

	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="offerId") 
	private Set<Applications> applications;

	public Offer() {
		super();
	}

	public Offer(String jobTitle, LocalDate startDate, int numberOfApplications, Set<Applications> applications) {
		super();
		this.jobTitle = jobTitle;
		this.startDate = startDate;
		this.numberOfApplications = numberOfApplications;
		this.applications = applications;
	}

	public Offer(int id, String jobTitle, LocalDate startDate, int numberOfApplications) {
		super();
		this.id = id;
		this.jobTitle = jobTitle;
		this.startDate = startDate;
		this.numberOfApplications = numberOfApplications;
	}

	public void addApplication(Applications application)
	{
		this.applications.add(application);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public int getNumberOfApplications() {
		return numberOfApplications;
	}

	public void setNumberOfApplications(int numberOfApplications) {
		this.numberOfApplications = numberOfApplications;
	}

	public Set<Applications> getApplications() {
		return applications;
	}

	public void setApplications(Set<Applications> applications) {
		this.applications = applications;
	}
}
