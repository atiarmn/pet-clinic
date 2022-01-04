package bdd;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.owner.*;

import static org.junit.jupiter.api.Assertions.*;

public class stepNewPet {
	@Autowired
	PetService petService;

	@Autowired
	OwnerRepository ownerRepository;

	private Owner owner;
	private Pet pet;

	@Given("One of the owners named {string}")
	public void the_client_issues_new_pet(String name) {
		owner = new Owner();
		owner.setFirstName(name);
		owner.setLastName("paterson");
		owner.setAddress("gheytarie");
		owner.setCity("tehran");
		owner.setTelephone("09213824900");
		owner.setId(3);
		ownerRepository.save(owner);
	}

	@When("He wants to have another pet")
	public void add_owners_pet() {
		pet = petService.newPet(owner);
	}

	@Then("A new pet will be added to his pet collection successfully")
	public void pet_added() {
		assertEquals(owner.getId(), pet.getOwner().getId());
	}

}
