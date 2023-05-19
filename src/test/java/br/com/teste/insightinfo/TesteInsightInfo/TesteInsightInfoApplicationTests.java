package br.com.teste.insightinfo.TesteInsightInfo;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.teste.insightinfo.domain.dto.AtrasoDTO;
import br.com.teste.insightinfo.domain.dto.HoraExtraDTO;
import br.com.teste.insightinfo.domain.dto.HorarioTrabalhoDTO;
import br.com.teste.insightinfo.domain.dto.MarcacaoFeitaDTO;
import br.com.teste.insightinfo.service.impl.AtrasoHoraExtraServiceImpl;

@SpringBootTest
class TesteInsightInfoApplicationTests {
	
	@InjectMocks
	private AtrasoHoraExtraServiceImpl atrasoHoraExtraService;
	
	public List<HorarioTrabalhoDTO> horariosTrabalho = new ArrayList<HorarioTrabalhoDTO>();
	public List<MarcacaoFeitaDTO> marcacoesFeitas = new ArrayList<MarcacaoFeitaDTO>();
	public List<HoraExtraDTO> horasExtrasExpect = new ArrayList<HoraExtraDTO>();
	public List<AtrasoDTO> atrasosExpect = new ArrayList<AtrasoDTO>();
	public List<HoraExtraDTO> horasExtrasResult = new ArrayList<HoraExtraDTO>();
	public List<AtrasoDTO> atrasosResult = new ArrayList<AtrasoDTO>();
	
	@BeforeEach
    public void beforeEach() {
        MockitoAnnotations.openMocks(this);
        
        // clear tables
        horariosTrabalho.clear();
        marcacoesFeitas.clear();
        horasExtrasExpect.clear();
        atrasosExpect.clear();
        horasExtrasResult.clear();
        atrasosResult.clear();
    }
	
	@Test
	void contextLoads() {
	}
	
	@Test
	void testScenario1() {		
		// scenario
		horariosTrabalho.add(new HorarioTrabalhoDTO(LocalTime.of(8, 0), LocalTime.of(12, 0)));
		marcacoesFeitas.add(new MarcacaoFeitaDTO(LocalTime.of(7, 0), LocalTime.of(11, 0)));
		
		// expectations scenario
		horasExtrasExpect.add(new HoraExtraDTO(LocalTime.of(7, 0), LocalTime.of(8, 0)));
		atrasosExpect.add(new AtrasoDTO(LocalTime.of(11, 0), LocalTime.of(12, 0)));
		
		// executing scenario
		horasExtrasResult = atrasoHoraExtraService.calculaHorasExtras(marcacoesFeitas, horariosTrabalho);
		atrasosResult = atrasoHoraExtraService.calculaAtrasos(horariosTrabalho, marcacoesFeitas);
		
		// checks scenario
		Assertions.assertThat(horasExtrasResult).containsExactlyElementsOf(horasExtrasExpect);
		Assertions.assertThat(atrasosResult).containsExactlyElementsOf(atrasosExpect);
	}
	
	@Test
	void testScenario2() {		
		// scenario
		horariosTrabalho.add(new HorarioTrabalhoDTO(LocalTime.of(8, 0), LocalTime.of(12, 0)));
		horariosTrabalho.add(new HorarioTrabalhoDTO(LocalTime.of(13, 30), LocalTime.of(17, 30)));
		marcacoesFeitas.add(new MarcacaoFeitaDTO(LocalTime.of(6, 0), LocalTime.of(20, 0)));
		
		// expectations scenario
		horasExtrasExpect.add(new HoraExtraDTO(LocalTime.of(6, 0), LocalTime.of(8, 0)));
		horasExtrasExpect.add(new HoraExtraDTO(LocalTime.of(12, 0), LocalTime.of(13, 30)));
		horasExtrasExpect.add(new HoraExtraDTO(LocalTime.of(17, 30), LocalTime.of(20, 0)));
		
		// executing scenario
		horasExtrasResult = atrasoHoraExtraService.calculaHorasExtras(marcacoesFeitas, horariosTrabalho);
		atrasosResult = atrasoHoraExtraService.calculaAtrasos(horariosTrabalho, marcacoesFeitas);
		
		// checks scenario
		Assertions.assertThat(horasExtrasResult).containsExactlyElementsOf(horasExtrasExpect);
		Assertions.assertThat(atrasosResult).containsExactlyElementsOf(atrasosExpect);
	}
	
