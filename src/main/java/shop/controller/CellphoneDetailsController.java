package shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import shop.entity.Cellphone;
import shop.service.CellphoneService;

/**
 * 手机详情控制器
 * @author Administrator
 *
 */
@Controller
public class CellphoneDetailsController {
	
	private CellphoneService cellphoneService;
	@Autowired
	public CellphoneDetailsController(CellphoneService cellphoneService) {
		super();
		this.cellphoneService = cellphoneService;
	}

	//手机详情
	@RequestMapping(method=RequestMethod.GET,value="/details/{id}")
	public String details(@ModelAttribute Cellphone cellphone,@PathVariable String id,Model model) {
		
		model.addAttribute("cellphone", cellphoneService.findByID(id));
		return "cellphone-details";
		
	}
}
