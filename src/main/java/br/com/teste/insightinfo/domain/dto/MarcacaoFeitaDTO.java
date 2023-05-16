package br.com.teste.insightinfo.domain.dto;

import java.time.LocalTime;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MarcacaoFeitaDTO implements Comparable<MarcacaoFeitaDTO>{
	private Long id;
	private LocalTime entrada;
	private LocalTime saida;
	
	@Override
	public int compareTo(MarcacaoFeitaDTO o) {
		return this.entrada.compareTo(o.getEntrada());
	}

	public MarcacaoFeitaDTO(LocalTime entrada, LocalTime saida) {
		this.entrada = entrada;
		this.saida = saida;
	}
}
