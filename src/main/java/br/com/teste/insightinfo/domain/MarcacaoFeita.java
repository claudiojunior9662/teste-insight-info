package br.com.teste.insightinfo.domain;

import java.io.Serializable;
import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "marcacao_feita")
@Data
public class MarcacaoFeita implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	
	@Column(name = "entrada")
	private LocalTime entrada;
	
	@Column(name = "saida")
	private LocalTime saida;
	
}
