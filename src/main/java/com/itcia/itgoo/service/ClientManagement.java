package com.itcia.itgoo.service;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.itcia.itgoo.dao.IAuctionDao;
import com.itcia.itgoo.dao.IClientDao;
import com.itcia.itgoo.dao.IMemberDao;
import com.itcia.itgoo.dto.Adopt;
import com.itcia.itgoo.dto.CareSheet;
import com.itcia.itgoo.dto.Company;
import com.itcia.itgoo.dto.Dog;
import com.itcia.itgoo.dto.Member;
import com.itcia.itgoo.dto.Reservation;
import com.itcia.itgoo.dto.SmallMeeting;
import com.itcia.itgoo.dto.VirtualAdopt;
import com.itcia.itgoo.share.UploadFile;

@Service
public class ClientManagement {
	@Autowired
	private IClientDao cDao;
	@Autowired
	private IMemberDao mDao;
	@Autowired
	private IAuctionDao aDao;

	private ModelAndView mav = new ModelAndView();

	public ModelAndView adoplist() {
		List<Dog> d = cDao.adoplist();

		System.out.println(d);
		mav.addObject("dogList", new Gson().toJson(d));
		mav.setViewName("adoptList");

		return mav;
	}

	public String adoptlistdetail(String dogid) {
		System.out.println("======================================\ndogid:" + dogid);
		Dog dog = cDao.dogDetail(dogid);
		dog.setDogpics(cDao.adoptlistdetail(dogid));
		return new Gson().toJson(dog);
	}

	public String myAdoptlistdetail(String dogid,Principal p) {
		System.out.println("======================================\ndogid:" + dogid);
		Adopt dog = cDao.AdoptDetail(dogid,p.getName());
		dog.setDogpics(cDao.adoptlistdetail(dogid));
		return new Gson().toJson(dog);
	}

	public ModelAndView applyadopt(MultipartHttpServletRequest multi, Adopt ad, Principal p) {
		ad.setPhase(1);
		ad.setId(p.getName());
		System.out.println("111111111111111111"+ad.getId());
		ad.setScore(0);
		UploadFile up = new UploadFile();
		String path = up.fileUp(multi.getFile("identifile"), "idfile");
		ad.setIdfile(path);
		cDao.insertapplyadopt(ad);
		mav.setViewName("clientMyPage");

		List<Member> mList = mDao.showmyinfo(ad);
		mav.addObject("mList",new Gson().toJson(mList));
		System.out.println("리스트++++++++++++++++++++"+p.getName());
		return mav;
	}


	public ModelAndView updateusername(Principal p,Member mb) {
		mb.setId(p.getName());
		mDao.updateusername(mb);
		return mav;
	}

	public ModelAndView updateuseremail(Principal p, Member mb) {
		mb.setId(p.getName());
		mDao.updateuseremail(mb);
		return null;
	}

	public ModelAndView updateuserphone(Principal p, Member mb) {
		mb.setId(p.getName());
		mDao.updatephone(mb);
		return null;
	}

	public ModelAndView updateuserbirth(Principal p, Member mb) {
		mb.setId(p.getName());
		mDao.updatebirth(mb);
		return null;
	}


	public ModelAndView updateuseraddress(Principal p, Member mb) {
		mb.setId(p.getName());
		mDao.updateadderess(mb);
		return null;
	}

	public ModelAndView showmyactivity(Principal p, Reservation rs) {
		System.out.println("액티비티 마이페이지가는중 ");
		System.out.println("도그번호"+rs);
		rs.setId(p.getName());
		List<Reservation> rList = cDao.showmyactivity(rs);
		System.out.println("알리스트 값은 "+rList);
		mav.addObject("rsList",new Gson().toJson(rList));
		mav.setViewName("client/MyActivityPage");
		return mav;
	}

	public ModelAndView finalsook(Principal p, Dog dog,int dogid) {
		dog.setId(p.getName());
		dog.setDogid(dogid);
		Dog choice=cDao.finalsook(dog);
		System.out.println("숙려기간 데이터 쏴주자");
		System.out.println("나의리스트는"+choice);
		mav.addObject("fifa",new Gson().toJson(choice));
		mav.setViewName("client/sook");
		return mav;
	}


	public ModelAndView regismallmeeting(Principal p, SmallMeeting sm) {
		mav= new ModelAndView();
		RedirectView redirectView = new RedirectView();
		String view = null;
		sm.setId((String) p.getName());
		cDao.regismallmeeting(sm);
		redirectView.setExposeModelAttributes(false);
		redirectView.setUrl("activitymyinfo");
		mav.setView(redirectView);
		return mav;
	}
	public ModelAndView updatedog(int dogid,String choice, Principal p, Reservation rs) {
		System.out.println("마지막 선택 업데이트 중");
		rs.setId(p.getName());
		rs.setDogid(dogid);

		if(choice.equals("go")){
			System.out.println("사랑으로 키우기");
			cDao.updateDog(rs);
			mav.setViewName("./clientMyPage");
		}
		if(choice.equals("stop")){
			System.out.println("강아지 입양해 좀!!!!!");
			cDao.deleteadopt(rs);
			mav.setViewName("adoptList");
		}
		return mav;
	}

	public ModelAndView finalcaresheet(int dogid, CareSheet cs, Principal p) {
		cs.setDogid(dogid);
		cs.setId(p.getName());
		System.out.println("cs의 값은 "+cs);
		List<CareSheet> cr =cDao.showcaresheet();
		System.out.println("cr의 값은 "+cr);
		System.out.println("cr의 값은 "+cr);
		mav.addObject("cr",new Gson().toJson(cr));
		mav.addObject("dogid",dogid);
		mav.setViewName("client/finalcaresheet");
		return mav;
	}


