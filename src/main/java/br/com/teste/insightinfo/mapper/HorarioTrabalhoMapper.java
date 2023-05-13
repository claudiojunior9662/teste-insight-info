package br.com.teste.insightinfo.mapper;

import org.mapstruct.Mapper;

import br.com.teste.insightinfo.domain.HorarioTrabalho;
import br.com.teste.insightinfo.domain.dto.HorarioTrabalhoDTO;

@Mapper(componentModel = "spring")
public interface HorarioTrabalhoMapper extends EntityMapper<HorarioTrabalhoDTO, HorarioTrabalho> {}
