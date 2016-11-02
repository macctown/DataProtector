package com.websystique.springmvc.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.websystique.springmvc.model.FileUpload;
import com.websystique.springmvc.model.User;
import com.websystique.springmvc.model.UserCert;
import com.websystique.springmvc.service.UserCertService;
import com.websystique.springmvc.service.UserService;
import com.websystique.springmvc.validator.FileValidator;
 
@Controller
@RequestMapping("/")
@SessionAttributes("roles")
public class FileUploadController {
 
    //private static String UPLOAD_LOCATION="/Users/zhangwei/Desktop/temp";
 
	@Autowired
	UserService userService;
	
	@Autowired
	UserCertService userCertService;
	
    FileValidator fileValidator;
 
    @InitBinder("fileUpload")
    protected void initBinderFileBucket(WebDataBinder binder) {
        binder.setValidator(fileValidator);
    }
 
	@RequestMapping(value = "/upload", method=RequestMethod.POST, headers=("content-type=multipart/*"))
    public String singleFileUpload(@Valid FileUpload fileUpload,
            BindingResult result, ModelMap model) throws IOException {
 
        if (result.hasErrors()) {
            System.out.println("validation errors");
            model.addAttribute("fileUploadError", true);
            model.addAttribute("dashboardpage", "certificates");
            return "dashboard/index";
        } else {
            System.out.println("Fetching file");
            System.out.println("CurrentUser: "+getPrincipal());
            MultipartFile file = fileUpload.getFile();
            // Now do something with file...
            //FileCopyUtils.copy(fileUpload.getFile().getBytes(), new File( UPLOAD_LOCATION + fileUpload.getFile().getOriginalFilename()));
            String file_name = file.getOriginalFilename();
            String hash_value = fileUpload.getHash();
            String file_type = file.getContentType();
            
            User currentUser = userService.findBySSO(getPrincipal());
            
            UserCert newCert = new UserCert();
            newCert.setFile_name(file_name);
            newCert.setHash_value(hash_value);
            newCert.setUser_id(currentUser.getId());
            newCert.setFile_type(file_type);
            
            userCertService.saveUser(newCert);
            
            List<UserCert> certsList = userCertService.findByUserId(currentUser.getId());
            
            model.addAttribute("fileName", file_name);
            model.addAttribute("certsList", certsList);
            model.addAttribute("loggedinuser", getPrincipal());
            model.addAttribute("dashboardpage", "certificates");
            return "dashboard/index";
        }
    }
	
	@RequestMapping(value = "/authUpload", method=RequestMethod.POST, headers=("content-type=multipart/*"))
    public String authFileUpload(@Valid FileUpload fileUpload,
            BindingResult result, ModelMap model, @ModelAttribute("certId") String certId) throws IOException {
 
        if (result.hasErrors()) {
            System.out.println("validation errors");
            model.addAttribute("fileUploadError", true);
            model.addAttribute("dashboardpage", "certificates");
            return "dashboard/index";
        } else {
            System.out.println("Fetching file");
            System.out.println("CurrentUser: "+getPrincipal());
            // Now do something with file...
            //FileCopyUtils.copy(fileUpload.getFile().getBytes(), new File( UPLOAD_LOCATION + fileUpload.getFile().getOriginalFilename()));

            String hash_value = fileUpload.getHash();
            int cert_id = Integer.valueOf(certId);
            
            User currentUser = userService.findBySSO(getPrincipal());
            
            if(userCertService.authUserCert(cert_id, hash_value)){
            	model.addAttribute("authResult", true);
            }
            else{
            	model.addAttribute("authResult", false);
            }
            
            List<UserCert> certsList = userCertService.findByUserId(currentUser.getId());
                      
            model.addAttribute("certsList", certsList);
            model.addAttribute("loggedinuser", getPrincipal());
            model.addAttribute("dashboardpage", "certificates");
            return "dashboard/index";
        }
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