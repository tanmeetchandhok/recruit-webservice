package com.recruit.controllers;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.io.IOException;
import org.joda.time.LocalDate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import com.recruit.application.Application;
import com.recruit.entities.Applications;
import com.recruit.entities.Offer;
import com.recruit.enumerator.ApplicationStatus;



@RunWith(SpringRunner.class)
@SpringBootTest(classes=Application.class)
@AutoConfigureMockMvc
@Transactional
public class OfferControllerTest {
	
	 @Autowired
	 private MockMvc mockMvc;
	 
	 private HttpMessageConverter objectToJson;
	 
	 protected String json(Object o) throws IOException {
	        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
	        this.objectToJson.write(o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
	        return mockHttpOutputMessage.getBodyAsString();
	    }
	 
	         LocalDate localDate = LocalDate.now();
	       
	//    Create New Offer
	  
	    @Test
	    public void createNewOffer() throws Exception {
	        Offer offer = new Offer(1,"Exprience Java Developer",localDate ,3);
	        offer.addApplication(new Applications("Java Experience Candidate","abc@gmail.com",ApplicationStatus.APPLIED));
            mockMvc.perform(post("/offer")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(json(offer)))
	                .andExpect(status().isCreated())
	                .andExpect(header().string("Location", is("http://localhost/offer/1")))
	                .andExpect(content().string(""));
	                //.andDo(MockMvcResultHandlers.print());
	    }

	 //    Get Offer 

	    @Test
	    public void retrieveOffer() throws Exception {
	    	 Offer offer = new Offer(1,"New Java Developer",localDate ,2);
		        offer.addApplication(new Applications("Java Fresher Candidate","xyz@gmail.com",ApplicationStatus.INVITED));
	    	mockMvc.perform(get("/offer/1").contentType(MediaType.APPLICATION_JSON))
	                .andExpect(status().isOk())
	                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
	                .andExpect(jsonPath("$.jobTitle", is("New Java Developer")))
	                .andExpect(jsonPath("$.startDate", is(localDate)))
	                .andExpect(jsonPath("$.numberOfApplications.", is(2)))
	                .andExpect(jsonPath("$.applications[0].relatedOffer", is("Java Fresher Candidate")))
	                .andExpect(jsonPath("$.applications[0].email", is("xyz@gmail.com")))
                    .andExpect(jsonPath("$.applications[0].status", is(ApplicationStatus.INVITED)));
	                //.andDo(MockMvcResultHandlers.print());
	    }
	 
	    
	 //    Update Offer 

	    @Test
	    public void updateOffer() throws Exception {
	        Offer offer = new Offer(1,"Exprience Java Developer Updated",localDate ,3);
	        offer.addApplication(new Applications("Java Experience Candidate Updated","abc@gmail.com",ApplicationStatus.APPLIED));
	        mockMvc.perform(put("/offer/1")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(json(offer)))
	                .andExpect(status().isOk())
	                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
	                .andExpect(jsonPath("$.jobTitle", is("Exprience Java Developer Updated")))
	                .andExpect(jsonPath("$.startDate", is(localDate)))
	                .andExpect(jsonPath("$.numberOfApplications.", is(3)))
	                .andExpect(jsonPath("$.applications[0].relatedOffer", is("Java Experience Candidate Updated")))
	                .andExpect(jsonPath("$.applications[0].email", is("abc@gmail.com")))
                    .andExpect(jsonPath("$.applications[0].status", is(ApplicationStatus.APPLIED)));
	                            
	    }
	    
	      //    Delete Offer

	    @Test
	    public void deleteOffer() throws Exception {
	        mockMvc.perform(delete("/offer/1")
	                .contentType(MediaType.APPLICATION_JSON))
	                .andExpect(status().isNoContent())
	                .andExpect(content().string(""));
	               }	
}
