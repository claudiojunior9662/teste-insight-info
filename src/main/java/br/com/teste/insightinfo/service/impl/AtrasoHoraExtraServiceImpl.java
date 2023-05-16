package br.com.teste.insightinfo.service.impl;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.springframework.stereotype.Service;

import br.com.teste.insightinfo.domain.dto.AtrasoDTO;
import br.com.teste.insightinfo.domain.dto.HoraExtraDTO;
import br.com.teste.insightinfo.domain.dto.HorarioTrabalhoDTO;
import br.com.teste.insightinfo.domain.dto.MarcacaoFeitaDTO;
import br.com.teste.insightinfo.service.AtrasoHoraExtraService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Service
@RequiredArgsConstructor
@Getter
@Setter
public class AtrasoHoraExtraServiceImpl implements AtrasoHoraExtraService{
	
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	private class IntervalIndicator implements Comparable<IntervalIndicator>{
		private LocalTime inicio;
		private LocalTime fim;
		private boolean isAtraso;
		
		@Override
		public int compareTo(IntervalIndicator o) {
			return this.inicio.compareTo(o.getInicio());
		}
	}
	
	private List<HorarioTrabalhoDTO> horariosTrabalho = new ArrayList<HorarioTrabalhoDTO>();
	private List<MarcacaoFeitaDTO> marcacoesFeitas = new ArrayList<MarcacaoFeitaDTO>();
	
