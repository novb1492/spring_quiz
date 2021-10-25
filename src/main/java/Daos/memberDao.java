package Daos;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.kim.demo4.member.getMembersDto;
import com.kim.demo4.member.memberDto;



@Repository
public interface memberDao {
	public int insert(memberDto memberDTO);
	public int countByEmail(String email);
	public memberDto findByEmail(String email);
	public List<getMembersDto>selectAll(Map<String, Object>map);
	public List<getMembersDto>selectAllByKeyword(Map<String, Object>map);
	public memberDto findById(int id);
	public int updateAddress(Map<String, Object>map);
}