	@Test
	void testScenario3() {		
		// scenario
		horariosTrabalho.add(new HorarioTrabalhoDTO(LocalTime.of(8, 0), LocalTime.of(12, 0)));
		horariosTrabalho.add(new HorarioTrabalhoDTO(LocalTime.of(13, 30), LocalTime.of(17, 30)));
		marcacoesFeitas.add(new MarcacaoFeitaDTO(LocalTime.of(7, 0), LocalTime.of(12, 30)));
		marcacoesFeitas.add(new MarcacaoFeitaDTO(LocalTime.of(14, 0), LocalTime.of(17, 0)));
		
		// expectations scenario
		horasExtrasExpect.add(new HoraExtraDTO(LocalTime.of(7, 0), LocalTime.of(8, 0)));
		horasExtrasExpect.add(new HoraExtraDTO(LocalTime.of(12, 0), LocalTime.of(12, 30)));
		atrasosExpect.add(new AtrasoDTO(LocalTime.of(13, 30), LocalTime.of(14, 0)));
		atrasosExpect.add(new AtrasoDTO(LocalTime.of(17, 0), LocalTime.of(17, 30)));
		
		// executing scenario
		horasExtrasResult = atrasoHoraExtraService.calculaHorasExtras(marcacoesFeitas, horariosTrabalho);
		atrasosResult = atrasoHoraExtraService.calculaAtrasos(horariosTrabalho, marcacoesFeitas);
		
		// checks scenario
		Assertions.assertThat(horasExtrasResult).containsExactlyElementsOf(horasExtrasExpect);
		Assertions.assertThat(atrasosResult).containsExactlyElementsOf(atrasosExpect);
	}
	
	@Test
	void testScenario4() {		
		// scenario
		horariosTrabalho.add(new HorarioTrabalhoDTO(LocalTime.of(22, 0), LocalTime.of(5, 0)));
		marcacoesFeitas.add(new MarcacaoFeitaDTO(LocalTime.of(21, 0), LocalTime.of(4, 0)));
		
		// expectations scenario
		horasExtrasExpect.add(new HoraExtraDTO(LocalTime.of(21, 0), LocalTime.of(22, 0)));
		atrasosExpect.add(new AtrasoDTO(LocalTime.of(4, 0), LocalTime.of(5, 0)));
		
		// executing scenario
		horasExtrasResult = atrasoHoraExtraService.calculaHorasExtras(marcacoesFeitas, horariosTrabalho);
		atrasosResult = atrasoHoraExtraService.calculaAtrasos(horariosTrabalho, marcacoesFeitas);
		
		// checks scenario
		Assertions.assertThat(horasExtrasResult).containsExactlyElementsOf(horasExtrasExpect);
		Assertions.assertThat(atrasosResult).containsExactlyElementsOf(atrasosExpect);
	}
	
	@Test
	void testScenario5() {		
		// scenario
		horariosTrabalho.add(new HorarioTrabalhoDTO(LocalTime.of(22, 0), LocalTime.of(5, 0)));
		marcacoesFeitas.add(new MarcacaoFeitaDTO(LocalTime.of(3, 0), LocalTime.of(7, 0)));
		
		// expectations scenario
		horasExtrasExpect.add(new HoraExtraDTO(LocalTime.of(5, 0), LocalTime.of(7, 0)));
		atrasosExpect.add(new AtrasoDTO(LocalTime.of(22, 0), LocalTime.of(3, 0)));
		
		// executing scenario
		horasExtrasResult = atrasoHoraExtraService.calculaHorasExtras(marcacoesFeitas, horariosTrabalho);
		atrasosResult = atrasoHoraExtraService.calculaAtrasos(horariosTrabalho, marcacoesFeitas);
		
		// checks scenario
		Assertions.assertThat(horasExtrasResult).containsExactlyElementsOf(horasExtrasExpect);
		Assertions.assertThat(atrasosResult).containsExactlyElementsOf(atrasosExpect);
	}
	
