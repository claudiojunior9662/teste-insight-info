package br.com.teste.insightinfo.domain.dto;

import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AtrasoDTO implements Comparable<AtrasoDTO>{
	private LocalTime inicio;
	private LocalTime fim;
	
	@Override
	public int compareTo(AtrasoDTO o) {
		return this.inicio.compareTo(o.getInicio());
	}
}
