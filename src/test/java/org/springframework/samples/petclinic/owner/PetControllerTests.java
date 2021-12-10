package org.springframework.samples.petclinic.owner;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.samples.petclinic.utility.PetTimedCache;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


@WebMvcTest(value = PetController.class,
			includeFilters = {
				@ComponentScan.Filter(value = PetTypeFormatter.class, type = FilterType.ASSIGNABLE_TYPE),
				@ComponentScan.Filter(value = PetService.class, type = FilterType.ASSIGNABLE_TYPE),
				@ComponentScan.Filter(value = LoggerConfig.class, type = FilterType.ASSIGNABLE_TYPE),
				@ComponentScan.Filter(value = PetTimedCache.class, type = FilterType.ASSIGNABLE_TYPE),
			})
class PetControllerTests {
	@Autowired
	private MockMvc mockmvc;

	@MockBean
	private PetRepository pets;

	@MockBean
	private OwnerRepository owners;

	private static final String VIEWS_PETS_CREATE_OR_UPDATE_FORM = "pets/createOrUpdatePetForm";

	@BeforeEach
	void setup(){
		Owner owner = new Owner();
		owner.setId(1);

		PetType dog = new PetType();
		dog.setId(2);
		dog.setName("dog");

		Pet pet = new Pet();
		pet.setId(3);

		given(pets.findPetTypes()).willReturn(Lists.newArrayList(dog));
		given(owners.findById(1)).willReturn(owner);
		given(pets.findById(3)).willReturn(pet);
	}

	@Test
	public void TestInitCreationFormSuccessful() throws Exception {
		mockmvc.perform(get("/owners/1/pets/new").contentType(MediaType.APPLICATION_JSON))
															.andExpect(status().isOk())
															.andExpect(model().attributeExists("pet"))
															.andExpect(view().name(VIEWS_PETS_CREATE_OR_UPDATE_FORM));
	}

	@Test
	public void TestProcessCreationFormSuccessful() throws Exception {
		mockmvc.perform(post("/owners/1/pets/new").param("name", "daisy")
														 	 .param("type", "dog")
															 .param("id", "3")
															 .param("birthDate", "2021-02-01"))
															 .andExpect(status().is3xxRedirection())
															 .andExpect(view().name("redirect:/owners/{ownerId}"));
	}

	@Test
	public void TestProcessCreationFomNotSuccessful() throws Exception {
		mockmvc.perform(post("/owners/1/pets/new")).andExpect(status().isOk())
															  .andExpect(model().attributeExists("pet"))
															  .andExpect(view().name(VIEWS_PETS_CREATE_OR_UPDATE_FORM));
	}

	@Test
	public void TestInitUpdateFormSuccessful() throws Exception {
		mockmvc.perform(get("/owners/1/pets/3/edit").contentType(MediaType.APPLICATION_JSON))
																.andExpect(status().isOk())
																.andExpect(model().attributeExists("pet"))
																.andExpect(view().name(VIEWS_PETS_CREATE_OR_UPDATE_FORM));
	}

	@Test
	public void TestProcessUpdateFormSuccessful() throws Exception {
		mockmvc.perform(post("/owners/1/pets/3/edit").param("name", "daisy")
																.param("type", "dog")
																.param("id", "3")
																.param("birthDate", "2021-02-01"))
																.andExpect(status().is3xxRedirection())
																.andExpect(view().name("redirect:/owners/{ownerId}"));
	}

	@Test
	public void TestProcessUpdateFormNotSuccessful() throws Exception {
		mockmvc.perform(post("/owners/1/pets/3/edit").param("name", "lucy")
																.param("type", "cat"))
																.andExpect(status().isOk())
																.andExpect(model().attributeExists("pet"))
																.andExpect(view().name(VIEWS_PETS_CREATE_OR_UPDATE_FORM));
	}
}
