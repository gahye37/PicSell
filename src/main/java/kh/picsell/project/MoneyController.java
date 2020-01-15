package kh.picsell.project;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import kh.picsell.dto.ChargeListDTO;
import kh.picsell.dto.DealListDTO;
import kh.picsell.dto.PointDTO;
import kh.picsell.service.MoneyService;


@Controller
public class MoneyController {

	@Autowired
	private MoneyService money_sv;

	@Autowired
	private HttpSession session;

	@Autowired
	private HttpServletRequest request;

	// Home 화면 
	@RequestMapping(value = "/")
	public String home() {
		// 로그인 연결하면 고쳐야 할 부분 
		session.setAttribute("loginInfo", "aaa");
		// 로그인 안 한 경우
		// session.setAttribute("loginInfo", null);
		String nickname = (String)session.getAttribute("loginInfo");
		
		System.out.println("도착");
		
		// 상세페이지에 뿌릴 DTO
		// 임시로 정한 DTO라서 실제 값 받아오도록 고쳐줘야함 
		//WriterImageUpDTO dto = new Writer_Img_UpDTO(2, "bbb", 1, "icon.png");
		//request.setAttribute("dto", dto);
		//if(nickname==null) {
			
		//}else {
		// 사진 구매 가능 여부 알기 위해서 point 받아옴 
		//int point = money_sv.getPoint(nickname);
		//request.setAttribute("point", point);
		// 이미 구매한 사용자는 '구매'버튼이 아닌 '다운로드'버튼을 보게된다
		// 구매 이력을 가져온다 
		//DealListDTO dto2 = money_sv.buyHistory(nickname, dto.getImg_seq()); 
			// 구매 이력이 없는 경우 
			//if(dto2==null) {
				//request.setAttribute("history", 0);
			// 구매 이력이 있는 경우 
			//}else {
				//request.setAttribute("history", 1);
			//}
			
		//}
		return "home";
	}

	// 충전 금액 선택 화면으로 가게하기 
	@RequestMapping(value = "/charge.do")
	public String plus() {
		return "charge";
	}

	// 충전 로직 완료 + form으로 데이터 받아서 충전 내역 화면에 뿌리기 
	@RequestMapping(value = "/chargeComplete.do")
	public String chargeComplete(String receipt_id, int price, 
			String payment_name, String requested_at) {
		// 1. 세션에서 아이디값을 가져온다 
		String nickname = (String) session.getAttribute("loginInfo");
		String deal_sort = "충전";
		String money_sort = "포인트";
		// 2. DB에 충전내역, 포인트 입출금내역을 저장한다
		// 3. 충전 내역을 가져와서 DTO에 담는다 
		ChargeListDTO dto = money_sv.Charge(nickname, receipt_id, price, payment_name, requested_at, deal_sort, money_sort);
		request.setAttribute("dto", dto);
		// 4. 충전 내역 화면으로 보낸다 
		return "chargeComplete";
	}

	/////////////////////////////////////////////////////////
	
	// 사진 구매하기 
	@RequestMapping("/buy.do")
	@ResponseBody
	public String buy(String writer_nickname, int deal_img_seq) {
		String buyer_nickname = (String)session.getAttribute("loginInfo");
		// 날짜
		Date today = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String deal_date = sdf.format(today);
		// 구분
		String buyer_deal_sort = "구매";
		String writer_deal_sort = "판매";
		String money_sort = "포인트";
		int point = 500;
		if(writer_nickname == buyer_nickname) {
			return "본인의 사진은 구매하실 수 없습니다..?";
		}
		money_sv.buy(deal_date, point, deal_img_seq, buyer_nickname, writer_nickname, buyer_deal_sort, writer_deal_sort, money_sort);
		return "";	
	}
	///////////////////////////////////////////////////////////////////

	// 내 포인트 확인하기 화면으로 가기 
	@RequestMapping("/myPoint.do")
	public String myPoint(String msg) {
		String nickname = (String)session.getAttribute("loginInfo");
		// 1. 내 포인트 확인하기 
		int point = money_sv.getPoint(nickname);
		request.setAttribute("point", point);
		// 2. 포인트 입출금 내역 확인하기 
		List<PointDTO> list = new ArrayList<>();
		list = money_sv.getPointList(nickname);
		request.setAttribute("list", list);
		request.setAttribute("msg", msg);
		return "myPoint";
	}

	///////////////////////////////////////////////////////////////////

