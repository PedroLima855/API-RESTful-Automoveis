package com.api.automoveis.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.api.automoveis.domain.model.Carro;
import com.api.automoveis.repository.CarroRepository;
import com.api.automoveis.representation.model.CarroInput;
import com.api.automoveis.representation.model.CarroModel;
import com.api.automoveis.service.CarroService;

@RestController
@RequestMapping("/carros")
public class CarroController {
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private CarroRepository carroRepository;
	
	@Autowired
	private CarroService carroService;
	
	// Lista todos os Carros 
	@GetMapping
	public List<Carro> listar(){
		return carroRepository.findAll();
	}

	// faz uma busca atravez do Id e retorna o representation model
	@GetMapping("/{carroId}")
	public ResponseEntity<CarroModel> buscar(@PathVariable Long carroId){
		
		Optional<Carro> carro = carroRepository.findById(carroId);
		
		if(carro.isPresent()) {
			CarroModel carroModel = toModel(carro.get());
			return ResponseEntity.ok(carroModel);
		}
		return ResponseEntity.ok().build();
	}
	
	// Salva um registro passando pelo service
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CarroModel salvar(@Valid @RequestBody CarroInput carroInput) {
		Carro carro = toEntity(carroInput);
		
		return toModel(carroService.salvar(carro));
	}
	
	
	// Metodos para reutulizar o modelmapper
	private CarroModel toModel(Carro carro) {
		return modelMapper.map(carro, CarroModel.class);
	}
	
	private Carro toEntity(CarroInput carroInput) {
		return modelMapper.map(carroInput, Carro.class);
	}
}
