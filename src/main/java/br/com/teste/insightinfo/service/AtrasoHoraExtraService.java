package br.com.teste.insightinfo.service;

import java.util.List;

import br.com.teste.insightinfo.domain.dto.AtrasoDTO;
import br.com.teste.insightinfo.domain.dto.HoraExtraDTO;
import br.com.teste.insightinfo.domain.dto.HorarioTrabalhoDTO;
import br.com.teste.insightinfo.domain.dto.MarcacaoFeitaDTO;

public interface AtrasoHoraExtraService {
	List<AtrasoDTO> calculaAtrasos(List<HorarioTrabalhoDTO> horariosTrabalho, List<MarcacaoFeitaDTO> marcacoesFeitas);
	List<HoraExtraDTO> calculaHorasExtras(List<MarcacaoFeitaDTO> horariosTrabalho, List<HorarioTrabalhoDTO> marcacoesFeitas);
}
