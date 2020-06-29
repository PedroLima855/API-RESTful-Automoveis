package com.api.automoveis.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.automoveis.domain.model.Carro;

@Repository
public interface CarroRepository extends JpaRepository<Carro, Long>{

	//List<Carro> findByNomeStartingWith(String carro);
	
	List<Carro> findByModeloStartingWith(String modelo);
	

}
