package com.jdbc.project1;

import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ProductConsole {
	public static void main(String[] args) throws Exception {
		Scanner sc = new Scanner(System.in);
		ProductManager manager = new ProductManager();

		try {
			while(true) {
				manager.logInMenu();
				int type=sc.nextInt();

				switch(type) {
				case 1:
					int rs = manager.tryLogin();
					if(rs!=1) {
						break;
					}
					String result = ProductDAO.getUSERID();

					if(result.equalsIgnoreCase("master")) {
						while(true) { // �����ڸ޴�
							manager.masterMenu();
							int type2=sc.nextInt();

							switch(type2) {
							case 1:
								manager.productRegister();
								break;
							case 2:
								manager.showAll();
								break;
							case 3:
								manager.edit();
								break;
							case 4:
								manager.remove();
								break;
							case 5:
								manager.SearchFor();
								break;						
							case 6:
								manager.orderSearch();
								break;						
							case 7:
								System.out.println("���α׷��� �����մϴ�");
								System.exit(0);	
							default:
								System.out.println("�߸� ���� �ϼ̽��ϴ�.");
								continue;
							}
						}//while
					}else {
						while(true) { // ����ڸ޴�
							manager.userMenu();
							int type2=sc.nextInt();

							switch(type2) {
							case 1:
								manager.showAll2();
								break;
							case 2:
								manager.SearchFor();
								break;						
							case 3:
								manager.orderRequest();
								break;
							case 4:
								manager.orderSearch2();
								break;
							case 5:
								System.out.println("���α׷��� �����մϴ�");
								System.exit(0);	
							case 6:
								System.out.print("������ Ż���Ͻðڽ��ϱ�?(Y/N)");
								sc.nextLine();
								String r = sc.nextLine();

								if(r.equalsIgnoreCase("Y")) {
									manager.withdrawalMember();
									System.exit(0);	
								}else {
									break;
								}
							default:
								System.out.println("�߸� ���� �ϼ̽��ϴ�.");
								continue;
							}
						}//while
					}//else
				case 2: //ȸ������
					manager.userRegister();
					break;
				case 3:
					System.out.println("���α׷��� �����մϴ�");
					System.exit(0);	
				default:
					System.out.println("�߸� ���� �ϼ̽��ϴ�.");
					continue;
				}
			}//while
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (InputMismatchException e) {
			System.out.println("���ڸ� �Է°����մϴ�.");
		}

	}
}
