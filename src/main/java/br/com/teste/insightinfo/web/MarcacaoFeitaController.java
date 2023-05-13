package br.com.teste.insightinfo.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import br.com.teste.insightinfo.domain.dto.MarcacaoFeitaDTO;
import br.com.teste.insightinfo.service.MarcacaoFeitaService;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/marcacao-feita")
@RequiredArgsConstructor
public class MarcacaoFeitaController {

	private final MarcacaoFeitaService marcacaoFeitaService;
	
	@GetMapping
	public String listMarcacaoFeita(Model model, RedirectAttributes redirectAttributes) {
		model.addAttribute("marcacoesFeitas", marcacaoFeitaService.findAll());
		return "marcacao-feita/list";
	}
	
	@GetMapping("/add-marcacao-feita")
	public String viewAddMarcacaoFeita(Model model) {
		model.addAttribute("marcacaoFeita", new MarcacaoFeitaDTO());
		return "marcacao-feita/update";
	}
	
	@GetMapping("/edit-marcacao-feita")
	public String viewEditMarcacaoFeita(Model model, @RequestParam(name = "id", required = true) Long id) {
		model.addAttribute("marcacaoFeita", marcacaoFeitaService.findOne(id));
		model.addAttribute("isEditing", true);
		return "marcacao-feita/update";
	}
	
	@GetMapping("/delete-marcacao-feita")
	public RedirectView deleteMarcacaoFeita(Model model, @RequestParam(name = "id", required = true) Long id, RedirectAttributes redirectAttributes) {
		final RedirectView redirectView = new RedirectView("/marcacao-feita", true);
		marcacaoFeitaService.delete(id);
		redirectAttributes.addFlashAttribute("isExcluido", true);
		return redirectView;
	}
	
	@PostMapping("/add-marcacao-feita")
    public RedirectView save(@ModelAttribute("marcacaoFeita") MarcacaoFeitaDTO marcacaoFeita, RedirectAttributes redirectAttributes) {
        final RedirectView redirectView = new RedirectView("/marcacao-feita/add-marcacao-feita", true);
        MarcacaoFeitaDTO savedMarcacaoFeita = marcacaoFeitaService.save(marcacaoFeita);
        redirectAttributes.addFlashAttribute("savedMarcacaoFeita", savedMarcacaoFeita);
        redirectAttributes.addFlashAttribute("addMarcacaoFeitaSuccess", true);
        return redirectView;
    }
	
	@PostMapping("/edit-marcacao-feita")
    public RedirectView update(@ModelAttribute("marcacaoFeita") MarcacaoFeitaDTO marcacaoFeita, RedirectAttributes redirectAttributes, Model model) {
        final RedirectView redirectView = new RedirectView("/marcacao-feita/edit-marcacao-feita?id=" + marcacaoFeita.getId().toString(), true);
        MarcacaoFeitaDTO updatedMarcacaoFeita = marcacaoFeitaService.save(marcacaoFeita);
        redirectAttributes.addFlashAttribute("updatedMarcacaoFeita", updatedMarcacaoFeita);
        redirectAttributes.addFlashAttribute("updateMarcacaoFeitaSuccess", true);
        return redirectView;
    }
}
