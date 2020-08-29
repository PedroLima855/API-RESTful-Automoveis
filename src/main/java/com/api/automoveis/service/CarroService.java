package com.api.automoveis.service;

import com.api.automoveis.domain.model.Carro;
import com.api.automoveis.dto.CarroModel;
import com.api.automoveis.repository.CarroRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class CarroService {
	

	private CarroRepository carroRepository;
	private ModelMapper modelMapper;

	public CarroService(CarroRepository carroRepository, ModelMapper modelMapper) {
		this.carroRepository = carroRepository;
		this.modelMapper = modelMapper;
	}

	// lista todos os carros
	public List<Carro> listarCarros(){
		return carroRepository.findAll();
	}

	// faz uma busca por id
	public ResponseEntity<CarroModel> buscarId(Long id){

		Optional<Carro> carro = carroRepository.findById(id);

		// essa condição verifica se tem um registro
		if(carro.isPresent()){
			CarroModel carroModel = toDTO(carro.get());
			return ResponseEntity.ok(carroModel);
		}

		return ResponseEntity.notFound().build();

	}

	// salva um registro
	public ResponseEntity<CarroModel> salvar(Carro carro) {

		Carro carroSalvo = carroRepository.save(carro);
		CarroModel carroModel = toDTO(carroSalvo);

		return ResponseEntity.status(HttpStatus.CREATED).body(carroModel);

	}
	
	public void deletar(Long carroId) {
		carroRepository.deleteById(carroId);
	}
	
	public List<Carro> pesquisarModelo (String modelo) {
		return carroRepository.findByModeloStartingWith(modelo);
	}

	// Metodo para mapear DTO
	private CarroModel toDTO(Carro carro) {
		return modelMapper.map(carro, CarroModel.class);
	}

	
}