	/**
	 * Método responsável por retornar uma lista de elementos {@link AtrasoDTO}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<AtrasoDTO> calculaAtrasos(List<HorarioTrabalhoDTO> horariosTrabalho,
			List<MarcacaoFeitaDTO> marcacoesFeitas) {
		List<AtrasoDTO> preResult = new ArrayList<AtrasoDTO>();
		
		if(horariosTrabalho.isEmpty() || marcacoesFeitas.isEmpty() || verifyIfMarkedTimesIncludesAllWorkingHours(horariosTrabalho, marcacoesFeitas)) {
			return preResult;
		}
		
		setHorariosTrabalho(horariosTrabalho);
		setMarcacoesFeitas(marcacoesFeitas);
		
		getHorariosTrabalho().forEach(horario -> {
			getMarcacoesFeitas().forEach(marcacao -> {
				Set<IntervalIndicator> intervalosAtraso = returnIntervalsBetweenTimes(
						horario, 
						marcacao);
				intervalosAtraso.forEach(intervalo -> {
					if(intervalo.isAtraso()) {
						preResult.add(new AtrasoDTO(intervalo.getInicio(), intervalo.getFim()));
					}
				});
			});
		});
		
		Set<IntervalIndicator> intervalosEntreHorarios = returnMiddleIntervalsBetweenWorkTimes(horariosTrabalho);
		intervalosEntreHorarios.addAll(returnMiddleIntervalsBetweenMarkedTimes(marcacoesFeitas));
		
		List<AtrasoDTO> result = removeDuplicateElements(preResult);
		result.sort(null);
		
		return result;
	}

	/**
	 * Método responsável por retornar uma lista de elementos {@link HoraExtraDTO}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<HoraExtraDTO> calculaHorasExtras(List<MarcacaoFeitaDTO> marcacoesFeitas,
			List<HorarioTrabalhoDTO> horariosTrabalho) {
		List<HoraExtraDTO> preResult = new ArrayList<HoraExtraDTO>();
		
		if(horariosTrabalho.isEmpty() || marcacoesFeitas.isEmpty()) {
			return preResult;
		}
		
		setHorariosTrabalho(horariosTrabalho);
		setMarcacoesFeitas(marcacoesFeitas);
		
		getHorariosTrabalho().forEach(horario -> {
			getMarcacoesFeitas().forEach(marcacao -> {
				Set<IntervalIndicator> intervalosHorasExtras = returnIntervalsBetweenTimes(
						horario, 
						marcacao);
				intervalosHorasExtras.forEach(intervalo -> {
					if(!intervalo.isAtraso()) {
						preResult.add(new HoraExtraDTO(intervalo.getInicio(), intervalo.getFim()));
					}
				});
			});
		});
		
		Set<IntervalIndicator> intervalosEntreHorarios = returnMiddleIntervalsBetweenWorkTimes(horariosTrabalho);
		intervalosEntreHorarios.addAll(returnMiddleIntervalsBetweenMarkedTimes(marcacoesFeitas));
		
		marcacoesFeitas.forEach(marcacaoFeita -> {
			intervalosEntreHorarios.forEach(interval -> {
				if(interval.getInicio().compareTo(marcacaoFeita.getEntrada()) >= 0 && 
				interval.getFim().compareTo(marcacaoFeita.getSaida()) <= 0) {
					preResult.add(
							new HoraExtraDTO(interval.getInicio(), interval.getFim()));
				}
			});
		});
		
		List<HoraExtraDTO> result = removeDuplicateElements(preResult);
		result.sort(null);
				
		return result;
	}
	
	/**
	 * Método responsável por comparar horário de trabalho e marcação feita e retornar os intervalos
	 * (Atraso ou HoraExtra) entre eles
	 * 
	 * @author Claudio Júnior
	 * @since 13/05/2023
	 * @param horarioTrabalho
	 * @param marcacaoFeita
	 * @return
	 */
	private Set<IntervalIndicator> returnIntervalsBetweenTimes(
			HorarioTrabalhoDTO horarioTrabalho, 
			MarcacaoFeitaDTO marcacaoFeita) {
		Set<IntervalIndicator> result = new HashSet<IntervalIndicator>();
		
		if(horarioTrabalho.getEntrada().equals(marcacaoFeita.getEntrada())) {
			if(horarioTrabalho.getSaida().equals(marcacaoFeita.getSaida())) {
				// são os mesmos horários, então retorna uma lista sem intervalos
				return result;
			} else {
				// o horário de saída é diferente, então retorna o intervalo
				// pega a menor hora de saída
				if(horarioTrabalho.getSaida().compareTo(marcacaoFeita.getSaida()) > 0) {
					// a hora fim de 'horarioTrabalho' é maior que a hora fim de 'marcacaoFeita'
					result = verifyValidIntervalAndAdd(
							result, new IntervalIndicator(marcacaoFeita.getSaida(), horarioTrabalho.getSaida(), true));
				} else {
					// a hora fim de 'marcacaoFeita' é maior que a hora fim de 'horarioTrabalho'
					result = verifyValidIntervalAndAdd(
							result, 
							new IntervalIndicator(horarioTrabalho.getSaida(), marcacaoFeita.getSaida(), false));
				}
			}
		} else {
			// a hora de início é diferente, verifica qual começa primeiro
			if(horarioTrabalho.getEntrada().compareTo(horarioTrabalho.getSaida()) > 0) {
				// ultrapassa o dia atual (entra na madrugada)
				if(horarioTrabalho.getEntrada().compareTo(marcacaoFeita.getEntrada()) > 0) {
					// a hora início de 'horarioTrabalho' é maior que a hora início de 'marcacaoFeita'
					result = verifyValidIntervalAndAdd(
							result, 
							new IntervalIndicator(marcacaoFeita.getEntrada(), horarioTrabalho.getEntrada(), false));
				} else {
					//  a hora início de 'marcacaoFeita' é maior que a hora início de 'horarioTrabalho'
					result = verifyValidIntervalAndAdd(
							result, 
							new IntervalIndicator(horarioTrabalho.getEntrada(), marcacaoFeita.getEntrada(), true));
				}
				
			} else {
				// a hora de início é diferente, verifica qual começa primeiro
				if(horarioTrabalho.getEntrada().compareTo(marcacaoFeita.getEntrada()) > 0) {
					// a hora início de 'horarioTrabalho' é maior que a hora início de 'marcacaoFeita'
					result = verifyValidIntervalAndAdd(
							result, 
							new IntervalIndicator(marcacaoFeita.getEntrada(), horarioTrabalho.getEntrada(), false));
				} else {
					//  a hora início de 'marcacaoFeita' é maior que a hora início de 'horarioTrabalho'
					result = verifyValidIntervalAndAdd(
							result, 
							new IntervalIndicator(horarioTrabalho.getEntrada(), marcacaoFeita.getEntrada(), true));
				}
			}
			
			LocalTime newTime = LocalTime.now();			
			result.addAll(returnIntervalsBetweenTimes(
					new HorarioTrabalhoDTO(newTime, horarioTrabalho.getSaida()), 
					new MarcacaoFeitaDTO(newTime, marcacaoFeita.getSaida())));
		}
		
		return result;
	}
	
