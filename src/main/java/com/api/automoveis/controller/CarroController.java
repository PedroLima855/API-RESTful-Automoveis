package com.api.automoveis.controller;

import com.api.automoveis.domain.model.Carro;
import com.api.automoveis.dto.CarroInput;
import com.api.automoveis.dto.CarroModel;
import com.api.automoveis.repository.CarroRepository;
import com.api.automoveis.service.CarroService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/carros")
@Api("Automoveis API REST")
@CrossOrigin(origins = "*")
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
	@ApiOperation(value = "Lista todos os registros")
	@ResponseStatus(HttpStatus.OK)
	public List<Carro> listar(){
		return carroService.listarCarros();
	}

	// faz uma busca atravez do Id e retorna o representation model
	@GetMapping("/{id}")
	@ApiOperation(value = "Lista os registros por id")
	public ResponseEntity<CarroModel> buscar(@PathVariable Long id){
		return carroService.buscarId(id);
	}

	// faz uma pesquisa atravez do modelo
	@GetMapping("/pesquisa/{modelo}")
	@ApiOperation(value = "Pesquisa um registro atravez do modelo")
	public List<Carro> pesquisarCarro(@PathVariable String modelo ){
		return carroService.pesquisarModelo(modelo);
	}
	
	// Salva um registro passando pelo service
	@PostMapping
	@ApiOperation(value = "Salva um registro")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<CarroModel> salvar(@Valid @RequestBody Carro carro) {
		return carroService.salvar(carro);
	}
	
	// Edita um registro
	@PutMapping("/{carroId}")
	@ApiOperation(value = "Edita um registro")
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
	@ApiOperation(value = "Deleta um registro")
	public ResponseEntity<Void> deletar(@PathVariable Long carroId){
		
		if(!carroRepository.existsById(carroId)) {
			return ResponseEntity.notFound().build();
		}
		
		carroService.deletar(carroId);
		
		return ResponseEntity.noContent().build();
	}
	
	// Metodos para reutulizar o modelmapper
	private CarroModel toModel(Carro carro) {
		return modelMapper.map(carro, CarroModel.class);
	}
	
	private Carro toEntity(CarroInput carroInput) {
		return modelMapper.map(carroInput, Carro.class);
	}
}
