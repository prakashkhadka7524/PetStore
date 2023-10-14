package pet.store.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import pet.store.dao.CustomerDao;
import pet.store.dao.EmployeeDao;
import pet.store.dao.PetStoreDao;
import pet.store.entity.Customer;
import pet.store.entity.Employee;
import pet.store.entity.PetStore;
import pet.store.model.PetStoreCustomer;
import pet.store.model.PetStoreData;
import pet.store.model.PetStoreEmployee;

@Service
public class PetStoreService {
    @Autowired
    private PetStoreDao petStoreDao;
    @Autowired
    private EmployeeDao employeeDao;
    @Autowired
    private CustomerDao customerDao;


    @Transactional(readOnly = false)
    public PetStoreData savePetStore(PetStoreData petStoreData) {
        Long petStoreId = petStoreData.getPetStoreId();
        PetStore petStore = findOrCreatePetStore(petStoreId);

        setFieldsInPetStore(petStore, petStoreData);
        return new PetStoreData(petStoreDao.save(petStore));
    }

    private void setFieldsInPetStore(PetStore petStore, PetStoreData petStoreData) {
        petStore.setPetStoreId(petStoreData.getPetStoreId());
        petStore.setPetStoreName(petStoreData.getPetStoreName());
        petStore.setPetStoreAddress(petStoreData.getPetStoreAddress());
        petStore.setPetStoreCity(petStoreData.getPetStoreCity());
        petStore.setPetStoreState(petStoreData.getPetStoreState());
        petStore.setPetStoreZip(petStoreData.getPetStoreZip());
        petStore.setPetStorePhone(petStoreData.getPetStorePhone());
    }

    private PetStore findOrCreatePetStore(Long petStoreId) {
        PetStore petStore;
        if (Objects.isNull(petStoreId)) {
            petStore = new PetStore();
        } else {
            petStore = findPetStoreById(petStoreId);
        }
        return petStore;
    }

    public PetStore findPetStoreById(Long petStoreId) {
        return petStoreDao.findById(petStoreId).orElseThrow(() ->
                new NoSuchElementException("PetStore with Id " + petStoreId
                        + " was not found!!"));
    }

    @Transactional(readOnly = false)
    public PetStoreEmployee saveEmployee(Long petStoreId, PetStoreEmployee petStoreEmployee) {
        PetStore petStore = findPetStoreById(petStoreId);
        Employee employee = findOrCreateEmployee(petStoreEmployee.getEmployeeId());
        setEmployeeFields(employee, petStoreEmployee);
        employee.setPetStore(petStore);
        petStore.getEmployees().add(employee);
        Employee dbEmployee = employeeDao.save(employee);
        return new PetStoreEmployee(dbEmployee);
    }

    private void setEmployeeFields(Employee employee, PetStoreEmployee petStoreEmployee) {
        employee.setEmployeeId(petStoreEmployee.getEmployeeId());
        employee.setEmployeeFirstName(petStoreEmployee.getEmployeeFirstName());
        employee.setEmployeeLastName(petStoreEmployee.getEmployeeLastName());
        employee.setEmployeePhone(petStoreEmployee.getEmployeePhone());
        employee.setEmployeeJobTitle(petStoreEmployee.getEmployeeJobTitle());
    }

    private Employee findOrCreateEmployee(Long employeeId) {
        Employee employee;
        if (Objects.isNull(employeeId)) {
            employee = new Employee();
        } else {
            employee = findEmployeeById(employeeId);
        }
        return employee;
    }

    private Employee findEmployeeById(Long employeeId) {
        return employeeDao.findById(employeeId).
                orElseThrow(() -> new NoSuchElementException("Employee with Id " + employeeId
                        + " was not found"));
    }
@Transactional(readOnly = false)
    public PetStoreCustomer saveCustomer(Long petStoreId, PetStoreCustomer petStoreCustomer) {
        PetStore petStore = findPetStoreById(petStoreId);
        Customer customer = findOrCreateCustomer(petStoreId, petStoreCustomer.getCustomerId());
        setCustomerFields(customer, petStoreCustomer);
        customer.getPetStores().add(petStore);
        petStore.getCustomers().add(customer);
        Customer dbCustomer = customerDao.save(customer);
        return new PetStoreCustomer(dbCustomer);
    }

    private void setCustomerFields(Customer customer, PetStoreCustomer petStoreCustomer) {
        customer.setCustomerId(petStoreCustomer.getCustomerId());
        customer.setCustomerFirstName(petStoreCustomer.getCustomerFirstName());
        customer.setCustomerLastName(petStoreCustomer.getCustomerLastName());
        customer.setCustomerEmail(petStoreCustomer.getCustomerEmail());
    }

    private Customer findOrCreateCustomer(Long petStoreId, Long customerId) {
        Customer customer;
        if (Objects.isNull(customerId)) {
            customer = new Customer();
        } else {
            customer = findCustomerById(petStoreId, customerId);
        }
        return customer;
    }

    private Customer findCustomerById(Long petStoreId, Long customerId) {
        Customer customer = customerDao.findById(customerId)
                .orElseThrow(() -> new NoSuchElementException("Customer with Id " + customerId
                        + " was not found"));
        boolean found = false;
        for (PetStore petStore : customer.getPetStores()) {
            if (petStore.getPetStoreId() == petStoreId) {
                found = true;
                break;
            }
            if (!found) {
                throw new IllegalArgumentException("Customer with ID" + customerId + "not found in customer");
            }
        }


        return customer;
    }

    @Transactional(readOnly = true)
    public List<PetStoreData> retrieveAllPetStores() {
        List<PetStore> petStores = petStoreDao.findAll();
        List<PetStoreData> petStoreDataList = new LinkedList<>();
        for (PetStore petStore : petStores) {
            PetStoreData petStoreData = new PetStoreData(petStore);
            petStoreData.getEmployees().clear();
            petStoreData.getCustomers().clear();
            petStoreDataList.add(petStoreData);
        }
        return petStoreDataList;
         //return    petStoreDao.findAll().stream().map(PetStoreData ::new).toList();
    }

    public PetStoreData retrivePetStoreById(Long petStoreId) {
        PetStore petStore = findPetStoreById(petStoreId);
        return new PetStoreData(petStore);
    }

    @Transactional(readOnly = false)
    public void deletePetStoreById(Long petStoreId) {
        PetStore petStore = findPetStoreById(petStoreId);
        petStoreDao.deleteById(petStoreId);

    }
}

