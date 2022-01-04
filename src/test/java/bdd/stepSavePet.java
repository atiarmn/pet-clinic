package bdd;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.owner.*;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class stepSavePet {
	@Autowired
	PetService petService;

	@Autowired
	PetTypeRepository petTypeRepository;

	@Autowired
	OwnerRepository ownerRepository;

	private Pet pet;
	private Owner owner;


	@Given("There is an owner named {string}")
	public void the_client_issues_save_pet(String name) {
		owner = new Owner();
		owner.setFirstName(name);
		owner.setLastName("paterson");
		owner.setAddress("gheytarie");
		owner.setCity("tehran");
		owner.setTelephone("09213824900");
		owner.setId(3);
		ownerRepository.save(owner);
	}

	@When("The owner wants to take {string} for himself")
	public void save_pet(String name) {
		PetType petType = new PetType();
		petType.setName(name);
		petTypeRepository.save(petType);
		pet = new Pet();
		pet.setName(name);
		pet.setType(petType);
		pet.setBirthDate(LocalDate.now());
		petService.savePet(pet, owner);
	}

	@Then("{string} will be added to his pet collection successfully")
	public void pet_saved(String name) {
		assertNotNull(owner.getPet(name));
	}
}
