package br.com.teste.insightinfo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.teste.insightinfo.domain.HorarioTrabalho;

@Repository
public interface HorarioTrabalhoRepository extends JpaRepository<HorarioTrabalho, Long>{

}
