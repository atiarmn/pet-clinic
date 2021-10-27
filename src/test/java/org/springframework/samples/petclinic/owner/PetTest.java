package org.springframework.samples.petclinic.owner;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.samples.petclinic.visit.Visit;
import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import static org.mockito.Mockito.*;

class PetTest {

	@InjectMocks
	private Pet pet;
	private Visit visit;
	Integer petId;
	@BeforeEach
	public void SetUp(){
		this.pet = new Pet();
		pet.setId(1);
		this.petId = 1;

		this.visit = mock(Visit.class);
		this.visit.setDate(LocalDate.of(2021, 10, 13));
	}

	 // Behavior verification
	@Test
	public void AddVisit_GivenVisit_AddtheVisit_Behavioral(){
		pet.addVisit(visit);
		verify(visit).setPetId(pet.getId());
	}
	// State verification
	@Test
	public void AddVisit_GivenVisit_AddtheVisit_State(){
		when(visit.getPetId()).thenReturn(1);
		pet.addVisit(visit);
		Integer actual = visit.getPetId();
		assertEquals("Test for add visit failed: Returned pet id is not equal to expected pet id!",petId, actual );
	}
}
