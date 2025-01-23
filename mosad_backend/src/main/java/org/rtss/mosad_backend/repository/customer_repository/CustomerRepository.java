package org.rtss.mosad_backend.repository.customer_repository;

import org.rtss.mosad_backend.entity.customer.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findByNameAndContact(String name, String contact);
}
