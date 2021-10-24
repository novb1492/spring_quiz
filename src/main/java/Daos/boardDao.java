package Daos;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.kim.demo4.board.boardDto;
import com.kim.demo4.board.getAllBoardDto;

@Repository
public interface boardDao {
	
	public int insert(boardDto boardDto);
	public List<getAllBoardDto>selectAll(Map<String, Object>map);
	public List<getAllBoardDto>selectAllWithKeyword(Map<String, Object>map);
	public boardDto findById(int id);
	public int deleteById(int id);
	public int plusHit(Map<String, Integer>map);
}
