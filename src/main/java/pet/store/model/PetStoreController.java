package pet.store.model;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pet.store.service.PetStoreService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/pet_store")
@Slf4j
public class PetStoreController {
    @Autowired
    private PetStoreService petStoreService;

    @PostMapping("/create")
    @ResponseStatus(code = HttpStatus.CREATED)
    public PetStoreData insertPetStore(
            @RequestBody PetStoreData petStoreData) {
        log.info("Creating Pet Store []", petStoreData);
        return petStoreService.savePetStore(petStoreData);
    }

    @PutMapping("/update/{petStoreId}")
    public PetStoreData updatePetStore(@PathVariable Long petStoreId, @RequestBody PetStoreData petStoreData) {
        petStoreData.setPetStoreId(petStoreId);
        log.info("Updating Pet Store", petStoreData);
        return petStoreService.savePetStore(petStoreData);
    }


    @GetMapping(value = "/ping")
    public String ping() {
        return "ping";
    }


    @PostMapping("/petstore/{petStoreId}/employee")
    @ResponseStatus(code = HttpStatus.CREATED)
    public PetStoreEmployee createEmployee(@PathVariable Long petStoreId, @RequestBody PetStoreEmployee petStoreEmployee) {
        log.info("Creating Pet Store Employee [] for petstore with ID={}",
                petStoreId, petStoreEmployee);
        return petStoreService.saveEmployee(petStoreId, petStoreEmployee);

    }

    @PostMapping("/petstore/{petStoreId}/customer")
    @ResponseStatus(code = HttpStatus.CREATED)
    public PetStoreCustomer createCustomer(@PathVariable Long petStoreId, @RequestBody PetStoreCustomer petStoreCustomer) {
        log.info("Creating Pet Store Customer [] for petstore with ID= {}",
                petStoreId, petStoreCustomer);
        return petStoreService.saveCustomer(petStoreId, petStoreCustomer);
    }

    @GetMapping("/listOfPetStore")
    public List<PetStoreData> petStoreDataList() {

        return petStoreService.retrieveAllPetStores();
    }
    @GetMapping("/{petStoreId}")
    public PetStoreData retrivePetStoreById(@PathVariable Long petStoreId){
        return petStoreService.retrivePetStoreById(petStoreId);

    }
    @DeleteMapping("/{petStoreId}")
    public Map<String, String> deletePetStoreById(@PathVariable Long petStoreId){
        petStoreService.deletePetStoreById(petStoreId);
        return Map.of("message", "Deletion of pet store with ID="+ petStoreId +" is successful");
    }
}
