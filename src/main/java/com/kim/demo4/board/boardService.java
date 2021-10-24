package com.kim.demo4.board;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.kim.demo4.stringEnums;
import com.kim.demo4.utillService;
import com.kim.demo4.apis.kakao.kakaoService;

import Daos.boardDao;

@Service
public class boardService {
	
	 private final static Logger logger=LoggerFactory.getLogger(boardService.class);
	 private final String getEmail=stringEnums.email.getValue();
		private final String dtostext=stringEnums.dtos.getValue();
	 private final int pageSize=2;
	 @Autowired
	 private boardDao boardDao;
	 
	 public JSONObject delete(HttpServletRequest request,HttpSession session) {
			logger.debug("delete");
			/*boardDto boardDto=getArticle(request);
			if(!boardDto.getEmail().equals(session.getAttribute(getEmail).toString())){
				return utillService.makeJson(false, "작성자가 일치하지 않습니다");
			}*/
			boardDao.deleteById(24);
			return utillService.makeJson(true, "글삭제 완료");
	}
	 
	 public JSONObject insert(tryArticleInsertDto articleInsertDto,HttpSession session) {
		logger.debug("insert");
		try {
			boardDto dto=boardDto.builder()
					.created(Timestamp.valueOf(LocalDateTime.now()))
					.email((String)session.getAttribute(getEmail))
					.text(articleInsertDto.getText())
					.title(articleInsertDto.getTitle())
					.hit(0)
					.build();
					boardDao.insert(dto);
					return utillService.makeJson(true, "작성완료");
		} catch (Exception e) {
			return utillService.makeJson(false, "글 저장에 실패했습니다");
		}
		
	}
	 public void getArticles(HttpServletRequest request,Model model) {
		logger.debug("getArticles");
		System.out.println("fds");
		int page=Integer.parseInt(request.getParameter("page"));
		String keyword=request.getParameter("keyword");
		Map<String, Integer>map=utillService.getStart(page, pageSize);
		List<getAllBoardDto>getAllBoardDtos=getGetAllBoardDtos(map, keyword);
		List<boardDto>dtos=new ArrayList<boardDto>();
		//System.out.println(dtos.get(0).getCreated().toString().split("0")[0]);
		if(getAllBoardDtos.size()==0) {
			model.addAttribute("page", 1);
			model.addAttribute("totalPage", 1);
			model.addAttribute(dtostext, null);
			return;
		}
		int totalPage=utillService.getTotalPage(getAllBoardDtos.get(0).getTotalcount(), pageSize);
		if(page>totalPage) {
			throw new RuntimeException("마지막페이지입니다");
		}
		for(getAllBoardDto g:getAllBoardDtos) {
			boardDto dto=new boardDto();
			dto.setCreated(g.getCreated());
			dto.setEmail(g.getEmail());
			dto.setHit(g.getHit());
			dto.setId(g.getId());
			dto.setTitle(g.getTitle());
			dtos.add(dto);
		}
		model.addAttribute("page", page);
		model.addAttribute("totalPage", totalPage);
		model.addAttribute(dtostext, dtos);
		
	}
	 private List<getAllBoardDto> getGetAllBoardDtos(Map<String, Integer>map2,String keyword) {
		logger.debug("getGetAllBoardDtos");
		Map<String, Object>map=new HashMap<String, Object>();
		map.put("start",map2.get("start"));
		map.put("pagesize" ,map2.get("end"));
		if(keyword==null||keyword.isBlank()) {
			return boardDao.selectAll(map);
		}
		map.put("keyword", keyword);
		return  boardDao.selectAllWithKeyword(map);
	}
	public boardDto getArticle(HttpServletRequest request) {
		logger.debug("getArticle");
		int id=Integer.parseInt(request.getParameter("bid"));
		boardDto boardDto=boardDao.findById(id);
		return boardDto;
	}
	 
	 
}
