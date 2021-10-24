package Daos;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.kim.demo4.board.boardDto;
import com.kim.demo4.board.getAllBoardDto;

@Repository
public interface boardDao {
	
	public int insert(boardDto boardDto);
	public List<getAllBoardDto>selectAll();
}
