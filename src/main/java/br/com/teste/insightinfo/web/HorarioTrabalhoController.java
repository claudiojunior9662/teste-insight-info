package br.com.teste.insightinfo.web;

import org.hibernate.service.spi.ServiceException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import br.com.teste.insightinfo.domain.dto.HorarioTrabalhoDTO;
import br.com.teste.insightinfo.service.HorarioTrabalhoService;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/horario-trabalho")
@RequiredArgsConstructor
public class HorarioTrabalhoController {

	private final HorarioTrabalhoService horarioTrabalhoService;
	
	@GetMapping("/add-horario-trabalho")
	public String viewAddHorarioTrabalho(Model model) {
		model.addAttribute("horarioTrabalho", new HorarioTrabalhoDTO());
		return "horario-trabalho/update";
	}
	
	@GetMapping("/edit-horario-trabalho")
	public String viewEditHorarioTrabalho(Model model, @RequestParam(name = "id", required = true) Long id) {
		model.addAttribute("horarioTrabalho", horarioTrabalhoService.findOne(id));
		model.addAttribute("isEditing", true);
		return "horario-trabalho/update";
	}
	
	@GetMapping("/delete-horario-trabalho")
	public RedirectView deleteHorarioTrabalho(Model model, @RequestParam(name = "id", required = true) Long id, RedirectAttributes redirectAttributes) {
		final RedirectView redirectView = new RedirectView("/", true);
		horarioTrabalhoService.delete(id);
		redirectAttributes.addFlashAttribute("isHorarioTrabalhoExcluido", true);
		return redirectView;
	}
	
	@PostMapping("/add-horario-trabalho")
    public RedirectView save(@ModelAttribute("horarioTrabalho") HorarioTrabalhoDTO horarioTrabalho, RedirectAttributes redirectAttributes) {
        final RedirectView redirectView = new RedirectView("/horario-trabalho/add-horario-trabalho", true);
        try {
        	HorarioTrabalhoDTO savedHorarioTrabalho = horarioTrabalhoService.save(horarioTrabalho);
            redirectAttributes.addFlashAttribute("savedHorarioTrabalho", savedHorarioTrabalho);
            redirectAttributes.addFlashAttribute("addHorarioTrabalhoSuccess", true);
        } catch (ServiceException e) {
        	redirectAttributes.addFlashAttribute("addHorarioTrabalhoError", true);
        	redirectAttributes.addFlashAttribute("addHorarioTrabalhoErrorMessage", e.getMessage());
		}
        
        return redirectView;
    }
	
	@PostMapping("/edit-horario-trabalho")
    public RedirectView update(@ModelAttribute("horarioTrabalho") HorarioTrabalhoDTO horarioTrabalho, RedirectAttributes redirectAttributes, Model model) {
        final RedirectView redirectView = new RedirectView("/horario-trabalho/edit-horario-trabalho?id=" + horarioTrabalho.getId().toString(), true);
        try {
        	HorarioTrabalhoDTO updatedHorarioTrabalho = horarioTrabalhoService.update(horarioTrabalho);
            redirectAttributes.addFlashAttribute("updatedHorarioTrabalho", updatedHorarioTrabalho);
            redirectAttributes.addFlashAttribute("updateHorarioTrabalhoSuccess", true);
        } catch (ServiceException e) {
        	redirectAttributes.addFlashAttribute("addHorarioTrabalhoError", true);
        	redirectAttributes.addFlashAttribute("addHorarioTrabalhoErrorMessage", e.getMessage());
        }
        
        return redirectView;
    }
	
}
