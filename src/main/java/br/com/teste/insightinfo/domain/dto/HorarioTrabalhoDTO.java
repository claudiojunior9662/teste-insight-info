package br.com.teste.insightinfo.domain.dto;

import java.time.LocalTime;

import lombok.Data;

@Data
public class HorarioTrabalhoDTO {
	private Long id;
	private LocalTime entrada;
	private LocalTime saida;
}
