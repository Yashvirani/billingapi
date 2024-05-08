package com.billing.software.Dao;

import com.billing.software.Model.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

public interface CustomerDao extends CrudRepository<Customer,Long> {
    List<Customer> findByEmail(String email);
}
