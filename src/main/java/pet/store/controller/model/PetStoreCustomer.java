package pet.store.controller.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
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
