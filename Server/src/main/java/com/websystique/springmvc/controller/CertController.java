package com.websystique.springmvc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.websystique.springmvc.model.FileUpload;
import com.websystique.springmvc.model.User;
import com.websystique.springmvc.model.UserCert;
import com.websystique.springmvc.service.UserCertService;
import com.websystique.springmvc.service.UserService;

@Controller
@RequestMapping("/")
@SessionAttributes("roles")
public class CertController {

	@Autowired
	UserService userService;
	
	@Autowired
	UserCertService userCertService;
	
	 @RequestMapping(value = "/getCert/{id}", method = RequestMethod.GET)
	 public String getCertification(ModelMap model, @PathVariable("id") int id) {
	        System.out.println("Get Cer ID: "+id);
	        
	        UserCert cert = userCertService.findByCertId(id);
	        User user = userService.findById(cert.getUser_id());
	        
            model.addAttribute("certInfo", cert);
            model.addAttribute("userInfo", user);
	        return "/dashboard/certification";
	 }
	 
	 @RequestMapping(value = "/deleteCert/{id}", method = RequestMethod.GET)
	 public String deleteCertification(ModelMap model, @PathVariable("id") int id) {
	        System.out.println("Delete Cer ID: "+id);
	        
	        try{
	        	UserCert cert = userCertService.findByCertId(id);
	        	userCertService.deleteByCertId(id);
		        User user = userService.findById(cert.getUser_id());
	        	 List<UserCert> certsList = userCertService.findByUserId(user.getId());
	        	 
	        	 model.addAttribute("deleteFileName", cert.getFile_name());
	             model.addAttribute("certsList", certsList);
	             model.addAttribute("loggedinuser", user.getSsoId());
	             model.addAttribute("dashboardpage", "certificates");
	 	         return "/dashboard/index";
	        }
	        catch(Exception e){
	        	UserCert cert = userCertService.findByCertId(id);
		        User user = userService.findById(cert.getUser_id());
	        	List<UserCert> certsList = userCertService.findByUserId(user.getId());
	        	 
	        	 model.addAttribute("errMsg", "Delete Certificate Failed");
	             model.addAttribute("certsList", certsList);
	             model.addAttribute("loggedinuser", user.getSsoId());
	             model.addAttribute("dashboardpage", "certificates");
	 	         return "/dashboard/index";
	        }
	        
	       
	 }
	 
	 @RequestMapping(value = "/searchCert", method = RequestMethod.GET)
	 public String getCertification(ModelMap model, @ModelAttribute("searchKwd") String searchKwd) {
	        System.out.println("Search Cer like: "+searchKwd);
	        
	        User user = userService.findBySSO(getPrincipal());
	        List<UserCert> certsList = userCertService.findByKwd(user.getId(), searchKwd);
	        
	        model.addAttribute("certsList", certsList);
            model.addAttribute("loggedinuser", user.getSsoId());
            model.addAttribute("dashboardpage", "certificates");
	        return "/dashboard/index";
	 }
	 
		/**
		 * This method returns the principal[user-name] of logged-in user.
		 */
		private String getPrincipal(){
			String userName = null;
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			if (principal instanceof UserDetails) {
				userName = ((UserDetails)principal).getUsername();
			} else {
				userName = principal.toString();
			}
			return userName;
		}
}
