package com.api.automoveis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.automoveis.domain.model.Carro;

@Repository
public interface CarroRepository extends JpaRepository<Carro, Long>{
	

}
