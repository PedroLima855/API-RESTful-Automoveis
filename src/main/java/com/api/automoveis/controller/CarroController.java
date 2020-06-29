package com.api.automoveis.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
	
	// Lista todos os registros
	@GetMapping
	public List<Carro> listar(){
		return carroRepository.findAll();
	}

	// faz uma busca atravez do Id e retorna o representation model
	@GetMapping("/{carroId}")
	public ResponseEntity<CarroModel> buscar(@PathVariable Long carroId){
		
		Optional<Carro> carro = carroRepository.findById(carroId);
		
		// essa condição verifica se tem um registro 
		if(carro.isPresent()) {
			CarroModel carroModel = toModel(carro.get());
			return ResponseEntity.ok(carroModel);
		}
		return ResponseEntity.notFound().build();
	}
	
	// Salva um registro passando pelo service
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CarroModel salvar(@Valid @RequestBody CarroInput carroInput) {
		Carro carro = toEntity(carroInput);
		
		return toModel(carroService.salvar(carro));
	}
	
	// Edita um registro
	@PutMapping("/{carroId}")
	public ResponseEntity<Carro> editar(@Valid @PathVariable Long carroId, @RequestBody Carro carro ){
		
		// Essa condição verifica se tem algum registro
		if(!carroRepository.existsById(carroId)) {
			return ResponseEntity.notFound().build();
		}
		
		carro.setId(carroId);
		carro = carroService.salvar(carro);
		
		return ResponseEntity.ok(carro);
	}
	
	// Deleta um registro
	@DeleteMapping("/{carroId}")
	public ResponseEntity<Void> deletar(@PathVariable Long carroId){
		
		if(!carroRepository.existsById(carroId)) {
			return ResponseEntity.notFound().build();
		}
		
		carroService.deletar(carroId);
		
		return ResponseEntity.noContent().build();
	}
	
	// faz uma pesquisa atravez do modelo
	@GetMapping("/pesquisa/{modelo}")
	public List<Carro> pesquisarCarro(@PathVariable String modelo ){
		return carroService.pesquisarModelo(modelo);
	}
	
	// Metodos para reutulizar o modelmapper
	private CarroModel toModel(Carro carro) {
		return modelMapper.map(carro, CarroModel.class);
	}
	
	private Carro toEntity(CarroInput carroInput) {
		return modelMapper.map(carroInput, Carro.class);
	}
}