	public ModelAndView submitSheet(int dogid,String aJson, Principal p) {
		CareSheet cs=new CareSheet();
		cs.setId(p.getName());
		cs.setDogid(dogid);
		System.out.println("강아지아이디를 "+dogid);
		System.out.println("사라진아이디를 찾아라"+p.getName());
		System.out.println(cs.getDogid());
		//System.out.println(cs.getId());
		//System.out.println(cs.getQuestionnum());
		mav.setViewName("clentMyPage");
		mav.addObject("dogid",dogid);
		List<CareSheet> aList= new Gson().fromJson(aJson, new TypeToken<List<CareSheet>>() {}.getType());
		for(CareSheet a: aList){
			cs.setQuestionnum(a.getQuestionnum());
			cs.setAnswer(a.getAnswer());
			System.out.println("번호:"+a.getQuestionnum());
			System.out.println(cs.getAnswer());
			cDao.submitSheet(cs);
		}
		return mav;
	}

	public ModelAndView smalllist(SmallMeeting sm) {
		mav= new ModelAndView();
		String view = null;
		List<SmallMeeting> smList = cDao.smalllist(sm);
		mav.addObject("smList", new Gson().toJson(smList));
		mav.setViewName("client/smallList");

		return mav;
	}

	public ModelAndView myrecruitsmall(Principal p,SmallMeeting sm) {
		mav= new ModelAndView();
		String view = null;
		sm.setId((String) p.getName());
		
		List<SmallMeeting> asList = cDao.myappliedsmall(sm);
		System.out.println("asList="+asList);
		mav.addObject("asList", new Gson().toJson(asList));
		
		mav.setViewName("client/mySmallMeeting");
		return mav;
	}

	public ModelAndView smalllistdetail(Integer smallnumber) {
		mav = new ModelAndView();
		String view=null;
		SmallMeeting sldetail = cDao.smalllistdetail(smallnumber);
		sldetail.setSmallnumber(smallnumber);
		mav.addObject("sldetail",new Gson().toJson(sldetail));
		view="client/smallListDetail";
		mav.setViewName(view);

		return mav;
	}

	public ModelAndView joinsmallmeeting(Principal p, SmallMeeting sm) {
		mav= new ModelAndView();
		System.out.println("smallnumber="+sm.getSmallnumber());
		System.out.println("smalldogcnt="+sm.getSmalldogcnt());
		System.out.println("meetparticipatecnt="+sm.getMeetparticipatecnt());
		RedirectView redirectView= new RedirectView();
		sm.setId((String) p.getName());
		cDao.insertsmallmeeting(sm);
		cDao.updatesmallmeeting(sm);
		redirectView.setExposeModelAttributes(false);
		redirectView.setUrl("smalllist");
		 mav.setView(redirectView);
		return mav;
	}

	public ModelAndView myenrollsmall(Principal p, SmallMeeting sm) {
		mav= new ModelAndView();
		String view = null;
		sm.setId((String) p.getName());
		List<SmallMeeting> rsList = cDao.myrecruitsmall(sm);
		System.out.println("rsList="+rsList);
		mav.addObject("rsList", new Gson().toJson(rsList));
		mav.setViewName("client/myEnrollSmallMeeting");
		return mav;
	}

	public ModelAndView delmysmallmeeting(Principal p, SmallMeeting sm,RedirectAttributes attr) {
		mav= new ModelAndView();
		sm.setId((String) p.getName());
		boolean dsm = cDao.delmysmallmeeting(sm);
		if(dsm) {
			System.out.println("글 존재시 삭제 트랜잭션 성공");
			attr.addFlashAttribute("sm",sm);
		}else {
			System.out.println("삭제 트랜잭션 실패");
		}
		cDao.updatemeetparticipatecnt(sm);
		mav.setViewName("redirect:mysmallmeeting");
		return mav;
	}
	public ModelAndView myenrollsmalldetail(Integer smallnumber) {
		mav = new ModelAndView();
		String view=null;
		SmallMeeting esdetail = cDao.myenrollsmalldetail(smallnumber);
		esdetail.setSmallnumber(smallnumber);
		mav.addObject("esdetail",new Gson().toJson(esdetail));
		view="client/myEnrollSmallDetail";
		mav.setViewName(view);
		
		return mav;
	}

	public ModelAndView myvirtualadopt() {
		// TODO Auto-generated method stub
		return null;
	}

	public ModelAndView myauction(String id) {
		// TODO Auto-generated method stub
		mav.addObject("attendedList",new Gson().toJson(aDao.myAttended(id)));
		mav.addObject("myauctionList",new Gson().toJson(aDao.myUploadAuction(id)));
		mav.setViewName("client/MyAuction");
		return mav;
	}


	public ModelAndView virtualadoptapply(int dogid, VirtualAdopt va, Principal p) {
		System.out.println(p.getName());
		System.out.println(dogid);
		va.setDogid(dogid);
		System.out.println(va.getDogid());
		va.setId(p.getName());
		va.setPayday("15");
		cDao.virtualadoptapply(va);
		List<VirtualAdopt> vaList = cDao.myvirtual(va);
		mav.addObject("dogid",dogid);
		mav.addObject("vaList",new Gson().toJson(vaList));
		System.out.println("vaList는"+vaList);
		mav.setViewName("./client/MyVirtualAdopt");
		return mav;
	}