	@Test
	void testScenario6() {		
		// scenario
		horariosTrabalho.add(new HorarioTrabalhoDTO(LocalTime.of(8, 0), LocalTime.of(12, 0)));
		marcacoesFeitas.add(new MarcacaoFeitaDTO(LocalTime.of(8, 0), LocalTime.of(9, 30)));
		marcacoesFeitas.add(new MarcacaoFeitaDTO(LocalTime.of(10, 30), LocalTime.of(12, 0)));
		
		// expectations scenario
		atrasosExpect.add(new AtrasoDTO(LocalTime.of(9, 30), LocalTime.of(10, 30)));
		
		// executing scenario
		horasExtrasResult = atrasoHoraExtraService.calculaHorasExtras(marcacoesFeitas, horariosTrabalho);
		atrasosResult = atrasoHoraExtraService.calculaAtrasos(horariosTrabalho, marcacoesFeitas);
		
		// checks scenario
		Assertions.assertThat(horasExtrasResult).containsExactlyElementsOf(horasExtrasExpect);
		Assertions.assertThat(atrasosResult).containsExactlyElementsOf(atrasosExpect);
	}
	
	@Test
	void testScenario7() {		
		// scenario
		horariosTrabalho.add(new HorarioTrabalhoDTO(LocalTime.of(8, 0), LocalTime.of(12, 0)));
		marcacoesFeitas.add(new MarcacaoFeitaDTO(LocalTime.of(6, 0), LocalTime.of(7, 30)));
		marcacoesFeitas.add(new MarcacaoFeitaDTO(LocalTime.of(8, 15), LocalTime.of(10, 0)));
		marcacoesFeitas.add(new MarcacaoFeitaDTO(LocalTime.of(10, 10), LocalTime.of(11, 35)));
		marcacoesFeitas.add(new MarcacaoFeitaDTO(LocalTime.of(11, 50), LocalTime.of(12, 45)));
		
		// expectations scenario
		horasExtrasExpect.add(new HoraExtraDTO(LocalTime.of(6, 0), LocalTime.of(7, 30)));
		horasExtrasExpect.add(new HoraExtraDTO(LocalTime.of(12, 0), LocalTime.of(12, 45)));
		atrasosExpect.add(new AtrasoDTO(LocalTime.of(8, 0), LocalTime.of(8, 15)));
		atrasosExpect.add(new AtrasoDTO(LocalTime.of(10, 0), LocalTime.of(10, 10)));
		atrasosExpect.add(new AtrasoDTO(LocalTime.of(11, 35), LocalTime.of(11, 50)));
		
		// executing scenario
		horasExtrasResult = atrasoHoraExtraService.calculaHorasExtras(marcacoesFeitas, horariosTrabalho);
		atrasosResult = atrasoHoraExtraService.calculaAtrasos(horariosTrabalho, marcacoesFeitas);
		
		// checks scenario
		Assertions.assertThat(horasExtrasResult).containsExactlyElementsOf(horasExtrasExpect);
		Assertions.assertThat(atrasosResult).containsExactlyElementsOf(atrasosExpect);
	}
	