	// 환급 페이지로 이동 
	@RequestMapping(value = "/moneyBack.do", produces="text/html; charset=UTF-8")
	public String moneyBack(String msg) {
		String nickname = (String)session.getAttribute("loginInfo");
		int profit = money_sv.getProfit(nickname);
		request.setAttribute("msg", msg);
		request.setAttribute("profit", profit);
		return "moneyBack";
	}

	// 환급하기 
	@RequestMapping(value="/moneyBackProc.do", produces="text/html; charset=UTF-8")
	public String money_back(int back_point) {
		String nickname = (String)session.getAttribute("loginInfo");
		int profit = money_sv.getProfit(nickname);
		request.setAttribute("profit", profit);
		if(profit<back_point) {
			String msg = "출금하시려는 포인트가 가지고 계신 포인트보다 많습니다.";
			request.setAttribute("msg", msg);
			return "moneyBack";
		}else if(back_point<10) {
			String msg = "10원 미만은 출금할 수 없습니다.";
			request.setAttribute("msg", msg);
			return "moneyBack";
		}else {
			Date today = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String formatted_requested_at = sdf.format(today);
			String deal_sort = "환급";
			String money_sort = "수익금";
			back_point = -back_point;
			money_sv.money_back(nickname, deal_sort, formatted_requested_at, back_point, money_sort);
			String msg = "환급이 완료되었습니다. 환급은 결제방식에 따라 최대 1~2일정도 소요됩니다.";
			request.setAttribute("msg", msg);
			List<PointDTO> list = new ArrayList<>();
			list = money_sv.getPointList(nickname);
			request.setAttribute("list", list);
			int point = money_sv.getPoint(nickname);
			request.setAttribute("point", point);
			return "myPoint";
		}
	}

	///////////////////////////////////////////////////////////////////
	
	// 포인트 전환 페이지로 이동 
	@RequestMapping("/change.do")
	public String change() {
		String nickname = (String)session.getAttribute("loginInfo");
		// 전환 가능 수익금을 가지고 감 
		int profit = money_sv.getProfit(nickname);
		request.setAttribute("profit", profit);
		return "change";
	}
	
	// 수익금을 포인트로 전환하기 
	@RequestMapping("/changeProc.do")
	public String changeProc(int money) {
		String nickname = (String)session.getAttribute("loginInfo");
		int profit = money_sv.getProfit(nickname);
		request.setAttribute("profit", profit);
		// 전환하려는 포인트가 1000원보다 적은 경우
		if(money<100) {
			String msg = "전환하려는 포인트가 1000원보다 작습니다.";
			request.setAttribute("msg", msg);
			return "change";
			//return 
		// 전환하려는 포인트가 수익금보다 작은 경우 
		}else if(profit<money) {
			String msg = "전환하려는 포인트가 잔여 수익금보다 많습니다.";
			request.setAttribute("msg", msg);
			return "change";
		// 정상 전환 
		}else {
			Date today = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String formatted_requested_at = sdf.format(today);
			String deal_sort = "포인트전환";
			String point_money_sort = "포인트";
			String profit_money_sort = "수익금";
			int change_profit = -money;
			money_sv.money_change(nickname, deal_sort, formatted_requested_at, 
					money, change_profit, point_money_sort, profit_money_sort);
			String msg = "수익금이 정상적으로 전환되었습니다. 내 포인트 확인하기 페이지에서 잔여 포인트를 확인해주세요.";
			request.setAttribute("msg", msg);
			List<PointDTO> list = new ArrayList<>();
			list = money_sv.getPointList(nickname);
			request.setAttribute("list", list);
			int point = money_sv.getPoint(nickname);
			request.setAttribute("point", point);
			return "myPoint";
		}
	}
	///////////////////////////////////////////////////////////////////

	// 구매 내역 확인하기
	@RequestMapping("/buy_list_check.do")
	public String buy_list_check() {
		String buyer_nickname = (String)session.getAttribute("loginInfo");
		List<DealListDTO> list = money_sv.buy_list_check(buyer_nickname);
		request.setAttribute("list", list);
		List<String> list2 = money_sv.buy_sysname(buyer_nickname);
		request.setAttribute("list2", list2);
		return "Buy_List_Check";
	}
	// 판매 내역 확인하기 
	@RequestMapping("/sell_list_check.do")
	public String sell_list_check() {
		String writer_nickname = (String)session.getAttribute("loginInfo");
		List<DealListDTO> list = money_sv.sell_list_check(writer_nickname);
		request.setAttribute("list", list);
		List<String> list2 = money_sv.buy_sysname(writer_nickname);
		request.setAttribute("list2", list2);
		return "Sell_List_Check";
	}



}
