package com.jdbc.project1;

import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class ProductManager {
	private ProductDAO productDao;
	private Scanner sc = new Scanner(System.in);

	public ProductManager(){
		productDao=new ProductDAO(); 
	}

	public void logInMenu() { //처음 메뉴
		System.out.println("=========================================상품관리 프로그램==========================================");
		System.out.println("1.로그인 2.회원가입");
		System.out.println("================================================================================================");
		System.out.print("선택하세요 : ");
	}

	public int tryLogin() throws SQLException { //로그인
		System.out.println("아이디, 비밀번호를 입력하세요.");
		System.out.print("ID : ");
		String id = sc.nextLine();
		System.out.print("PWD : ");
		String pwd = sc.nextLine();

		int rs = productDao.logIn(id, pwd);
		return rs;
	}

	public void userRegister() throws SQLException { //회원가입
		System.out.println("---------회원가입--------");	
		System.out.print("ID : ");
		String c_id=sc.nextLine();
		System.out.print("회사명 : ");
		String c_name=sc.nextLine();
		System.out.print("PWD : ");
		String pwd=sc.nextLine();

		MemberDTO mdto = new MemberDTO();
		mdto.setC_id(c_id);
		mdto.setC_name(c_name);
		mdto.setPwd(pwd);
		productDao.registerUser(mdto);

		System.out.println("회원가입 성공");
	}

	public void masterMenu() { //관리자 메뉴
		System.out.println("=============================================Menu===============================================");
		System.out.println("1.상품등록 2.전체조회 3.상품수정 4.상품삭제 5.상품검색 6.발주내역조회 7.종료");
		System.out.println("================================================================================================");
		System.out.print("선택하세요 : ");
	}

	public void productRegister() throws SQLException { //상품등록(관리자)
		System.out.println("상품명, 입고가, 판매가, 수량, 썸네일(url) 입력!");	
		System.out.print("상품명 : ");
		String p_name=sc.nextLine();
		System.out.print("입고가 : ");
		int u_price=sc.nextInt();
		System.out.print("판매가 : ");
		int s_price=sc.nextInt();
		System.out.print("수량 : ");
		int ea=sc.nextInt();
		sc.nextLine();
		System.out.print("썸네일 : ");
		String thumbnail=sc.nextLine();

		ProductDTO dto = new ProductDTO();
		dto.setP_name(p_name);
		dto.setU_price(u_price);
		dto.setS_price(s_price);
		dto.setEa(ea);
		dto.setThumbnail(thumbnail);
		int cnt=productDao.insertProduct(dto);

		String result=(cnt>0)?"상품등록 성공":"상품등록 실패";
		System.out.println(result);
	}

	public void showAll() throws SQLException { //전체조회(관리자)
		List<ProductDTO> list = productDao.selectAll();
		for(int i=0;i<list.size();i++) {
			ProductDTO dto=list.get(i);

			System.out.println(dto.getNo()+"\t\t"+dto.getP_name()+"\t\t"+dto.getU_price()
			+"\t"+dto.getS_price()+"\t"+dto.getEa()+"\t"+dto.getRegdate()+"\t"+dto.getThumbnail());
		}
		System.out.println();
	}

	public void edit() throws SQLException, InputMismatchException { //상품수정(관리자)
		System.out.print("수정할 상품의 상품코드를 입력하세요 : ");
		int no=sc.nextInt();

		ProductDTO dto=productDao.selectByNo(no);

		System.out.println("----------------------------------------------------------------------------------------------------");
		System.out.println("상품코드\t\t상품명\t\t입고가\t판매가\t수량\t\t등록일\t\t썸네일");
		System.out.println("----------------------------------------------------------------------------------------------------");
		System.out.println(dto.getNo()+"\t\t"+dto.getP_name()+"\t\t"+dto.getU_price()+"\t"+dto.getS_price()
		+"\t"+dto.getEa()+"\t"+dto.getRegdate()+"\t"+dto.getThumbnail());


		sc.nextLine();
		System.out.print("변경할 이름 : ");
		String p_name=sc.nextLine();
		System.out.print("변경할 입고가 : ");
		int u_price=sc.nextInt();
		System.out.print("변경할 판매가 : ");
		int s_price=sc.nextInt();
		System.out.print("변경할 재고수량 : ");
		int ea=sc.nextInt();
		sc.nextLine();
		System.out.print("썸네일 변경 : ");
		String thumbnail=sc.nextLine();

		dto = new ProductDTO();
		dto.setNo(no);
		dto.setP_name(p_name);
		dto.setU_price(u_price);
		dto.setS_price(s_price);
		dto.setEa(ea);
		dto.setThumbnail(thumbnail);

		int cnt=productDao.updateProduct(dto);

		String res=(cnt>0)?"상품수정 성공":"상품수정 실패";
		System.out.println(res);

		dto=productDao.selectByNo(no);

		System.out.println("----------------------------------------상품 수정결과--------------------------------------------------");
		System.out.println("상품코드\t\t상품명\t\t입고가\t판매가\t수량\t\t등록일\t\t썸네일");
		System.out.println("----------------------------------------------------------------------------------------------------");
		System.out.println(dto.getNo()+"\t\t"+dto.getP_name()+"\t\t"+dto.getU_price()+"\t"+dto.getS_price()
		+"\t"+dto.getEa()+"\t"+dto.getRegdate()+"\t"+dto.getThumbnail()+"\n");
	}

	public void remove() throws NumberFormatException, SQLException { //상품삭제(관리자)
		System.out.println("삭제할 번호 입력!");
		int no=sc.nextInt();

		int cnt=productDao.deleteProduct(no);

		String res=(cnt>0)?"삭제 성공":"삭제 실패";
		System.out.println(res);
	}

	public void orderSearch() throws SQLException { //발주내역조회(관리자)

		List<offerDTO> list = productDao.searchAllOffer();

		for(int i=0;i<list.size();i++) {
			offerDTO dto=list.get(i);

			System.out.println(dto.getOrdernum()+"\t"+dto.getNo()+"\t"+dto.getEa()
			+"\t"+dto.getUser_id()+"\t\t"+dto.getMemo()+"\t\t"+dto.getRegdate());
		}
		System.out.println();
	}

	public void userMenu() { 					//회원 메뉴
		System.out.println("=============================================Menu===============================================");
		System.out.println("1.전체상품조회 2.상품검색 3.발주요청 4.발주이력조회 5.종료 6.회원탈퇴");
		System.out.println("================================================================================================");
		System.out.print("선택하세요 : ");
	}

	public void showAll2() throws SQLException { //전체조회(회원)

		List<UserProductDTO> list = productDao.selectAll2();

		for(int i=0;i<list.size();i++) {
			UserProductDTO dto=list.get(i);

			System.out.println(dto.getNo()+"\t\t"+dto.getP_name()+"\t\t"+dto.getS_price()
			+"\t\t"+dto.getEa()+"\t"+dto.getRegdate()+"\t"+dto.getThumbnail());
		}
		System.out.println();
	}

	public void SearchFor() { 					//조회유형 선택
		System.out.println("조회할 유형을 입력하세요");
		System.out.println("번호로 검색 : 1 / 상품명으로 검색 : 2 ");
		int no=sc.nextInt();
		try {
			switch(no) {
			case 1:
				showByNo();
				break;
			case 2:
				showByP_name();
				break;
			default:
				System.out.println("잘못 선택!");
			}//switch
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	public void showByNo() throws NumberFormatException, SQLException { //번호조회
		System.out.print("조회할 번호 : ");
		int no=sc.nextInt();

		String result = productDao.getUSERID();

		if(result.equalsIgnoreCase("master")) {
			ProductDTO dto=productDao.selectByNo(no);

			System.out.println("번호 : "+dto.getNo());
			System.out.println("상품명 : "+dto.getP_name());
			System.out.println("입고가 : "+dto.getU_price());
			System.out.println("판매가 : "+dto.getS_price());
			System.out.println("재고수량 : "+dto.getEa());
			System.out.println("등록일 : "+dto.getRegdate());
			System.out.println("썸네일 : "+dto.getThumbnail());
		}else {
			UserProductDTO dto2=productDao.selectByNo2(no);

			System.out.println("번호 : "+dto2.getNo());
			System.out.println("상품명 : "+dto2.getP_name());
			System.out.println("판매가 : "+dto2.getS_price());
			System.out.println("재고수량 : "+dto2.getEa());
			System.out.println("등록일 : "+dto2.getRegdate());
			System.out.println("썸네일 : "+dto2.getThumbnail());
		}
	}

	public void showByP_name() throws SQLException { //상품명조회
		sc.nextLine();
		System.out.print("조회할 상품명을 입력하세요 (ex.보조배터리=>배터리) : ");
		String p_name=sc.nextLine();
		String result = productDao.getUSERID();

		if(result.equalsIgnoreCase("master")) {
			List<ProductDTO> list=productDao.selectByP_name(p_name);

			System.out.println("--------------------------------------------------------------------------------------------------");
			System.out.println("상품코드\t\t상품명\t\t입고가\t판매가\t수량\t\t등록일\t\t썸네일");
			System.out.println("--------------------------------------------------------------------------------------------------");
			for(ProductDTO dto : list) {
				System.out.println(dto.getNo()+"\t\t"+dto.getP_name()+"\t\t"+dto.getU_price()+"\t"+dto.getS_price()
				+"\t"+dto.getEa()+"\t"+dto.getRegdate()+"\t"+dto.getThumbnail()+"\n");
			}
		}else {
			List<UserProductDTO> list=productDao.selectByP_name2(p_name);

			System.out.println("--------------------------------------------------------------------------------------------------");
			System.out.println("상품코드\t\t상품명\t\t판매가\t\t수량\t\t등록일\t\t썸네일");
			System.out.println("--------------------------------------------------------------------------------------------------");
			for(UserProductDTO dto : list) {
				System.out.println(dto.getNo()+"\t\t"+dto.getP_name()+"\t\t"+dto.getS_price()
				+"\t\t"+dto.getEa()+"\t"+dto.getRegdate()+"\t"+dto.getThumbnail()+"\n");
			}
		}
	}

	public void orderRequest() throws SQLException { //발주요청(사용자)
		System.out.println("상품명, 주문할 수량, 배송시 메모를 입력하세요.");	
		System.out.print("상품번호 : ");
		int no=sc.nextInt();
		System.out.print("주문수량 : ");
		int ea=sc.nextInt();
		sc.nextLine();
		System.out.print("배송메모 : ");
		String memo=sc.nextLine();

		offerDTO dto = new offerDTO();
		dto.setNo(no);
		dto.setEa(ea);
		dto.setMemo(memo);
		int cnt=productDao.insertOffer(dto);

		String result=(cnt>0)?"발주요청 성공":"발주요청 실패";
		System.out.println(result);
	}

	public void orderSearch2() throws SQLException { //발주내역조회(회원)

		List<offerDTO> list = productDao.searchOffer();

		for(int i=0;i<list.size();i++) {
			offerDTO dto=list.get(i);

			System.out.println(dto.getOrdernum()+"\t"+dto.getNo()+"\t"+dto.getEa()
			+"\t"+dto.getUser_id()+"\t\t"+dto.getMemo()+"\t\t"+dto.getRegdate());
		}
		System.out.println();
	}

	public void withdrawalMember() throws SQLException { //회원탈퇴(회원)
		productDao.deleteMember();
		System.out.println("그동안 사용해 주셔서 감사합니다.");
	}

}