	/**
	 * Retorna os intervalos entre os registros de {@link HorarioTrabalhoDTO}}
	 * 
	 * @author Claudio Júnior
	 * @since 13/05/2023
	 * @param horariosTrabalho
	 * @return
	 */
	private Set<IntervalIndicator> returnMiddleIntervalsBetweenWorkTimes(List<HorarioTrabalhoDTO> horariosTrabalho) {
		Set<IntervalIndicator> result = new HashSet<IntervalIndicator>();
		
		if(horariosTrabalho.size() == 1) {
			return result;
		}
		
		for(int i = 0; i < horariosTrabalho.size(); i++) {
			IntervalIndicator interval = new IntervalIndicator();
			interval.setInicio(horariosTrabalho.get(i).getSaida());
			if(i < horariosTrabalho.size() - 1) {
				interval.setFim(horariosTrabalho.get(i + 1).getEntrada());
				interval.setAtraso(false);
				result.add(interval);
			}
		}
		
		return result;
	}
	
	/**
	 * Retorna os intervalos entre os registros de {@link MarcacaoFeitaDTO}
	 * 
	 * @author Claudio Júnior
	 * @since 13/05/2023
	 * @param marcacoesFeitas
	 * @return
	 */
	private Set<IntervalIndicator> returnMiddleIntervalsBetweenMarkedTimes(List<MarcacaoFeitaDTO> marcacoesFeitas) {
		Set<IntervalIndicator> result = new HashSet<IntervalIndicator>();
		
		if(marcacoesFeitas.size() == 1) {
			return result;
		}
		
		for(int i = 0; i < marcacoesFeitas.size(); i++) {
			IntervalIndicator interval = new IntervalIndicator();
			interval.setInicio(marcacoesFeitas.get(i).getSaida());
			if(i < marcacoesFeitas.size() - 1) {
				interval.setFim(marcacoesFeitas.get(i + 1).getEntrada());
				interval.setAtraso(false);
				result.add(interval);
			}
		}
		
		return result;
	}
	
	/**
	 * Verifica se uma marcação feita está contida em todos os horários de trabalho
	 * 
	 * @author Claudio Júnior
	 * @since 15/05/2023
	 * @param horariosTrabalho
	 * @param marcacoesFeitas
	 * @return
	 */
	private boolean verifyIfMarkedTimesIncludesAllWorkingHours(
			List<HorarioTrabalhoDTO> horariosTrabalho, 
			List<MarcacaoFeitaDTO> marcacoesFeitas) {	
		boolean includes = false;
		
		for(HorarioTrabalhoDTO horarioTrabalho : horariosTrabalho) {
			for(MarcacaoFeitaDTO marcacaoFeita : marcacoesFeitas) {
				if(horarioTrabalho.getEntrada().compareTo(horarioTrabalho.getSaida()) > 0) {
					// ultrapassa o dia atual (entra na madrugada)
					if(horarioTrabalho.getEntrada().compareTo(marcacaoFeita.getEntrada()) <= 0 && 
					horarioTrabalho.getSaida().compareTo(marcacaoFeita.getSaida()) >= 0) {
						includes = true;
					} else {
						includes = false;
					}
				} else {
					if(horarioTrabalho.getEntrada().compareTo(marcacaoFeita.getEntrada()) >= 0 && 
					horarioTrabalho.getSaida().compareTo(marcacaoFeita.getSaida()) <= 0) {
						includes = true;
					} else {
						includes = false;
					}
				}
			}
		}
		return includes;
	}
	
