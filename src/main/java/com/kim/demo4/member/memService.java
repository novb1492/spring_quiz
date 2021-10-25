package com.kim.demo4.member;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.kim.demo4.utillService;

import Daos.memberDao;



@Service
public class memService {
	@Autowired
	private memberDao memberDao;
	
	private static Logger logger=LoggerFactory.getLogger(memService.class);
	private final int pagesize=2;
	
	public void getMembers(HttpServletRequest request,Model model) {
		System.out.println("getMembers");
		int page=Integer.parseInt(request.getParameter("page"));
		String keyword=request.getParameter("keyword");
		Map<String, Object>map=utillService.getStart(page, pagesize);
		List<getMembersDto>getMembersDtos=getDtos(map, keyword);
		if(getMembersDtos.size()==0) {
			model.addAttribute("page", 1);
			model.addAttribute("totalPage", 1);
			model.addAttribute("dtos", null);
			return;
		}
		int totalPage=utillService.getTotalPage(getMembersDtos.get(0).getTotalCount(), pagesize);
		if(page>totalPage) {
			throw  new RuntimeException("마지막페이지입니다");
		}
		List<memberDto>memberDtos=new ArrayList<memberDto>();
		for(getMembersDto g: getMembersDtos) {
			memberDto memberDto=new memberDto();
			memberDto.setCreated(g.getCreated());
			memberDto.setEmail(g.getEmail());
			memberDto.setGender(g.getGender());
			memberDto.setRole(g.getRole());
			memberDtos.add(memberDto);
		}
		model.addAttribute("dtos", memberDtos);
		model.addAttribute("page", page);
		model.addAttribute("totalPage", totalPage);
		
	}
	private List<getMembersDto> getDtos(Map<String, Object>map,String keyword) {
		System.out.println("getDtos");
		if(keyword.isBlank()||keyword==null||keyword.equals("null")) {
			return memberDao.selectAll(map);
		}
		map.put("keyword", keyword);
		return memberDao.selectAllByKeyword(map);
		
	}
	public JSONObject insert(tryInsertDto insertDto,HttpServletRequest request) {
		logger.debug("insert");
		String message="알수 없는 오류 발생";
		confrim(insertDto);
		try {
			boolean flag=(boolean)request.getSession().getAttribute("flag");
			if(flag) {
				insert(insertDto);
				request.getSession().invalidate();
				return utillService.makeJson(true, "회원가입 성공");
			}
			message="인증이 완료 되지 않았습니다";
		} catch (RuntimeException e) {
			message="인증요청을 번저 부탁드립니다";
		}
		throw utillService.makeRunTimeEx(message, "insert");
		
		
	}
	public JSONObject login(tryLoginDto loginDto,HttpServletRequest request) {
		logger.debug("login");
		String message="로그인에 실패했습니다";
		try {
			memberDto memberDto=memberDao.findByEmail(loginDto.getEmail());
			System.out.println(memberDto.getPwd());
			if(!new BCryptPasswordEncoder().matches(loginDto.getPwd(), memberDto.getPwd())) {
				throw new RuntimeException("비밀번호 불일치");
			}
			request.getSession().setAttribute("email",loginDto.getEmail() );
			return utillService.makeJson(true, "로그인성공");
		} catch (NullPointerException e) {
			message="존재하는 이메일이 아닙니다";
		}catch (RuntimeException e) {
			message=e.getMessage();
		}
		return utillService.makeJson(false, message);
		
	}
	private void confrim(tryInsertDto insertDto) {
		logger.debug("confrim");
		String message=null;
		if(!insertDto.getPwd().equals(insertDto.getPwd2())) {
			message="비밀번호 불일치";
		}else if(countByEmail(insertDto.getEmail())) {
			message="이미존재하는 이메일입니다";
		}else {
			logger.debug("회원가입 유효성 통과");
			return;
		}
		throw utillService.makeRunTimeEx(message,"confrim" );
	}
	 public memberDto insertOauth(memberDto uservo) {
		 	logger.debug("insertOauth");
	        int already=memberDao.countByEmail(uservo.getEmail());
	        if(already==0){
	        	logger.debug(uservo+"로그인 회원가입시도");
	        	uservo.setPwd(new BCryptPasswordEncoder().encode("oauthpwd"));
	        	memberDao.insert(uservo);
	        }
	        return uservo;
	    }
	public JSONObject checkSame(HttpServletRequest request) {
		logger.debug("checkSame");
		String message=null;
		boolean flag=countByEmail(request.getParameter("email"));
		if(flag) {
			message="이미존재 하는 이메일입니다";
			return utillService.makeJson(true, message);
		}else {
			message="사용가능한 이메일입니다";
		}
		return utillService.makeJson(false, message);
	}
	private boolean countByEmail(String email) {
		logger.debug("countByEmail");
		if(memberDao.countByEmail(email)!=0) {
			return true;
		}
		return false;
	}
	
	public void insert(tryInsertDto insertDto) {
		logger.debug("insert");
		String message="회원가입에 성공했습니다";
		try {
			insertDto.getAddress();
			memberDto dto=memberDto.builder()
									.email(insertDto.getEmail())
									.address(insertDto.getPostcode()+","+insertDto.getAddress()+","+insertDto.getDetailAddress())
									.created(Timestamp.valueOf(LocalDateTime.now()))
									.name(insertDto.getName())
									.gender(insertDto.getGender())
									.pwd(new BCryptPasswordEncoder().encode(insertDto.getPwd()))
									.provider("home")
									.role("user")
									.build();
						
			memberDao.insert(dto);
		
		} catch (Exception e) {
			e.printStackTrace();
			message="회원가입에 실패했습니다";
			throw utillService.makeRunTimeEx(message, "insert");
		}	
	}
	
}
