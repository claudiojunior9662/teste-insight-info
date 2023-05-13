package br.com.teste.insightinfo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.teste.insightinfo.domain.MarcacaoFeita;

@Repository
public interface MarcacaoFeitaRepository extends JpaRepository<MarcacaoFeita, Long>{

}
