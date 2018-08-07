package shop.serviceimpl;


import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;

import shop.service.IpService;

@Service
public class IpServiceImpl implements IpService {
	/**
	 * 构造方法注入logger,restTemplate组件
	 */
	private Logger logger=LoggerFactory.getLogger(this.getClass());
	private RestTemplate restTemplate;
	@Autowired	
	public IpServiceImpl(RestTemplate restTemplate) {
		super();
		this.restTemplate = restTemplate;
	}




	@Override
	public String ipToProvince(String ip) {
		Map<String,String> params=new HashMap<String, String>();
		params.put("key", "991ddb41d0ba901fe35396ec2f4a0113");
		params.put("ip", ip);
		
		//将响应转换成json树模型
		JsonNode json=restTemplate.getForObject(
				"https://restapi.amap.com/v3/ip?key={key}&ip={ip}",
				JsonNode.class,
				params);
		
		 logger.debug("调用高德ip转地址: " + json.at("/info"));
		 
		return json.at("/province").asText();
	}

}
