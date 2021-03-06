package kh.picsell.dto;

public class ContestDTO {
private int contest_seq;
private String reason;
private String grouping;
private String title;
private String detail;
private String terms;
private String enddate;
private String hosttype;
private String host;
private String accept;
private String checking;
private String accept_date;
private String oriname;
private String sysname;
private int price;
private int contestfiles_seq;
private String rejection;
private String selectimage;

private String contest_img_seq;
private String enroll_sysname;
private String enroll_nickname;
private String selecting;
private String selectedimage;

public ContestDTO() {
   super();
}




public ContestDTO(int contest_seq, String reason, String grouping, String title, String detail, String terms,
		String enddate, String hosttype, String host, String accept, String checking, String accept_date,
		String oriname, String sysname, int price, int contestfiles_seq, String rejection, String selectimage) {
	super();
	this.contest_seq = contest_seq;
	this.reason = reason;
	this.grouping = grouping;
	this.title = title;
	this.detail = detail;
	this.terms = terms;
	this.enddate = enddate;
	this.hosttype = hosttype;
	this.host = host;
	this.accept = accept;
	this.checking = checking;
	this.accept_date = accept_date;
	this.oriname = oriname;
	this.sysname = sysname;
	this.price = price;
	this.contestfiles_seq = contestfiles_seq;
	this.rejection = rejection;
	this.selectimage = selectimage;
}


public int getContest_seq() {
   return contest_seq;
}

public void setContest_seq(int contest_seq) {
   this.contest_seq = contest_seq;
}

public String getReason() {
   return reason;
}

public void setReason(String reason) {
   this.reason = reason;
}

public String getTitle() {
   return title;
}

public void setTitle(String title) {
   this.title = title;
}

public String getDetail() {
   return detail;
}

public void setDetail(String detail) {
   this.detail = detail;
}

public String getTerms() {
   return terms;
}

public void setTerms(String terms) {
   this.terms = terms;
}

public String getEnddate() {
   return enddate;
}

public void setEnddate(String enddate) {
   this.enddate = enddate;
}

public String getHosttype() {
   return hosttype;
}

public void setHosttype(String hosttype) {
   this.hosttype = hosttype;
}

public String getHost() {
   return host;
}

public void setHost(String host) {
   this.host = host;
}

public String getAccept() {
   return accept;
}

public void setAccept(String accept) {
   this.accept = accept;
}

public String getChecking() {
   return checking;
}

public void setChecking(String checking) {
   this.checking = checking;
}

public String getAccept_date() {
   return accept_date;
}

public void setAccept_date(String accept_date) {
   this.accept_date = accept_date;
}

public String getOriname() {
   return oriname;
}

public void setOriname(String oriname) {
   this.oriname = oriname;
}

public String getSysname() {
   return sysname;
}

public void setSysname(String sysname) {
   this.sysname = sysname;
}

public int getPrice() {
   return price;
}

public void setPrice(int price) {
   this.price = price;
}

public int getContestfiles_seq() {
   return contestfiles_seq;
}

public void setContestfiles_seq(int contestfiles_seq) {
   this.contestfiles_seq = contestfiles_seq;
}

public String getRejection() {
   return rejection;
}

public void setRejection(String rejection) {
   this.rejection = rejection;
}


public String getContest_img_seq() {
	return contest_img_seq;
}


public void setContest_img_seq(String contest_img_seq) {
	this.contest_img_seq = contest_img_seq;
}


public String getEnroll_sysname() {
	return enroll_sysname;
}


public void setEnroll_sysname(String enroll_sysname) {
	this.enroll_sysname = enroll_sysname;
}

public String getEnroll_nickname() {
	return enroll_nickname;
}


public void setEnroll_nickname(String enroll_nickname) {
	this.enroll_nickname = enroll_nickname;
}


public String getSelecting() {
	return selecting;
}


public void setSelecting(String selecting) {
	this.selecting = selecting;
}


public String getSelectedimage() {
	return selectedimage;
}


public void setSelectedimage(String selectedimage) {
	this.selectedimage = selectedimage;
}




public String getGrouping() {
	return grouping;
}




public void setGrouping(String grouping) {
	this.grouping = grouping;
}




public String getSelectimage() {
	return selectimage;
}




public void setSelectimage(String selectimage) {
	this.selectimage = selectimage;
}






}