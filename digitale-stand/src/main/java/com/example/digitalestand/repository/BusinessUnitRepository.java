package com.example.digitalestand.repository;

import com.example.digitalestand.model.BusinessUnit;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// This interface will be used to handle requests related to the businessUnit model
@Repository
public interface BusinessUnitRepository extends MongoRepository<BusinessUnit, String> {
    List<BusinessUnit> findByParentId(String parentId);

    // No need to define these methods, they are provided by MongoRepository
    // List<BusinessUnit> findAll();
    // Optional<BusinessUnit> findById(String id);
    // void deleteById(String id);

    // No need to define a 'create' method, save method is used for creating and updating
    // business units.

}
