package com.api.automoveis.service;

import com.api.automoveis.domain.model.Carro;
import com.api.automoveis.repository.CarroRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CarroService {
	

	private CarroRepository carroRepository;

	public CarroService(CarroRepository carroRepository) {
		this.carroRepository = carroRepository;
	}

	// lista todos os carros
	public List<Carro> listarCarros(){
		return carroRepository.findAll();
	}

	public Carro salvar(Carro carro) {
		return carroRepository.save(carro);
	}
	
	public void deletar(Long carroId) {
		carroRepository.deleteById(carroId);
	}
	
	public List<Carro> pesquisarModelo (String modelo) {
		return carroRepository.findByModeloStartingWith(modelo);
	}
	
}
