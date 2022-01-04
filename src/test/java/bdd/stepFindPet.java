package bdd;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.owner.*;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class stepFindPet {
	@Autowired
	PetService petService;

	@Autowired
	PetRepository petRepository;

	@Autowired
	PetTypeRepository petTypeRepository;

	@Autowired
	OwnerRepository ownerRepository;

	private Owner owner;
	private Pet pet;
	private Pet foundPet;

	private void setup_owner() {
		owner = new Owner();
		owner.setFirstName("mark");
		owner.setLastName("paterson");
		owner.setAddress("gheytarie");
		owner.setCity("tehran");
		owner.setTelephone("09213824900");
		owner.setId(3);
		ownerRepository.save(owner);
	}

	@Given("There are some pets with specific IDs")
	public void the_client_issues_find_pet() {
		setup_owner();
		PetType petType = new PetType();
		petType.setName("dog");
		petTypeRepository.save(petType);

		pet = new Pet();
		pet.setName("daisy");
		pet.setType(petType);
		pet.setBirthDate(LocalDate.now());
		pet.setId(5);

		owner.addPet(pet);
		petRepository.save(pet);
	}

	@When("We want to find a pet with ID = 5")
	public void find_pet_by_id() {
		foundPet = petService.findPet(5);
	}

	@Then("The pet with ID = 5 will be found and returned successfully")
	public void pet_found() {
		assertEquals(pet.getId(), foundPet.getId());
	}
}
