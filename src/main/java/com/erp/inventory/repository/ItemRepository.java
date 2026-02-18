package com.erp.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.erp.inventory.model.Item;

public interface ItemRepository extends JpaRepository<Item, Long> { }