	@Test
	void testScenario8() {		
		// scenario
		horariosTrabalho.add(new HorarioTrabalhoDTO(LocalTime.of(8, 0), LocalTime.of(12, 0)));
		horariosTrabalho.add(new HorarioTrabalhoDTO(LocalTime.of(14, 0), LocalTime.of(18, 0)));
		marcacoesFeitas.add(new MarcacaoFeitaDTO(LocalTime.of(8, 0), LocalTime.of(12, 0)));
		marcacoesFeitas.add(new MarcacaoFeitaDTO(LocalTime.of(19, 0), LocalTime.of(21, 0)));
		
		// expectations scenario
		horasExtrasExpect.add(new HoraExtraDTO(LocalTime.of(19, 0), LocalTime.of(21, 0)));
		atrasosExpect.add(new AtrasoDTO(LocalTime.of(14, 0), LocalTime.of(18, 0)));
		
		// executing scenario
		horasExtrasResult = atrasoHoraExtraService.calculaHorasExtras(marcacoesFeitas, horariosTrabalho);
		atrasosResult = atrasoHoraExtraService.calculaAtrasos(horariosTrabalho, marcacoesFeitas);
		
		// checks scenario
		Assertions.assertThat(horasExtrasResult).containsExactlyElementsOf(horasExtrasExpect);
		Assertions.assertThat(atrasosResult).containsExactlyElementsOf(atrasosExpect);
	}
	
	@Test
	void testScenario9() {		
		// scenario
		horariosTrabalho.add(new HorarioTrabalhoDTO(LocalTime.of(8, 0), LocalTime.of(12, 0)));
		horariosTrabalho.add(new HorarioTrabalhoDTO(LocalTime.of(14, 0), LocalTime.of(18, 0)));
		marcacoesFeitas.add(new MarcacaoFeitaDTO(LocalTime.of(6, 0), LocalTime.of(7, 0)));
		marcacoesFeitas.add(new MarcacaoFeitaDTO(LocalTime.of(14, 0), LocalTime.of(18, 0)));
		
		// expectations scenario
		horasExtrasExpect.add(new HoraExtraDTO(LocalTime.of(6, 0), LocalTime.of(7, 0)));
		atrasosExpect.add(new AtrasoDTO(LocalTime.of(8, 0), LocalTime.of(12, 0)));
		
		// executing scenario
		horasExtrasResult = atrasoHoraExtraService.calculaHorasExtras(marcacoesFeitas, horariosTrabalho);
		atrasosResult = atrasoHoraExtraService.calculaAtrasos(horariosTrabalho, marcacoesFeitas);
		
		// checks scenario
		Assertions.assertThat(horasExtrasResult).containsExactlyElementsOf(horasExtrasExpect);
		Assertions.assertThat(atrasosResult).containsExactlyElementsOf(atrasosExpect);
	}
	
	@Test
	void testScenario10() {		
		// scenario
		horariosTrabalho.add(new HorarioTrabalhoDTO(LocalTime.of(8, 0), LocalTime.of(12, 0)));
		horariosTrabalho.add(new HorarioTrabalhoDTO(LocalTime.of(14, 0), LocalTime.of(18, 0)));
		marcacoesFeitas.add(new MarcacaoFeitaDTO(LocalTime.of(8, 0), LocalTime.of(11, 0)));
		marcacoesFeitas.add(new MarcacaoFeitaDTO(LocalTime.of(12, 0), LocalTime.of(16, 0)));
		marcacoesFeitas.add(new MarcacaoFeitaDTO(LocalTime.of(17, 0), LocalTime.of(21, 0)));
		marcacoesFeitas.add(new MarcacaoFeitaDTO(LocalTime.of(22, 0), LocalTime.of(23, 0)));
		
		// expectations scenario
		horasExtrasExpect.add(new HoraExtraDTO(LocalTime.of(11, 0), LocalTime.of(12, 0)));
		horasExtrasExpect.add(new HoraExtraDTO(LocalTime.of(16, 0), LocalTime.of(17, 0)));
		atrasosExpect.add(new AtrasoDTO(LocalTime.of(12, 0), LocalTime.of(14, 0)));
		atrasosExpect.add(new AtrasoDTO(LocalTime.of(18, 0), LocalTime.of(21, 0)));
		atrasosExpect.add(new AtrasoDTO(LocalTime.of(22, 0), LocalTime.of(23, 0)));
		
		// executing scenario
		horasExtrasResult = atrasoHoraExtraService.calculaHorasExtras(marcacoesFeitas, horariosTrabalho);
		atrasosResult = atrasoHoraExtraService.calculaAtrasos(horariosTrabalho, marcacoesFeitas);
		
		// checks scenario
		Assertions.assertThat(horasExtrasResult).containsExactlyElementsOf(horasExtrasExpect);
		Assertions.assertThat(atrasosResult).containsExactlyElementsOf(atrasosExpect);
	}
	
