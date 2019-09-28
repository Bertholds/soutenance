package com.codetreatise.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codetreatise.bean.Courier;

public interface CourierRepository extends JpaRepository<Courier, Long> {
	

}
