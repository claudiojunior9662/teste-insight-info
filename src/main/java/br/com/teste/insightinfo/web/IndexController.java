package br.com.teste.insightinfo.web;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import br.com.teste.insightinfo.domain.dto.AtrasoDTO;
import br.com.teste.insightinfo.domain.dto.HoraExtraDTO;
import br.com.teste.insightinfo.domain.dto.HorarioTrabalhoDTO;
import br.com.teste.insightinfo.domain.dto.MarcacaoFeitaDTO;
import br.com.teste.insightinfo.service.AtrasoHoraExtraService;
import br.com.teste.insightinfo.service.HorarioTrabalhoService;
import br.com.teste.insightinfo.service.MarcacaoFeitaService;
import lombok.RequiredArgsConstructor;

@Controller
@SessionAttributes("name")
@RequiredArgsConstructor
public class IndexController {
	
	private final HorarioTrabalhoService horarioTrabalhoService;
	private final MarcacaoFeitaService marcacaoFeitaService;
	private final AtrasoHoraExtraService atrasoHoraExtraService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String showIndexPage(ModelMap model) {
		List<HorarioTrabalhoDTO> horariosTrabalho = horarioTrabalhoService.findAll();
		List<MarcacaoFeitaDTO> marcacoesFeitas = marcacaoFeitaService.findAll();
		List<HoraExtraDTO> horasExtras = atrasoHoraExtraService.calculaHorasExtras(marcacoesFeitas, horariosTrabalho);
		List<AtrasoDTO> atrasos = atrasoHoraExtraService.calculaAtrasos(horariosTrabalho, marcacoesFeitas);
		
		model.addAttribute("horariosTrabalhos", horariosTrabalho);
		model.addAttribute("marcacoesFeitas", marcacaoFeitaService.findAll());
		model.addAttribute("atrasos", atrasos);
		model.addAttribute("horasExtras", horasExtras);
		model.addAttribute("duracaoHorasExtras", atrasoHoraExtraService.retornaQuantidadeHorasHoraExtra(horasExtras));
		model.addAttribute("duracaoAtrasos", atrasoHoraExtraService.retornaQuantidadeHorasAtraso(atrasos));
		return "index";
	}
	
}
