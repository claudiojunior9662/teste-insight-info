package br.com.teste.insightinfo.service.impl;

import java.util.List;
import java.util.Optional;

import org.hibernate.service.spi.ServiceException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.teste.insightinfo.domain.MarcacaoFeita;
import br.com.teste.insightinfo.domain.dto.MarcacaoFeitaDTO;
import br.com.teste.insightinfo.mapper.MarcacaoFeitaMapper;
import br.com.teste.insightinfo.repository.MarcacaoFeitaRepository;
import br.com.teste.insightinfo.service.MarcacaoFeitaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Transactional
@Service
@RequiredArgsConstructor
@Log4j2
public class MarcacaoFeitaServiceImpl implements MarcacaoFeitaService{
	
	private final MarcacaoFeitaRepository marcacaoFeitaRepository;
	private final MarcacaoFeitaMapper marcacaoFeitaMapper;
	
	@Override
	public MarcacaoFeitaDTO save(MarcacaoFeitaDTO entity) {
		log.debug("Saving an {}: {}", MarcacaoFeita.class, entity);
		if(findOne(entity).isPresent()) {
			throw new ServiceException("Registro já existente");
		}
		return marcacaoFeitaMapper.toDto(marcacaoFeitaRepository.save(marcacaoFeitaMapper.toEntity(entity)));
	}

	@Override
	public MarcacaoFeitaDTO update(MarcacaoFeitaDTO entity) {
		log.debug("Updating an {}: {}", MarcacaoFeita.class, entity);
		if(findOne(entity).isPresent()) {
			throw new ServiceException("Registro já existente");
		}
		return marcacaoFeitaMapper.toDto(marcacaoFeitaRepository.save(marcacaoFeitaMapper.toEntity(entity)));
	}

	@Override
	public Boolean existsById(Long id) {
		log.debug("Veryfing if exists an {} by id: {}", MarcacaoFeita.class, id);
		return marcacaoFeitaRepository.existsById(id);
	}

	@Override
	public List<MarcacaoFeitaDTO> findAll() {
		log.debug("Finding all {}", MarcacaoFeita.class);
		return marcacaoFeitaRepository.findAll().stream().map(marcacaoFeitaMapper::toDto).toList();
	}

	@Override
	public Optional<MarcacaoFeitaDTO> findOne(Long id) {
		log.debug("Finding one {} by id: {}", MarcacaoFeita.class, id);
		return marcacaoFeitaRepository.findById(id).map(marcacaoFeitaMapper::toDto);
	}

	@Override
	public Optional<MarcacaoFeitaDTO> findOne(MarcacaoFeitaDTO example) {
		log.debug("Finding one {} by example: {}", MarcacaoFeita.class, example);
		return marcacaoFeitaRepository.findOne(Example.of(marcacaoFeitaMapper.toEntity(example), returnExampleMatcher())).map(marcacaoFeitaMapper::toDto);
	}

	@Override
	public void delete(Long id) {
		log.debug("Deleting an {} by id: {}", MarcacaoFeita.class, id);
		marcacaoFeitaRepository.deleteById(id);
	}
	
	private ExampleMatcher returnExampleMatcher() {
        return ExampleMatcher
            .matchingAll()
            .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
            .withIgnoreCase();
    }
}
