package com;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration
public class Exemple {
	static Set<String> NOT_SHOW_SET = new TreeSet<String>();
	static boolean SHOW = true;
	
	static{
		NOT_SHOW_SET.add("Hangzhou");
	}
	@RequestMapping("/ip/ipseek")
	public Map<String, Object> ip(
			@RequestParam(value = "ip", required = false) String ip,
			HttpServletRequest request) {
		if (ip == null || ip.length() == 0) {
			ip = getRemortIP(request);
		}
		return CountryService.getIpInfo(ip);
	}
	
	@RequestMapping("/ip/show")
	public Map<String, Object> show(
			@RequestParam(value = "ip", required = false) String ip,
			HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		if(!SHOW){
			result.put("show", SHOW);
			return result;
		}
		if (ip == null || ip.length() == 0) {
			ip = getRemortIP(request);
		}
		String city =(String) CountryService.getIpInfo(ip).get("city");
		if(city!=null && NOT_SHOW_SET.contains(city)){
			result.put("show", false);
			return result;
		}
		result.put("show", true);
		return result;
	}
	
	@RequestMapping("/ip/setshow")
	public String setshow(@RequestParam(value = "show", required = false) Boolean show) {
		if(show!=null && show){
			SHOW = show;
			return "SHOW value:"+SHOW;
		}
		SHOW = false;
		return "SHOW value:"+SHOW;
	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(Exemple.class, args);
	}

	public String getRemortIP(HttpServletRequest request) {

		if (request.getHeader("x-forwarded-for") == null) {

			return request.getRemoteAddr();

		}
		String ip = request.getHeader("x-forwarded-for");
		if(ip!=null && ip.length()>0 && ip.split(",").length>1){
			ip=ip.split(",")[0];
		}
		return ip;

	}
}