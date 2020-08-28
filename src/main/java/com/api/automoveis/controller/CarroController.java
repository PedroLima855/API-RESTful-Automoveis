package com.api.automoveis.controller;

import com.api.automoveis.domain.model.Carro;
import com.api.automoveis.dto.CarroInput;
import com.api.automoveis.dto.CarroModel;
import com.api.automoveis.repository.CarroRepository;
import com.api.automoveis.service.CarroService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/carros")
public class CarroController {
	

	private ModelMapper modelMapper;
	private CarroRepository carroRepository;
	private CarroService carroService;

	public CarroController(ModelMapper modelMapper, CarroRepository carroRepository, CarroService carroService) {
		this.modelMapper = modelMapper;
		this.carroRepository = carroRepository;
		this.carroService = carroService;
	}

	// Lista todos os registros
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<Carro> listar(){
		return carroService.listarCarros();
	}

	// faz uma busca atravez do Id e retorna o representation model
	@GetMapping("/{id}")
	public ResponseEntity<CarroModel> buscar(@PathVariable Long id){
		return carroService.buscarId(id);
	}
	
	// Salva um registro passando pelo service
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<CarroModel> salvar(@Valid @RequestBody Carro carro) {
		return carroService.salvar(carro);
	}
	
	// Edita um registro
	@PutMapping("/{carroId}")
	public ResponseEntity<Carro> editar(@Valid @PathVariable Long carroId, @RequestBody Carro carro ){
		
		// Essa condição verifica se tem algum registro
		if(!carroRepository.existsById(carroId)) {
			return ResponseEntity.notFound().build();
		}
		carro.setId(carroId);
		
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
