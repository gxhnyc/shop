package shop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import shop.entity.Cellphone;
import shop.service.CellphoneService;
import shop.service.CustomerService;
import shop.service.IpService;

/**
 * 首页 控制器（处理 / 请求）
 * 
 */
@Controller
public class HomeController extends BaseController{
	
	private CellphoneService cellphoneService;
	private IpService ipService;
	
	@Autowired
	public HomeController(CellphoneService cellphoneService,IpService ipService) {
		super();
		this.cellphoneService = cellphoneService;
		this.ipService=ipService;
	}

	@RequestMapping(method=RequestMethod.GET,value="/")
	public String HomeIndex(Model model,Cellphone cellphone) {
		 logger.debug("手机搜索条件: " + cellphone);
		List<Cellphone> cellphones=cellphoneService.fuzzyQuery(cellphone);
		model.addAttribute("cellphones", cellphones);
		
		return "index";
		
	}
	
	 @RequestMapping(method = RequestMethod.GET, value = "/ip", produces = "text/plain; charset=utf-8")
	    @ResponseBody
	    public String ipToProvince(@RequestParam String ip) {
	        return ipService.ipToProvince(ip);
	    }
	
	
}