	@Test
	void testScenario11() {		
		// scenario
		horariosTrabalho.add(new HorarioTrabalhoDTO(LocalTime.of(7, 0), LocalTime.of(11, 0)));
		horariosTrabalho.add(new HorarioTrabalhoDTO(LocalTime.of(13, 0), LocalTime.of(17, 0)));
		horariosTrabalho.add(new HorarioTrabalhoDTO(LocalTime.of(19, 0), LocalTime.of(21, 0)));
		marcacoesFeitas.add(new MarcacaoFeitaDTO(LocalTime.of(7, 0), LocalTime.of(11, 0)));
		
		// expectations scenario
		atrasosExpect.add(new AtrasoDTO(LocalTime.of(13, 0), LocalTime.of(17, 0)));
		atrasosExpect.add(new AtrasoDTO(LocalTime.of(19, 0), LocalTime.of(21, 0)));
		
		// executing scenario
		horasExtrasResult = atrasoHoraExtraService.calculaHorasExtras(marcacoesFeitas, horariosTrabalho);
		atrasosResult = atrasoHoraExtraService.calculaAtrasos(horariosTrabalho, marcacoesFeitas);
		
		// checks scenario
		Assertions.assertThat(horasExtrasResult).containsExactlyElementsOf(horasExtrasExpect);
		Assertions.assertThat(atrasosResult).containsExactlyElementsOf(atrasosExpect);
	}
	
	@Test
	void testScenario12() {		
		// scenario
		horariosTrabalho.add(new HorarioTrabalhoDTO(LocalTime.of(7, 0), LocalTime.of(11, 0)));
		horariosTrabalho.add(new HorarioTrabalhoDTO(LocalTime.of(13, 0), LocalTime.of(17, 0)));
		horariosTrabalho.add(new HorarioTrabalhoDTO(LocalTime.of(19, 0), LocalTime.of(21, 0)));
		marcacoesFeitas.add(new MarcacaoFeitaDTO(LocalTime.of(7, 0), LocalTime.of(10, 0)));
		marcacoesFeitas.add(new MarcacaoFeitaDTO(LocalTime.of(11, 0), LocalTime.of(15, 0)));
		marcacoesFeitas.add(new MarcacaoFeitaDTO(LocalTime.of(16, 0), LocalTime.of(18, 0)));
		
		// expectations scenario
		horasExtrasExpect.add(new HoraExtraDTO(LocalTime.of(11, 0), LocalTime.of(13, 0)));
		horasExtrasExpect.add(new HoraExtraDTO(LocalTime.of(17, 0), LocalTime.of(18, 0)));
		atrasosExpect.add(new AtrasoDTO(LocalTime.of(10, 0), LocalTime.of(11, 0)));
		atrasosExpect.add(new AtrasoDTO(LocalTime.of(15, 0), LocalTime.of(16, 0)));
		atrasosExpect.add(new AtrasoDTO(LocalTime.of(19, 0), LocalTime.of(21, 0)));
		
		// executing scenario
		horasExtrasResult = atrasoHoraExtraService.calculaHorasExtras(marcacoesFeitas, horariosTrabalho);
		atrasosResult = atrasoHoraExtraService.calculaAtrasos(horariosTrabalho, marcacoesFeitas);
		
		// checks scenario
		Assertions.assertThat(horasExtrasResult).containsExactlyElementsOf(horasExtrasExpect);
		Assertions.assertThat(atrasosResult).containsExactlyElementsOf(atrasosExpect);
	}
	
