package pet.store.model;


import lombok.Data;
import lombok.NoArgsConstructor;
import pet.store.entity.Employee;
import pet.store.entity.PetStore;

@Data
@NoArgsConstructor
public class PetStoreEmployee {

	private Long employeeId;
	private String employeeFirstName;
	private String employeeLastName;
	private long employeePhone;
	private String employeeJobTitle;
	private PetStore petStore;
	public PetStoreEmployee(Employee employee){
		employeeId=employee.getEmployeeId();
		employeeFirstName=employee.getEmployeeFirstName();
		employeeLastName=employee.getEmployeeLastName();
		employeePhone=employee.getEmployeePhone();
		employeeJobTitle=employee.getEmployeeJobTitle();



	}
	
}