	/**
	 * Remove os elementos duplicados dentro de uma java.util.List
	 * 
	 * @author Claudio Júnior
	 * @since 14/05/2023
	 * @param list
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List removeDuplicateElements(List list) {
		List copyList = new ArrayList();
		for(Object o : list) {
			if(!copyList.contains(o)) {
				copyList.add(o);
			}
		}
		return copyList;
	}
	
	/**
	 * Verifica se um intervalo é válido e o adiciona no retorno solicitado
	 * 
	 * @author Claudio Júnior
	 * @since 16/05/2023
	 * @param intervalosAdicionados
	 * @param novoIntervalo
	 * @return
	 */
	private Set<IntervalIndicator> verifyValidIntervalAndAdd(
			Set<IntervalIndicator> intervalosAdicionados, 
			IntervalIndicator novoIntervalo) {
		if(novoIntervalo.getFim().getHour() - novoIntervalo.getInicio().getHour() > 8 && 
			(novoIntervalo.getFim().getHour() < 6 || novoIntervalo.getInicio().getHour() < 6)) {
			LocalTime temp = novoIntervalo.getFim();
			novoIntervalo.setFim(novoIntervalo.getInicio());
			novoIntervalo.setInicio(temp);
			novoIntervalo.setAtraso(novoIntervalo.isAtraso() ? false : true);
		}		
		DateTime dateTimeEntrada = convertLocalTimeToDateTime(novoIntervalo.getInicio());		
		DateTime dateTimeSaida = convertLocalTimeToDateTime(novoIntervalo.getFim());
		
		if(dateTimeEntrada.compareTo(dateTimeSaida) > 0) {
			dateTimeSaida = dateTimeSaida.plusDays(1);
		}
		
		Interval compareInterval = new Interval(
				dateTimeEntrada, 
				dateTimeSaida);
		boolean contido = false;
		
		if(novoIntervalo.isAtraso) {
			for(MarcacaoFeitaDTO marcacaoFeita : getMarcacoesFeitas()) {				
				dateTimeEntrada = convertLocalTimeToDateTime(marcacaoFeita.getEntrada());				
				dateTimeSaida = convertLocalTimeToDateTime(marcacaoFeita.getSaida());
				
				if(dateTimeEntrada.compareTo(dateTimeSaida) > 0) {
					dateTimeSaida = dateTimeSaida.plusDays(1);
				}
				
				Interval compareMarcacaoFeita = new Interval(
						dateTimeEntrada, 
						dateTimeSaida);
				if(compareInterval.overlaps(compareMarcacaoFeita)) {
					contido = true;
				}
			}
		} else {
			for(HorarioTrabalhoDTO horarioTrabalho : getHorariosTrabalho()) {
				dateTimeEntrada = convertLocalTimeToDateTime(horarioTrabalho.getEntrada());				
				dateTimeSaida = convertLocalTimeToDateTime(horarioTrabalho.getSaida());
				
				if(dateTimeEntrada.compareTo(dateTimeSaida) > 0) {
					dateTimeSaida = dateTimeSaida.plusDays(1);
				}
				
				Interval compareHorarioTrabalho = new Interval(
						dateTimeEntrada, 
						dateTimeSaida);
				if(compareInterval.overlaps(compareHorarioTrabalho)) {
					contido = true;
				}
			}
		}
		
		if(!contido) {
			intervalosAdicionados.add(novoIntervalo);
		}
		
		return intervalosAdicionados;
	}
	
	/**
	 * Método auxiliar para converter {@link LocalTime} para {@link DateTime}
	 * 
	 * @author Claudio Júnior
	 * @since 16/05/2023
	 * @see https://www.benchresources.net/java-8-how-to-convert-localtime-to-an-instant/
	 * @param value
	 * @return
	 */
	private DateTime convertLocalTimeToDateTime(LocalTime value) {
		Instant instant = value // LocalTime
                .atDate(LocalDate.now()) // LocalDateTime
                .atZone(ZoneId.systemDefault()) // ZonedDateTime
                .toInstant(); // Instant
		return new DateTime(instant.getLong(ChronoField.INSTANT_SECONDS));
	}
}
