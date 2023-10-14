package pet.store.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pet.store.entity.PetStore;

import java.util.Set;

public interface PetStoreDao extends JpaRepository<PetStore, Long> {

}
