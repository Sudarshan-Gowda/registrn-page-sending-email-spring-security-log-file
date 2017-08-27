package com.star.sud.web;

/*@Author Sudarshan*/
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.star.sud.SectionKeys;
import com.star.sud.StarDateUtil;
import com.star.sud.StarSystemProperties;
import com.star.sud.StarUtil;
import com.star.sud.form.Registration;
import com.star.sud.framework.service.StarUtilService;
import com.star.sud.notification.service.StarEmailConfigurationService;
import com.star.sud.security.model.StarUser;
import com.star.sud.security.service.StarSecurityService;

@Controller
public class StarLoginController {

	private static final Logger logger = Logger.getLogger(StarLoginController.class);

	@Resource(name = "starSecurityService")
	protected StarSecurityService starSecurityService;

	@Resource(name = "starSystemProperties")
	protected StarSystemProperties starSystemProperties;

	@Resource(name = "starUtilService")
	protected StarUtilService starUtilService;

	@Resource(name = "starEmailConfigurationService")
	protected StarEmailConfigurationService starEmailConfigurationService;

	protected static final String STAR_MESSAGE = "starMessage";

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String loginPage(Model model, HttpSession session, @RequestParam Map<String, String> reqParams) {
		String starMessage = reqParams.get(STAR_MESSAGE);

		if (starMessage != null)
			model.addAttribute(STAR_MESSAGE, starMessage);

		reqParams.remove(STAR_MESSAGE);
		model.addAttribute(SectionKeys.REQ_PARAMS, StarUtil.getParamsAsString(reqParams));
		return "login/login-page";
	}

	@RequestMapping(value = "/login/login-failure", method = RequestMethod.GET)
	public String loginFailureView(Model model, RedirectAttributes redirectAttributes) {

		redirectAttributes.addAttribute(STAR_MESSAGE, "Invalid Credentials.");

		return "redirect:/";
	}

	@RequestMapping(value = "/welcome", method = RequestMethod.GET)
	protected String welcome(Model model, HttpSession session, HttpServletRequest request,
			@RequestParam Map<String, String> requestParam) {

		int sessionTomeout = 50000;
		String sesTimeout = 60000 + "";
		if (sesTimeout != null && !sesTimeout.isEmpty()) {
			try {
				sessionTomeout = Integer.parseInt(sesTimeout);
			} catch (Exception e) {
			}
		}

		session.setMaxInactiveInterval(sessionTomeout);

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		StarUser user = starSecurityService.findSysUserName(auth.getName());

		String loc = starSystemProperties.getLogfileLoc() + StarDateUtil.getTodayDateAsRev("/") + "/";
		updateLog4jConfiguration(loc + user.getUserName() + ".log", request);
		logger.debug("-- Debug message --");
		logger.info("-- Displaying info message--");

		if (request.getRemoteUser() != null) {

			model.addAttribute("userName", user.getUserName());
			return "welcome/welcome";
		} else {
			return "redirect:/";
		}

	}

	@RequestMapping(value = "/logout/success", method = RequestMethod.GET)
	public String logoutSuccessView(Model model) {
		return "redirect:/";
	}

	@RequestMapping(value = "/registration", method = RequestMethod.GET)
	public String registartionPage(Model model) {

		Registration registration = new Registration();

		model.addAttribute("registration", registration);

		return "registarion/registarion";
	}

	@RequestMapping(value = "/user/new-user-registrn", method = RequestMethod.POST)
	public String newUserRegistrn(Model model, @ModelAttribute("registration") Registration registration,
			RedirectAttributes redirectAttributes) {

		StarUser user = (StarUser) starUtilService.save(setStarUser(registration));
		if (user != null) {

			String Subject = "Registartion Successfull !!!";
			String msg = "Dear " + registration.getFirstName() + " " + registration.getLastName()
					+ "\n Your account is Successfully Created. \n you can login with User Name: "
					+ registration.getUserName() + " " + " & Password: " + registration.getPassword();

			String fromAddrs="sudarshan@gmail.com";//change accordingly
			String password="****";//change accordingly
			
			starEmailConfigurationService.sendMail(fromAddrs,password, registration.getEmail(),
					Subject, msg);

		}

		model.addAttribute("registration", registration);

		redirectAttributes.addAttribute(STAR_MESSAGE, "Your Account is successfully created !!");

		return "redirect:/";
	}

	private void updateLog4jConfiguration(String logFile, HttpServletRequest request) {
		Properties props = new Properties();
		try {
			InputStream configStream = getClass().getResourceAsStream("/log4j.properties");
			props.load(configStream);
			configStream.close();
		} catch (IOException e) {
			System.out.println("Error not laod configuration file ");
		}

		props.setProperty("log4j.rootLogger", "DEBUG,Appender2");
		props.setProperty("log4j.appender.Appender2", "org.apache.log4j.RollingFileAppender");
		props.setProperty("log4j.appender.Appender2.File", logFile);
		props.setProperty("log4j.appender.Appender2.MaxFileSize", "10MB");
		props.setProperty("log4j.appender.Appender2.layout", "org.apache.log4j.PatternLayout");
		props.setProperty("log4j.appender.Appender2.layout.ConversionPattern",
				"%d{dd MMM yyyy HH:mm:ss} " + StarUtil.getClientIPAddress(request) + " [%t] %-5p %c %x - %m%n");
		props.setProperty("log4j.logger.org.hibernate", "error");
		props.setProperty("log4j.logger.org.springframework", "error");
		props.setProperty("log4j.logger.org.thymeleaf", "error");

		LogManager.resetConfiguration();
		PropertyConfigurator.configure(props);

	}

	public StarUser setStarUser(Registration registration) {
		StarUser starUser = starUtilService.createEntityObj(StarUser.class.getName(), StarUser.class);
		starUser.setUserName(registration.getUserName());
		starUser.setPassword(registration.getPassword());
		starUser.setIsEnabled(Boolean.TRUE);
		starUser.getStarUserRegistration().setFirstName(registration.getFirstName());
		starUser.getStarUserRegistration().setLastName(registration.getLastName());
		starUser.getStarUserRegistration().setContactNum(registration.getContactNum());
		if (registration.getDob() != null && !registration.getDob().isEmpty()) {
			starUser.getStarUserRegistration()
					.setDob(StarDateUtil.getDateFromString(registration.getDob(), Boolean.TRUE));
		}
		starUser.getStarUserRegistration().setEmail(registration.getEmail());
		starUser.getStarUserRegistration().setGender(registration.getGender());
		starUser.getStarUserRegistration().setUser(starUser);

		return starUser;
	}

}
