package Daos;

import org.springframework.stereotype.Repository;

import com.kim.demo4.board.boardDto;

@Repository
public interface boardDao {
	
	public int insert(boardDto boardDto);
}
