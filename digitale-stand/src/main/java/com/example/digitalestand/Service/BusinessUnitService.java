package com.example.digitalestand.Service;

import com.example.digitalestand.model.BusinessUnit;
import com.example.digitalestand.repository.BusinessUnitRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BusinessUnitService {

    private final BusinessUnitRepository repository;

    @Autowired
    public BusinessUnitService(BusinessUnitRepository repository) {
        this.repository = repository;
    }

    public List<BusinessUnit> getAllBusinessUnits() {
        return repository.findAll();
    }

    public Optional<BusinessUnit> getBusinessUnitById(String id) {
        return repository.findById(id);
    }

    public BusinessUnit createBusinessUnit(BusinessUnit businessUnit) {
        return repository.save(businessUnit);
    }

    public List<BusinessUnit> createBusinessUnits(List<BusinessUnit> businessUnits) {
        return repository.saveAll(businessUnits);
    }

    public BusinessUnit updateBusinessUnit(String id, BusinessUnit businessUnit) {
        businessUnit.setId(id); // Ensure the ID is set for update
        return repository.save(businessUnit);
    }

    public void deleteBusinessUnit(String id) {
        repository.deleteById(id);
    }

    public BusinessUnit getBusinessUnitWithChildren(String id, int depth) {
        return getBusinessUnitWithChildrenRecursive(id, depth);
    }

    private BusinessUnit getBusinessUnitWithChildrenRecursive(String id, int depth) {
        Optional<BusinessUnit> optionalBusinessUnit = repository.findById(id);
        if (optionalBusinessUnit.isEmpty()) {
            return null;
        }
        BusinessUnit businessUnit = optionalBusinessUnit.get();
        if (depth > 0) {
            List<BusinessUnit> children = repository.findByParentId(id);
            List<BusinessUnit> childrenWithSubChildren = new ArrayList<>();
            for (BusinessUnit child : children) {
                BusinessUnit childWithSubChildren = getBusinessUnitWithChildrenRecursive(child.getId(), depth - 1);
                childrenWithSubChildren.add(childWithSubChildren);
            }
            businessUnit.setChildren(childrenWithSubChildren);
        }
        return businessUnit;
    }

    public List<BusinessUnit> getChildren(String parentId) {
        return repository.findByParentId(parentId);
    }

    public BusinessUnit addChild(String parentId, BusinessUnit child) {
        child.setParentId(parentId);
        return repository.save(child);
    }

    public void removeChild(String parentId, String childId) {
        repository.deleteById(childId);
    }
}
