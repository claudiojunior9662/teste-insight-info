package br.com.teste.insightinfo.domain.dto;

import java.time.LocalTime;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class HorarioTrabalhoDTO implements Comparable<HorarioTrabalhoDTO>{
	private Long id;
	private LocalTime entrada;
	private LocalTime saida;
	
	@Override
	public int compareTo(HorarioTrabalhoDTO o) {
		return this.entrada.compareTo(o.getEntrada());
	}

	public HorarioTrabalhoDTO(LocalTime entrada, LocalTime saida) {
		this.entrada = entrada;
		this.saida = saida;
	}
}
