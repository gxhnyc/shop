package shop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import shop.entity.Cellphone;
import shop.service.CellphoneService;
import shop.service.CustomerService;

@Controller
public class HomeController {
	/**
	 * 首页 控制器（处理 / 请求）
	 * @return
	 */
	private CellphoneService cellphoneService;
	@Autowired
	public HomeController(CellphoneService cellphoneService) {
		super();
		this.cellphoneService = cellphoneService;
	}

	@RequestMapping(method=RequestMethod.GET,value="/")
	public String HomeIndex(Model model,Cellphone cellphone) {
		 System.out.println("手机搜索条件: " + cellphone);
		List<Cellphone> cellphones=cellphoneService.fuzzyQuery(cellphone);
		model.addAttribute("cellphones", cellphones);
		
		return "index";
		
	}	
}
