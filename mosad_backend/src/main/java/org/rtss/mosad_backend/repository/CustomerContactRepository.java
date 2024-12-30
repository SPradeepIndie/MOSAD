package org.rtss.mosad_backend.repository;


import org.rtss.mosad_backend.entity.customer.Customer;
import org.rtss.mosad_backend.entity.customer.CustomerContact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface CustomerContactRepository extends JpaRepository<CustomerContact,Long> {

    List<CustomerContact> findCustomerContactsByContactNumber(@Param("contactNumber") String contactNumber);

    @Query("SELECT c  FROM CustomerContact c WHERE c.contactNumber = :contactNumber")
    List<Customer> findCustomersByContactNumber(@Param("contactNumber") String contactNumber);


}