	@Test
	void testScenario13() {		
		// scenario
		horariosTrabalho.add(new HorarioTrabalhoDTO(LocalTime.of(22, 0), LocalTime.of(6, 0)));
		marcacoesFeitas.add(new MarcacaoFeitaDTO(LocalTime.of(23, 0), LocalTime.of(6, 0)));
		
		// expectations scenario
		atrasosExpect.add(new AtrasoDTO(LocalTime.of(22, 0), LocalTime.of(23, 0)));
		
		// executing scenario
		horasExtrasResult = atrasoHoraExtraService.calculaHorasExtras(marcacoesFeitas, horariosTrabalho);
		atrasosResult = atrasoHoraExtraService.calculaAtrasos(horariosTrabalho, marcacoesFeitas);
		
		// checks scenario
		Assertions.assertThat(horasExtrasResult).containsExactlyElementsOf(horasExtrasExpect);
		Assertions.assertThat(atrasosResult).containsExactlyElementsOf(atrasosExpect);
	}
	
	@Test
	void testScenario14() {		
		// scenario
		horariosTrabalho.add(new HorarioTrabalhoDTO(LocalTime.of(22, 0), LocalTime.of(6, 0)));
		marcacoesFeitas.add(new MarcacaoFeitaDTO(LocalTime.of(22, 0), LocalTime.of(5, 0)));
		
		// expectations scenario
		atrasosExpect.add(new AtrasoDTO(LocalTime.of(5, 0), LocalTime.of(6, 0)));
		
		// executing scenario
		horasExtrasResult = atrasoHoraExtraService.calculaHorasExtras(marcacoesFeitas, horariosTrabalho);
		atrasosResult = atrasoHoraExtraService.calculaAtrasos(horariosTrabalho, marcacoesFeitas);
		
		// checks scenario
		Assertions.assertThat(horasExtrasResult).containsExactlyElementsOf(horasExtrasExpect);
		Assertions.assertThat(atrasosResult).containsExactlyElementsOf(atrasosExpect);
	}
	
	@Test
	void testScenario15() {		
		// scenario
		horariosTrabalho.add(new HorarioTrabalhoDTO(LocalTime.of(22, 0), LocalTime.of(6, 0)));
		marcacoesFeitas.add(new MarcacaoFeitaDTO(LocalTime.of(20, 0), LocalTime.of(22, 0)));
		marcacoesFeitas.add(new MarcacaoFeitaDTO(LocalTime.of(6, 0), LocalTime.of(7, 0)));
		
		// expectations scenario
		horasExtrasExpect.add(new HoraExtraDTO(LocalTime.of(20, 0), LocalTime.of(22, 0)));
		horasExtrasExpect.add(new HoraExtraDTO(LocalTime.of(6, 0), LocalTime.of(7, 0)));
		atrasosExpect.add(new AtrasoDTO(LocalTime.of(22, 0), LocalTime.of(6, 0)));
		
		// executing scenario
		horasExtrasResult = atrasoHoraExtraService.calculaHorasExtras(marcacoesFeitas, horariosTrabalho);
		atrasosResult = atrasoHoraExtraService.calculaAtrasos(horariosTrabalho, marcacoesFeitas);
		
		// checks scenario
		Assertions.assertThat(horasExtrasResult).containsExactlyElementsOf(horasExtrasExpect);
		Assertions.assertThat(atrasosResult).containsExactlyElementsOf(atrasosExpect);
	}
	
