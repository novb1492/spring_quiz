package com.kim.demo4;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.kim.demo4.board.boardService;






/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	
	@Autowired
	private boardService boardService;


	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "index";
	}
	@GetMapping("/joinPage")
	public String joinPagr() {
		return "/member/memberForm";
	}
	@GetMapping("/loginPage")
	public String loginPage() {
		return "/member/loginForm";
	}
	@GetMapping("/doneLoingPage")
	public String doneLoingPage() {
		return "/member/doneLoginPage";
	}
	@GetMapping("/boardPage")
	public String boardPage(HttpServletRequest request,HttpServletResponse response,Model model) {
		boardService.getArticles(request,model);
		return "/board/boardForm";
	}
	@GetMapping("/writePage")
	public String writePage(HttpSession session,HttpServletResponse response,Model model) {
		utillService.checkLogin(session, response);
		model.addAttribute("email", session.getAttribute("email"));
		model.addAttribute("nowPage", 2);
		model.addAttribute("totalPage", 10);
		return "/board/writeForm";
	}

}
