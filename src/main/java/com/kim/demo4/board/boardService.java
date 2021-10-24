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

import com.kim.demo4.stringEnums;
import com.kim.demo4.utillService;
import com.kim.demo4.apis.kakao.kakaoService;

import Daos.boardDao;

@Service
public class boardService {
	
	 private final static Logger logger=LoggerFactory.getLogger(boardService.class);
	 private final String getEmail=stringEnums.email.getValue();
	 private final int pageSize=10;
	 @Autowired
	 private boardDao boardDao;
	 
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
	 public List<boardDto> getArticles(HttpServletRequest request) {
		logger.debug("getArticles");
		String page=request.getParameter("page");
		String keyword=request.getParameter("keyword");
		List<getAllBoardDto>getAllBoardDtos=getGetAllBoardDtos(page, keyword);
		List<boardDto>dtos=new ArrayList<boardDto>();
		//System.out.println(dtos.get(0).getCreated().toString().split("0")[0]);
		if(getAllBoardDtos.size()==0) {
			return null;
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
		return dtos;
	}
	 private List<getAllBoardDto> getGetAllBoardDtos(String page,String keyword) {
		logger.debug("getGetAllBoardDtos");
		Map<String, Object>map=new HashMap<String, Object>();
		map.put("start", Integer.parseInt(page));
		map.put("pagesize", pageSize);
		if(keyword==null||keyword.isBlank()) {
			return boardDao.selectAll(map);
		}
		map.put("keyword", keyword);
		return  boardDao.selectAllWithKeyword(map);
	}
	 
	 
}
