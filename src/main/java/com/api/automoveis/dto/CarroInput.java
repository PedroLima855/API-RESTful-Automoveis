package com.api.automoveis.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CarroInput {
	
	@NotBlank
	private String cor;
	
	@NotBlank
	private String fabricante;
	
	@NotBlank
	private String modelo;
	
	@NotNull
	private Integer ano;
	
	// Getts e Setts
	public String getCor() {
		return cor;
	}
	public void setCor(String cor) {
		this.cor = cor;
	}
	public String getFabricante() {
		return fabricante;
	}
	public void setFabricante(String fabricante) {
		this.fabricante = fabricante;
	}
	public String getModelo() {
		return modelo;
	}
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	public Integer getAno() {
		return ano;
	}
	public void setAno(Integer ano) {
		this.ano = ano;
	}
	
	

}
