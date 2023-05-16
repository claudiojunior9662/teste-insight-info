package br.com.teste.insightinfo.service.impl;

import java.util.List;
import java.util.Optional;

import org.hibernate.service.spi.ServiceException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.teste.insightinfo.domain.MarcacaoFeita;
import br.com.teste.insightinfo.domain.dto.HorarioTrabalhoDTO;
import br.com.teste.insightinfo.mapper.HorarioTrabalhoMapper;
import br.com.teste.insightinfo.repository.HorarioTrabalhoRepository;
import br.com.teste.insightinfo.service.HorarioTrabalhoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Transactional
@Service
@RequiredArgsConstructor
@Log4j2
public class HorarioTrabalhoServiceImpl implements HorarioTrabalhoService{

	private final HorarioTrabalhoRepository horarioTrabalhoRepository;
	private final HorarioTrabalhoMapper horarioTrabalhoMapper;
	
	@Override
	public HorarioTrabalhoDTO save(HorarioTrabalhoDTO entity) {
		log.debug("Saving an {}: {}", MarcacaoFeita.class, entity);
		if(horarioTrabalhoRepository.count() == 3) {
			throw new ServiceException("Número máximo de registros excedido");
		}
		if(findOne(entity).isPresent()) {
			throw new ServiceException("Registro já existente");
		}
		return horarioTrabalhoMapper.toDto(horarioTrabalhoRepository.save(horarioTrabalhoMapper.toEntity(entity)));
	}
	
	@Override
	public HorarioTrabalhoDTO update(HorarioTrabalhoDTO entity) {
		log.debug("Updating an {}: {}", MarcacaoFeita.class, entity);
		if(findOne(entity).isPresent()) {
			throw new ServiceException("Registro já existente");
		}
		return horarioTrabalhoMapper.toDto(horarioTrabalhoRepository.save(horarioTrabalhoMapper.toEntity(entity)));
	}
	
	@Override
	public Boolean existsById(Long id) {
		log.debug("Veryfing if exists an {} by id: {}", MarcacaoFeita.class, id);
		return horarioTrabalhoRepository.existsById(id);
	}
	
	@Override
	public List<HorarioTrabalhoDTO> findAll() {
		log.debug("Finding all {}", MarcacaoFeita.class);
		return horarioTrabalhoRepository.findAll().stream().map(horarioTrabalhoMapper::toDto).toList();
	}
	
	@Override
	public Optional<HorarioTrabalhoDTO> findOne(Long id) {
		log.debug("Finding one {} by id: {}", MarcacaoFeita.class, id);
		return horarioTrabalhoRepository.findById(id).map(horarioTrabalhoMapper::toDto);
	}
	
	@Override
	public Optional<HorarioTrabalhoDTO> findOne(HorarioTrabalhoDTO example) {
		log.debug("Finding one {} by example: {}", MarcacaoFeita.class, example);
		return horarioTrabalhoRepository.findOne(Example.of(horarioTrabalhoMapper.toEntity(example), returnExampleMatcher())).map(horarioTrabalhoMapper::toDto);
	}
	
	@Override
	public void delete(Long id) {
		log.debug("Deleting an {} by id: {}", MarcacaoFeita.class, id);
		horarioTrabalhoRepository.deleteById(id);
	}
	
	private ExampleMatcher returnExampleMatcher() {
        return ExampleMatcher
            .matchingAll()
            .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
            .withIgnoreCase();
    }
}
