package Daos;

import org.springframework.stereotype.Repository;

import com.kim.demo4.member.memberDto;



@Repository
public interface memberDao {
	public int insert(memberDto memberDTO);
	public int countByEmail(String email);
	public memberDto findByEmail(String email);
}
