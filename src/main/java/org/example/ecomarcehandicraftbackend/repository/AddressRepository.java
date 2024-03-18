package org.example.ecomarcehandicraftbackend.repository;

import org.example.ecomarcehandicraftbackend.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
