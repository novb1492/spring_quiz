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
import com.kim.demo4.apis.aws.awsConfig;
import com.kim.demo4.apis.kakao.kakaoService;
import com.kim.demo4.uploadService.uploadService;

import Daos.boardDao;

@Service
public class boardService {
	
	 private final static Logger logger=LoggerFactory.getLogger(boardService.class);
	 private final String getEmail=stringEnums.email.getValue();
		private final String dtostext=stringEnums.dtos.getValue();
	 private final int pageSize=2;
	 @Autowired
	 private boardDao boardDao;
	 @Autowired
	 private uploadService uploadService;
	 public void deleteAll(JSONObject jsonObject) {
		System.out.println("deleteAll");
		if(jsonObject.get("detail").equals("insert")) {
			List<String>Image=utillService.getImgSrc(jsonObject.get("text").toString());
			if(!Image.isEmpty()) {
				for(String s:Image) {
					uploadService.deleteImg(s.split("/")[5]);
				}
			}
		}else if(jsonObject.get("detail").equals("update")) {
			
		}
	}
	 public JSONObject update(tryUpdateArticleDto updateArticleDto,HttpSession session) {
		System.out.println("update");
		boardDto boardDto=findArticle(updateArticleDto.getId());
		if(boardDto==null) {
			return utillService.makeJson(false, "존재하지 않는 게시물입니다");
		}
		String email=(String)session.getAttribute("email");
		if(!email.equals(boardDto.getEmail())) {
			return utillService.makeJson(false, "작성자 불일치");
		}
		Map<String, Object>map=new HashMap<String, Object>();
		map.put("title", updateArticleDto.getTitle());
		map.put("text",  updateArticleDto.getText());
		map.put("id", boardDto.getId());
		boardDao.updateArticle(map);
		List<String>originImage=utillService.getImgSrc(boardDto.getText());
		List<String>dtoImages=utillService.getImgSrc(updateArticleDto.getText());

		if(dtoImages.isEmpty()) {
			if(!originImage.isEmpty()) {
				System.out.println("모든사진이 삭제되었습니다");
				for(String s:originImage) {
					uploadService.deleteImg(s.split("/")[5]);
				}
			}
		}else if(!originImage.isEmpty()) {
			List<String>array=utillService.getDeleteImgs(originImage, dtoImages);
			for(String s:array) {
				uploadService.deleteImg(s);
			}
		}
		return utillService.makeJson(true, "글수정완료");
		
	 }
	 
	 public JSONObject delete(HttpServletRequest request,HttpSession session) {
			logger.debug("delete");
			int id=Integer.parseInt(request.getParameter("bid"));
			boardDto boardDto=findArticle(id);
			if(!boardDto.getEmail().equals(session.getAttribute(getEmail).toString())){
				return utillService.makeJson(false, "작성자가 일치하지 않습니다");
			}
			boardDao.deleteById(id);
			List<String>imgs=utillService.getImgSrc(boardDto.getText());
			if(imgs.size()>0) {
				logger.debug("사진이 존재하는 게시글삭제");
				for(String s:imgs) {
					uploadService.deleteImg(s.split("/")[5]);
				}
				
			}
		
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
		Map<String, Object>map=utillService.getStart(page, pageSize);
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
	 private List<getAllBoardDto> getGetAllBoardDtos(Map<String, Object>map,String keyword) {
		logger.debug("getGetAllBoardDtos");
		if(keyword==null||keyword.isBlank()) {
			return boardDao.selectAll(map);
		}
		map.put("keyword", keyword);
		return  boardDao.selectAllWithKeyword(map);
	}
	public boardDto getArticle(HttpServletRequest request) {
		logger.debug("getArticle");
		int id=Integer.parseInt(request.getParameter("bid"));
		boardDto boardDto=findArticle(id);
		Map<String, Integer>map=new HashMap<String, Integer>();
		boardDto.setHit(boardDto.getHit()+1);
		map.put("id", id);
		map.put("hit", boardDto.getHit());
		boardDao.plusHit(map);
		return boardDto;
	}
	public boardDto findArticle(int id) {
		logger.debug("getArticle");
		boardDto boardDto=boardDao.findById(id);
		return boardDto;
	}
	 
	 
}
