package com.example.digitalestand.controller;

import com.example.digitalestand.Service.BusinessUnitService;
import com.example.digitalestand.model.BusinessUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/businessUnits")
public class BusinessUnitController {

    private final BusinessUnitService service;

    @Autowired
    public BusinessUnitController(BusinessUnitService service) {
        this.service = service;
    }

    @GetMapping("/organizations")
    public List<BusinessUnit> getAllBusinessUnits() {
        return service.getAllBusinessUnits();
    }

    @GetMapping("/organizations/{id}")
    public ResponseEntity<BusinessUnit> getBusinessUnitById(@PathVariable String id) {
        return service.getBusinessUnitById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/tree/{parentId}/{depth}")
    public ResponseEntity<BusinessUnit> getBusinessUnitTree(@PathVariable String parentId, @PathVariable int depth) {
        try {
            BusinessUnit businessUnit = service.getBusinessUnitWithChildren(parentId, depth);
            return ResponseEntity.ok(businessUnit);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/organizations")
    public ResponseEntity<BusinessUnit> createBusinessUnit(@RequestBody BusinessUnit businessUnit) {
        BusinessUnit createdUnit = service.createBusinessUnit(businessUnit);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUnit);
    }

    @PostMapping("/organizations/batch")
    public ResponseEntity<List<BusinessUnit>> createBusinessUnits(@RequestBody List<BusinessUnit> businessUnits) {
        List<BusinessUnit> createdUnits = service.createBusinessUnits(businessUnits);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUnits);
    }

    @PutMapping("/organizations/{id}")
    public ResponseEntity<BusinessUnit> updateBusinessUnit(@PathVariable String id, @RequestBody BusinessUnit businessUnit) {
        try {
            return ResponseEntity.ok(service.updateBusinessUnit(id, businessUnit));
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/organizations/{id}")
    public ResponseEntity<Void> deleteBusinessUnit(@PathVariable String id) {
        try {
            service.deleteBusinessUnit(id);
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/organizations/{parentId}/children")
    public ResponseEntity<List<BusinessUnit>> getChildren(@PathVariable String parentId) {
        List<BusinessUnit> children = service.getChildren(parentId);
        return ResponseEntity.ok(children);
    }

    @PostMapping("/organizations/{parentId}/children")
    public ResponseEntity<BusinessUnit> addChild(@PathVariable String parentId, @RequestBody BusinessUnit child) {
        BusinessUnit createdChild = service.addChild(parentId, child);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdChild);
    }

    @DeleteMapping("/organizations/{parentId}/children/{childId}")
    public ResponseEntity<Void> removeChild(@PathVariable String parentId, @PathVariable String childId) {
        service.removeChild(parentId, childId);
        return ResponseEntity.noContent().build();
    }
}
