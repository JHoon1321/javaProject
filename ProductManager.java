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

	public void logInMenu() { //ó�� �޴�
		System.out.println("=========================================��ǰ���� ���α׷�==========================================");
		System.out.println("1.�α��� 2.ȸ������");
		System.out.println("================================================================================================");
		System.out.print("�����ϼ��� : ");
	}

	public int tryLogin() throws SQLException { //�α���
		System.out.println("���̵�, ��й�ȣ�� �Է��ϼ���.");
		System.out.print("ID : ");
		String id = sc.nextLine();
		System.out.print("PWD : ");
		String pwd = sc.nextLine();

		int rs = productDao.logIn(id, pwd);
		return rs;
	}

	public void userRegister() throws SQLException { //ȸ������
		System.out.println("---------ȸ������--------");	
		System.out.print("ID : ");
		String c_id=sc.nextLine();
		System.out.print("ȸ��� : ");
		String c_name=sc.nextLine();
		System.out.print("PWD : ");
		String pwd=sc.nextLine();

		MemberDTO mdto = new MemberDTO();
		mdto.setC_id(c_id);
		mdto.setC_name(c_name);
		mdto.setPwd(pwd);
		productDao.registerUser(mdto);

		System.out.println("ȸ������ ����");
	}

	public void masterMenu() { //������ �޴�
		System.out.println("=============================================Menu===============================================");
		System.out.println("1.��ǰ��� 2.��ü��ȸ 3.��ǰ���� 4.��ǰ���� 5.��ǰ�˻� 6.���ֳ�����ȸ 7.����");
		System.out.println("================================================================================================");
		System.out.print("�����ϼ��� : ");
	}

	public void productRegister() throws SQLException { //��ǰ���(������)
		System.out.println("��ǰ��, �԰�, �ǸŰ�, ����, �����(url) �Է�!");	
		System.out.print("��ǰ�� : ");
		String p_name=sc.nextLine();
		System.out.print("�԰� : ");
		int u_price=sc.nextInt();
		System.out.print("�ǸŰ� : ");
		int s_price=sc.nextInt();
		System.out.print("���� : ");
		int ea=sc.nextInt();
		sc.nextLine();
		System.out.print("����� : ");
		String thumbnail=sc.nextLine();

		ProductDTO dto = new ProductDTO();
		dto.setP_name(p_name);
		dto.setU_price(u_price);
		dto.setS_price(s_price);
		dto.setEa(ea);
		dto.setThumbnail(thumbnail);
		int cnt=productDao.insertProduct(dto);

		String result=(cnt>0)?"��ǰ��� ����":"��ǰ��� ����";
		System.out.println(result);
	}

	public void showAll() throws SQLException { //��ü��ȸ(������)
		List<ProductDTO> list = productDao.selectAll();
		for(int i=0;i<list.size();i++) {
			ProductDTO dto=list.get(i);

			System.out.println(dto.getNo()+"\t\t"+dto.getP_name()+"\t\t"+dto.getU_price()
			+"\t"+dto.getS_price()+"\t"+dto.getEa()+"\t"+dto.getRegdate()+"\t"+dto.getThumbnail());
		}
		System.out.println();
	}

	public void edit() throws SQLException, InputMismatchException { //��ǰ����(������)
		System.out.print("������ ��ǰ�� ��ǰ�ڵ带 �Է��ϼ��� : ");
		int no=sc.nextInt();

		ProductDTO dto=productDao.selectByNo(no);

		System.out.println("----------------------------------------------------------------------------------------------------");
		System.out.println("��ǰ�ڵ�\t\t��ǰ��\t\t�԰�\t�ǸŰ�\t����\t\t�����\t\t�����");
		System.out.println("----------------------------------------------------------------------------------------------------");
		System.out.println(dto.getNo()+"\t\t"+dto.getP_name()+"\t\t"+dto.getU_price()+"\t"+dto.getS_price()
		+"\t"+dto.getEa()+"\t"+dto.getRegdate()+"\t"+dto.getThumbnail());


		sc.nextLine();
		System.out.print("������ �̸� : ");
		String p_name=sc.nextLine();
		System.out.print("������ �԰� : ");
		int u_price=sc.nextInt();
		System.out.print("������ �ǸŰ� : ");
		int s_price=sc.nextInt();
		System.out.print("������ ������ : ");
		int ea=sc.nextInt();
		sc.nextLine();
		System.out.print("����� ���� : ");
		String thumbnail=sc.nextLine();

		dto = new ProductDTO();
		dto.setNo(no);
		dto.setP_name(p_name);
		dto.setU_price(u_price);
		dto.setS_price(s_price);
		dto.setEa(ea);
		dto.setThumbnail(thumbnail);

		int cnt=productDao.updateProduct(dto);

		String res=(cnt>0)?"��ǰ���� ����":"��ǰ���� ����";
		System.out.println(res);

		dto=productDao.selectByNo(no);

		System.out.println("----------------------------------------��ǰ �������--------------------------------------------------");
		System.out.println("��ǰ�ڵ�\t\t��ǰ��\t\t�԰�\t�ǸŰ�\t����\t\t�����\t\t�����");
		System.out.println("----------------------------------------------------------------------------------------------------");
		System.out.println(dto.getNo()+"\t\t"+dto.getP_name()+"\t\t"+dto.getU_price()+"\t"+dto.getS_price()
		+"\t"+dto.getEa()+"\t"+dto.getRegdate()+"\t"+dto.getThumbnail()+"\n");
	}

	public void remove() throws NumberFormatException, SQLException { //��ǰ����(������)
		System.out.println("������ ��ȣ �Է�!");
		int no=sc.nextInt();

		int cnt=productDao.deleteProduct(no);

		String res=(cnt>0)?"���� ����":"���� ����";
		System.out.println(res);
	}

	public void orderSearch() throws SQLException { //���ֳ�����ȸ(������)

		List<offerDTO> list = productDao.searchAllOffer();

		for(int i=0;i<list.size();i++) {
			offerDTO dto=list.get(i);

			System.out.println(dto.getOrdernum()+"\t"+dto.getNo()+"\t"+dto.getEa()
			+"\t"+dto.getUser_id()+"\t\t"+dto.getMemo()+"\t\t"+dto.getRegdate());
		}
		System.out.println();
	}

	public void userMenu() { 					//ȸ�� �޴�
		System.out.println("=============================================Menu===============================================");
		System.out.println("1.��ü��ǰ��ȸ 2.��ǰ�˻� 3.���ֿ�û 4.�����̷���ȸ 5.���� 6.ȸ��Ż��");
		System.out.println("================================================================================================");
		System.out.print("�����ϼ��� : ");
	}

	public void showAll2() throws SQLException { //��ü��ȸ(ȸ��)

		List<UserProductDTO> list = productDao.selectAll2();

		for(int i=0;i<list.size();i++) {
			UserProductDTO dto=list.get(i);

			System.out.println(dto.getNo()+"\t\t"+dto.getP_name()+"\t\t"+dto.getS_price()
			+"\t\t"+dto.getEa()+"\t"+dto.getRegdate()+"\t"+dto.getThumbnail());
		}
		System.out.println();
	}

	public void SearchFor() { 					//��ȸ���� ����
		System.out.println("��ȸ�� ������ �Է��ϼ���");
		System.out.println("��ȣ�� �˻� : 1 / ��ǰ������ �˻� : 2 ");
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
				System.out.println("�߸� ����!");
			}//switch
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	public void showByNo() throws NumberFormatException, SQLException { //��ȣ��ȸ
		System.out.print("��ȸ�� ��ȣ : ");
		int no=sc.nextInt();

		String result = productDao.getUSERID();

		if(result.equalsIgnoreCase("master")) {
			ProductDTO dto=productDao.selectByNo(no);

			System.out.println("��ȣ : "+dto.getNo());
			System.out.println("��ǰ�� : "+dto.getP_name());
			System.out.println("�԰� : "+dto.getU_price());
			System.out.println("�ǸŰ� : "+dto.getS_price());
			System.out.println("������ : "+dto.getEa());
			System.out.println("����� : "+dto.getRegdate());
			System.out.println("����� : "+dto.getThumbnail());
		}else {
			UserProductDTO dto2=productDao.selectByNo2(no);

			System.out.println("��ȣ : "+dto2.getNo());
			System.out.println("��ǰ�� : "+dto2.getP_name());
			System.out.println("�ǸŰ� : "+dto2.getS_price());
			System.out.println("������ : "+dto2.getEa());
			System.out.println("����� : "+dto2.getRegdate());
			System.out.println("����� : "+dto2.getThumbnail());
		}
	}

	public void showByP_name() throws SQLException { //��ǰ����ȸ
		sc.nextLine();
		System.out.print("��ȸ�� ��ǰ���� �Է��ϼ��� (ex.�������͸�=>���͸�) : ");
		String p_name=sc.nextLine();
		String result = productDao.getUSERID();

		if(result.equalsIgnoreCase("master")) {
			List<ProductDTO> list=productDao.selectByP_name(p_name);

			System.out.println("--------------------------------------------------------------------------------------------------");
			System.out.println("��ǰ�ڵ�\t\t��ǰ��\t\t�԰�\t�ǸŰ�\t����\t\t�����\t\t�����");
			System.out.println("--------------------------------------------------------------------------------------------------");
			for(ProductDTO dto : list) {
				System.out.println(dto.getNo()+"\t\t"+dto.getP_name()+"\t\t"+dto.getU_price()+"\t"+dto.getS_price()
				+"\t"+dto.getEa()+"\t"+dto.getRegdate()+"\t"+dto.getThumbnail()+"\n");
			}
		}else {
			List<UserProductDTO> list=productDao.selectByP_name2(p_name);

			System.out.println("--------------------------------------------------------------------------------------------------");
			System.out.println("��ǰ�ڵ�\t\t��ǰ��\t\t�ǸŰ�\t\t����\t\t�����\t\t�����");
			System.out.println("--------------------------------------------------------------------------------------------------");
			for(UserProductDTO dto : list) {
				System.out.println(dto.getNo()+"\t\t"+dto.getP_name()+"\t\t"+dto.getS_price()
				+"\t\t"+dto.getEa()+"\t"+dto.getRegdate()+"\t"+dto.getThumbnail()+"\n");
			}
		}
	}

	public void orderRequest() throws SQLException { //���ֿ�û(�����)
		System.out.println("��ǰ��, �ֹ��� ����, ��۽� �޸� �Է��ϼ���.");	
		System.out.print("��ǰ��ȣ : ");
		int no=sc.nextInt();
		System.out.print("�ֹ����� : ");
		int ea=sc.nextInt();
		sc.nextLine();
		System.out.print("��۸޸� : ");
		String memo=sc.nextLine();

		offerDTO dto = new offerDTO();
		dto.setNo(no);
		dto.setEa(ea);
		dto.setMemo(memo);
		int cnt=productDao.insertOffer(dto);

		String result=(cnt>0)?"���ֿ�û ����":"���ֿ�û ����";
		System.out.println(result);
	}

	public void orderSearch2() throws SQLException { //���ֳ�����ȸ(ȸ��)

		List<offerDTO> list = productDao.searchOffer();

		for(int i=0;i<list.size();i++) {
			offerDTO dto=list.get(i);

			System.out.println(dto.getOrdernum()+"\t"+dto.getNo()+"\t"+dto.getEa()
			+"\t"+dto.getUser_id()+"\t\t"+dto.getMemo()+"\t\t"+dto.getRegdate());
		}
		System.out.println();
	}

	public void withdrawalMember() throws SQLException { //ȸ��Ż��(ȸ��)
		productDao.deleteMember();
		System.out.println("�׵��� ����� �ּż� �����մϴ�.");
	}

}
