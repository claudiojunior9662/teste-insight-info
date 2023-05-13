package br.com.teste.insightinfo.mapper;

import org.mapstruct.Mapper;

import br.com.teste.insightinfo.domain.MarcacaoFeita;
import br.com.teste.insightinfo.domain.dto.MarcacaoFeitaDTO;

@Mapper(componentModel = "spring")
public interface MarcacaoFeitaMapper extends EntityMapper<MarcacaoFeitaDTO, MarcacaoFeita> {}
