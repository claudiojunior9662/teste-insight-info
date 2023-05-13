package br.com.teste.insightinfo.domain.dto;

import java.time.LocalTime;

import lombok.Data;

@Data
public class MarcacaoFeitaDTO {
	private Long id;
	private LocalTime entrada;
	private LocalTime saida;
}
