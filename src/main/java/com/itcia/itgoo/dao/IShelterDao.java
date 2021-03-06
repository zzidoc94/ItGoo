package com.itcia.itgoo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.JsonElement;
import com.itcia.itgoo.dto.Adopt;
import com.itcia.itgoo.dto.Commonmember;
import com.itcia.itgoo.dto.Company;
import com.itcia.itgoo.dto.Dfile;
import com.itcia.itgoo.dto.Dog;
import com.itcia.itgoo.dto.Member;
import com.itcia.itgoo.dto.VirtualAdopt;
import com.itcia.itgoo.dto.VirtualAdoptRecent;

public interface IShelterDao {

	@Insert("INSERT INTO COMPANY VALUES(#{companyid},#{companyname},#{companyboss},#{companyphone},#{companyemail},"
			+ "#{companylocation},#{companycard},#{companykind},#{enroll})")
	void insertShelter(Commonmember cmb);

	@Insert("INSERT INTO CLIENT VALUES(#{id},#{password},#{useraddress},#{useremail},#{username},"
			+ "#{phone},#{birthday},#{enabled})")
	void insertClient(Commonmember cmb);

	@Insert("INSERT INTO COMPANYPIC  VALUES(#{param1},#{param2})")
	void insertPic(String param1, String param2);

//사진 다중으로 보냄 ↑
//	@Insert("INSERT INTO COMPANY VALUES(#{companycard},#{companyid})")
//	void insertPichture(String companyid,String companycard);
	@Insert("INSERT INTO COMPANY VALUES(#{companyid},#{companyname},#{companyboss},#{companyphone},#{companyemail},"
			+ "#{companylocation},#{companycard},#{companykind},#{enroll})")
	void insertCompany(Commonmember cMember);

	// @Select("SELECT * FROM CLIENT WHERE id=#{id}")
	Member xduplicateid(Member mb);

	@Insert("insert into role values(#{param1},#{param2})")
	void insertRole(String id, String string);

	@Insert("INSERT INTO DOG VALUES(dog_seq.nextval,#{dogname},#{dogage},#{dogspecial},#{dogjungsung},#{shelterid},#{sex},0)")
	void insertDog(Dog dog);

	void inserDogPics(Dfile df);

	@Select("SELECT * FROM DOG WHERE SHELTERID=#{shelterid} and (status=0 or status=1)")
	List<Dog> shelterdelete(Dog dog);

	@Select("SELECT * FROM COMPANY WHERE companyid=#{companyid}")
	Company shelterMyInfo(Company c);

	int updateshelterlocation(Company cp);

	@Select("select dogpic from dogpics where dogid=#{dogid}")
	List<String> shelterdogpics(Integer dogid);

	@Select("select * from dog where dogid=#{dogid}")
	Dog shelterdeletedetail(Integer dogid);

	boolean dogDeleteBtn(Dog dog);

	void insertCompanyLocPic(String param1, String param2);

	void updateCompanyCardPic(String param1, String param2);

	boolean deleteCompanyLocPic(Company cp);

	void updateCompanyLocPic(String param1, String param2);

	void updatecompanyname(Company cp);

	void updatecompanyboss(Company cp);

	void updatecompanyphone(Company cp);

	void updatecompanyemail(Company cp);

	@Select("select * from dog join virtualadopt on virtualadopt.dogid = dog.dogid where shelterid=#{companyid}")
	List<VirtualAdopt> virtualAdoptList(String companyid);

	@Insert("insert into recentpics values(#{param2},#{param1})")
	void insertRecentPic(int recentid, String s);

	@SelectKey(statement = "select max(recentid) from recent", keyProperty = "recentid", before = false, resultType = Integer.class)
	@Insert("insert into recent values(recent_seq.nextval,#{id},#{dogid},#{message},#{title})")
	void insertRecent(VirtualAdoptRecent r);

	@Select("select * from recent where id=#{id} and dogid=#{dogid}")
	List<VirtualAdoptRecent> recentList(VirtualAdopt r);

	@Select("select * from recent where recentid=#{recentid}")
	VirtualAdoptRecent recentDetail(int recentid);

	@Delete("delete recent where recentid=#{recentid}")
	void recentDelete(int recentid);

	@Select("select * from dog where status=2 and shelterid=#{shelterid}")
	List<Dog> aleadyAdopt(@Param("shelterid")String name);

}
