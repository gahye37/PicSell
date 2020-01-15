package kh.picsell.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import kh.picsell.dao.EventDAO;
import kh.picsell.dao.MoneyDAO;
import kh.picsell.dto.EventDTO;

public class EventService {
	
	@Autowired
	private EventDAO event_dao;
	
	// 출석체크
	
		// 1. 그날 출석체크 했는지 안했는지 체크 > 버튼 기능 조절 
		public EventDTO todayCheck(String nickname, String today) {
			EventDTO dto = event_dao.todayCheck(nickname, today);
			return dto;
		}
		
		// 2. 과거의 출석체크 정보 가져오기 
		public List<EventDTO> getEvent(String nickname) {
			List<EventDTO> list = event_dao.getEvent(nickname);
			return list;
		}
		
		// 오늘의 출석체크 정보 저장하기 
		@Transactional("txManager")
		public void insertCalendar(String nickname, String event_date) {
			// pointMapper pointMapper = new PointMapper();
			// 3. 출석체크한 사용자의 닉네임, 날짜 추가
			event_dao.addCalendar(nickname, event_date);
			// 4. 출석체크한 사용자에게 10포인트 추가
			event_dao.addPoint(nickname, event_date);
		}
}