	@Test
	void testScenario16() {		
		// scenario
		horariosTrabalho.add(new HorarioTrabalhoDTO(LocalTime.of(22, 0), LocalTime.of(1, 0)));
		horariosTrabalho.add(new HorarioTrabalhoDTO(LocalTime.of(2, 0), LocalTime.of(6, 0)));
		marcacoesFeitas.add(new MarcacaoFeitaDTO(LocalTime.of(22, 0), LocalTime.of(1, 0)));
		marcacoesFeitas.add(new MarcacaoFeitaDTO(LocalTime.of(7, 0), LocalTime.of(15, 0)));
		
		// expectations scenario
		horasExtrasExpect.add(new HoraExtraDTO(LocalTime.of(7, 0), LocalTime.of(15, 0)));
		atrasosExpect.add(new AtrasoDTO(LocalTime.of(2, 0), LocalTime.of(6, 0)));
		
		// executing scenario
		horasExtrasResult = atrasoHoraExtraService.calculaHorasExtras(marcacoesFeitas, horariosTrabalho);
		atrasosResult = atrasoHoraExtraService.calculaAtrasos(horariosTrabalho, marcacoesFeitas);
		
		// checks scenario
		Assertions.assertThat(horasExtrasResult).containsExactlyElementsOf(horasExtrasExpect);
		Assertions.assertThat(atrasosResult).containsExactlyElementsOf(atrasosExpect);
	}
	
	@Test
	void testScenario17() {		
		// scenario
		horariosTrabalho.add(new HorarioTrabalhoDTO(LocalTime.of(22, 0), LocalTime.of(1, 0)));
		horariosTrabalho.add(new HorarioTrabalhoDTO(LocalTime.of(2, 0), LocalTime.of(6, 0)));
		marcacoesFeitas.add(new MarcacaoFeitaDTO(LocalTime.of(20, 0), LocalTime.of(21, 0)));
		marcacoesFeitas.add(new MarcacaoFeitaDTO(LocalTime.of(2, 0), LocalTime.of(6, 0)));
		
		// expectations scenario
		horasExtrasExpect.add(new HoraExtraDTO(LocalTime.of(20, 0), LocalTime.of(21, 0)));
		atrasosExpect.add(new AtrasoDTO(LocalTime.of(22, 0), LocalTime.of(1, 0)));
		
		// executing scenario
		horasExtrasResult = atrasoHoraExtraService.calculaHorasExtras(marcacoesFeitas, horariosTrabalho);
		atrasosResult = atrasoHoraExtraService.calculaAtrasos(horariosTrabalho, marcacoesFeitas);
		
		// checks scenario
		Assertions.assertThat(horasExtrasResult).containsExactlyElementsOf(horasExtrasExpect);
		Assertions.assertThat(atrasosResult).containsExactlyElementsOf(atrasosExpect);
	}
	
	@Test
	void testScenario18() {		
		// scenario
		horariosTrabalho.add(new HorarioTrabalhoDTO(LocalTime.of(22, 0), LocalTime.of(1, 0)));
		horariosTrabalho.add(new HorarioTrabalhoDTO(LocalTime.of(2, 0), LocalTime.of(6, 0)));
		marcacoesFeitas.add(new MarcacaoFeitaDTO(LocalTime.of(22, 0), LocalTime.of(5, 0)));
		
		// expectations scenario
		horasExtrasExpect.add(new HoraExtraDTO(LocalTime.of(1, 0), LocalTime.of(2, 0)));
		atrasosExpect.add(new AtrasoDTO(LocalTime.of(5, 0), LocalTime.of(6, 0)));
		
		// executing scenario
		horasExtrasResult = atrasoHoraExtraService.calculaHorasExtras(marcacoesFeitas, horariosTrabalho);
		atrasosResult = atrasoHoraExtraService.calculaAtrasos(horariosTrabalho, marcacoesFeitas);
		
		// checks scenario
		Assertions.assertThat(horasExtrasResult).containsExactlyElementsOf(horasExtrasExpect);
		Assertions.assertThat(atrasosResult).containsExactlyElementsOf(atrasosExpect);
	}
}
