package br.com.teste.insightinfo.domain.dto;

import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HoraExtraDTO implements Comparable<HoraExtraDTO>{
	private LocalTime inicio;
	private LocalTime fim;
	
	@Override
	public int compareTo(HoraExtraDTO o) {
		return this.inicio.compareTo(o.getInicio());
	}
}
