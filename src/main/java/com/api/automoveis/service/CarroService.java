package com.api.automoveis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.automoveis.domain.model.Carro;
import com.api.automoveis.repository.CarroRepository;


@Service
public class CarroService {
	
	@Autowired
	private CarroRepository carroRepository;

	public Carro salvar(Carro carro) {
		return carroRepository.save(carro);
	}
	
	public void deletar(Long carroId) {
		carroRepository.deleteById(carroId);
	}
}
