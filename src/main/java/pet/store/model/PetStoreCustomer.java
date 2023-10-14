package pet.store.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import pet.store.entity.Customer;
import pet.store.entity.PetStore;

import java.util.HashSet;
import java.util.Set;


@Data
@NoArgsConstructor
public class PetStoreCustomer {

	private Long customerId;
	private String customerFirstName;
	private String customerLastName;
	private String customerEmail;


	public PetStoreCustomer(Customer customer){
		customerId= customer.getCustomerId();
		customerFirstName=customer.getCustomerFirstName();
		customerLastName=customer.getCustomerLastName();
		customerEmail=customer.getCustomerEmail();
	}

}
