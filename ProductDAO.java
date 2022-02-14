package com.jdbc.project1;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

//import com.jdbc.db.DBUtil;

public class ProductDAO {		
	static private String USERID;
	static private String USERPW;


	public static String getUSERID() {
		return USERID;
	}
	public static String getUSERPW() {
		return USERPW;
	}

	public int logIn(String id, String pwd) throws SQLException{ //로그인 검증
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;

		try {
			int result = 0;

			con=DBUtil.getConnection();

			String sql="select c_id,pwd from member where c_id=?";
			ps=con.prepareStatement(sql);

			ps.setString(1, id);

			rs=ps.executeQuery();

			if(rs.next()) {
				String resultId=rs.getString("c_id");
				String resultPw=rs.getString("pwd");

				if(resultPw.equalsIgnoreCase(pwd)) {
					USERID=resultId;
					USERPW=resultPw;
					result = 1;
					System.out.println("로그인 성공!"+USERID+"님 어서오세요.");
				}else {
					result = 2;
					System.out.println("비밀번호가 일치하지 않습니다.");
				}
			}else {
				result = 3;
				System.out.println("해당하는 아이디가 없습니다.");
			}
			return result;
		}finally {
			DBUtil.dbClose(rs, ps, con);
		}
	}

	public void registerUser(MemberDTO mdto) throws SQLException { //회원가입
		Connection con=null;
		PreparedStatement ps=null, ps2=null;

		try {
			con=DBUtil.getConnection();

			String sql="insert into member(no, c_id, c_name, pwd)"
					+ "values(product_seq.nextval, ?, ?, ?)";
			ps=con.prepareStatement(sql);

			ps.setString(1, mdto.getC_id());
			ps.setString(2, mdto.getC_name());			
			ps.setString(3, mdto.getPwd());			

			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			DBUtil.dbClose(ps, con);
		}try {
			con=DBUtil.getConnection();
			String sql="alter session set \"_ORACLE_SCRIPT\"=true";
			ps = con.prepareStatement(sql);

			ps.executeUpdate();
			ps.close();

			sql="create user "+mdto.getC_id()+" identified by "+mdto.getPwd()+" default tablespace users";
			ps2 = con.prepareStatement(sql);

			ps2.executeUpdate();
			ps2.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			DBUtil.dbClose(ps, con);
		}try {
			con=DBUtil.getConnection();
			String sql="grant create session to "+mdto.getC_id();
			ps = con.prepareStatement(sql);

			ps.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			DBUtil.dbClose(ps, con);
		}try {
			con=DBUtil.getConnection();
			String sql="grant select on v_product to "+mdto.getC_id();
			ps = con.prepareStatement(sql);

			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			DBUtil.dbClose(ps, con);
		}try {
			con=DBUtil.getConnection();
			String sql="alter session set \"_ORACLE_SCRIPT\"=true";
			ps = con.prepareStatement(sql);

			ps.executeUpdate();
			
			sql="grant select on v_offer to "+mdto.getC_id();
			ps2 = con.prepareStatement(sql);

			ps2.executeUpdate();
			ps2.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			DBUtil.dbClose(ps, con);
		}try {
			con=DBUtil.getConnection();
			String sql="alter session set \"_ORACLE_SCRIPT\"=true";
			ps = con.prepareStatement(sql);

			ps.executeUpdate();
			
			sql="grant insert on v_offer to "+mdto.getC_id();
			ps2 = con.prepareStatement(sql);

			ps2.executeUpdate();
			ps2.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			DBUtil.dbClose(ps, con);
		}
	}

	public List<UserProductDTO> selectAll2() throws SQLException { //전체조회(회원)
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;

		try {
			List<UserProductDTO> list = new ArrayList<UserProductDTO>();

			con=DBUtil.getConnection(USERID, USERPW);

			String sql="select * from javauser.v_product";
			ps=con.prepareStatement(sql);

			rs=ps.executeQuery();
			while(rs.next()) {
				int no=rs.getInt("no");
				String p_name=rs.getString("p_name");
				int s_price=rs.getInt("s_price");
				int ea=rs.getInt("ea");
				String thumbnail=rs.getString("thumbnail");
				Timestamp regdate=rs.getTimestamp("regdate");

				UserProductDTO dto = new UserProductDTO(no,p_name,s_price, ea, thumbnail, regdate);

				list.add(dto);
			}
			System.out.println("전체 조회 결과 : "+list.size());
			System.out.println("----------------------------------------------------------------------------------------------------");
			System.out.println("상품코드\t\t상품명\t\t판매가\t\t수량\t\t등록일\t\t썸네일");
			System.out.println("----------------------------------------------------------------------------------------------------");

			return list;

		}finally {
			DBUtil.dbClose(rs, ps, con);
		}
	}

	public int insertProduct(ProductDTO dto) throws SQLException { //상품등록(관리자)
		Connection con=null;
		PreparedStatement ps=null;
		try {
			con=DBUtil.getConnection();

			String sql="insert into product(no,p_name,u_price,s_price,ea,thumbnail)"
					+ " values(product_seq.nextval, ?, ?, ?, ?, ?)";
			ps=con.prepareStatement(sql);

			ps.setString(1, dto.getP_name());
			ps.setInt(2, dto.getU_price());			
			ps.setInt(3, dto.getS_price());			
			ps.setInt(4, dto.getEa());			
			ps.setString(5, dto.getThumbnail());

			int cnt=ps.executeUpdate();
			System.out.println("상품 등록 결과 cnt="+cnt);

			return cnt;
		}finally {
			DBUtil.dbClose(ps, con);
		}
	}

	public int updateProduct(ProductDTO dto) throws SQLException { //상품수정(관리자)
		Connection con=null;
		PreparedStatement ps=null;
		try {
			con=DBUtil.getConnection();

			String sql="update product set p_name=?,u_price=?,s_price=?,ea=?,thumbnail=? where no=?";
			ps=con.prepareStatement(sql);

			ps.setString(1, dto.getP_name());
			ps.setInt(2, dto.getU_price());
			ps.setInt(3, dto.getS_price());
			ps.setInt(4, dto.getEa());
			ps.setString(5, dto.getThumbnail());
			ps.setInt(6, dto.getNo());	

			int cnt=ps.executeUpdate();
			System.out.println("상품 수정 결과 cnt="+cnt);

			return cnt;
		}finally {
			DBUtil.dbClose(ps, con);
		}
	}

	public int deleteProduct(int no) throws SQLException { //상품삭제(관리자)
		Connection con=null;
		PreparedStatement ps=null;

		try {
			con=DBUtil.getConnection();

			String sql="delete from product where no=?";
			ps=con.prepareStatement(sql);

			ps.setInt(1, no);

			int cnt=ps.executeUpdate();
			System.out.println("삭제 결과 cnt="+cnt);

			return cnt;
		} finally {
			DBUtil.dbClose(ps, con);
		}
	}

	public List<ProductDTO> selectAll() throws SQLException { //전체조회(관리자)
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;

		try {
			List<ProductDTO> list = new ArrayList<ProductDTO>();

			//1,2
			con=DBUtil.getConnection();

			//3.ps
			String sql="select * from product order by no desc";
			ps=con.prepareStatement(sql);

			//4
			rs=ps.executeQuery();
			while(rs.next()) {
				int no=rs.getInt("no");
				String p_name=rs.getString("p_name");
				int u_price=rs.getInt("u_price");
				int s_price=rs.getInt("s_price");
				int ea=rs.getInt("ea");
				String thumbnail=rs.getString("thumbnail");
				Timestamp regdate=rs.getTimestamp("regdate");

				ProductDTO dto = new ProductDTO(no,p_name, u_price, s_price, ea, thumbnail, regdate);

				list.add(dto);
			}
			System.out.println("전체 조회 결과 : "+list.size());
			System.out.println("----------------------------------------------------------------------------------------------------");
			System.out.println("상품코드\t\t상품명\t\t입고가\t판매가\t\t수량\t\t등록일\t\t썸네일");
			System.out.println("----------------------------------------------------------------------------------------------------");

			return list;

		}finally {
			DBUtil.dbClose(rs, ps, con);
		}
	}

	public List<ProductDTO> selectByP_name(String p_name) throws SQLException { //상품명조회(관리자)
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;

		try {
			List<ProductDTO> list = new ArrayList<ProductDTO>();

			con=DBUtil.getConnection();

			String sql="select * from product where p_name like ?";
			ps=con.prepareStatement(sql);

			ps.setString(1, "%" + p_name + "%");

			rs=ps.executeQuery();
			while(rs.next()) {
				int no=rs.getInt("no");
				String p_name1=rs.getString("p_name");
				int u_price=rs.getInt("u_price");
				int s_price=rs.getInt("s_price");
				int ea=rs.getInt("ea");
				String thumbnail=rs.getString("thumbnail");
				Timestamp regdate=rs.getTimestamp("regdate");

				ProductDTO dto = new ProductDTO(no,p_name1,u_price,s_price,ea,thumbnail,regdate);
				list.add(dto);
			}
			System.out.println("조회 결과 : "+list.size());

			return list;

		}finally {
			DBUtil.dbClose(rs, ps, con);
		}
	}

	public ProductDTO selectByNo(int no) throws SQLException { //번호로 조회(관리자)
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;

		try {
			con=DBUtil.getConnection();

			String sql="select * from product where no=?";
			ps=con.prepareStatement(sql);

			ps.setInt(1, no);

			ProductDTO dto=new ProductDTO();

			rs=ps.executeQuery();
			if(rs.next()) {
				String p_name=rs.getString("p_name");
				int u_price=rs.getInt("u_price");
				int s_price=rs.getInt("s_price");
				int ea=rs.getInt("ea");
				String thumbnail=rs.getString("thumbnail");
				Timestamp regdate=rs.getTimestamp("regdate");

				dto.setNo(no);
				dto.setP_name(p_name);
				dto.setU_price(u_price);
				dto.setS_price(s_price);
				dto.setEa(ea);
				dto.setThumbnail(thumbnail);
				dto.setRegdate(regdate);
			}

			return dto;
		}finally {
			DBUtil.dbClose(rs, ps, con);
		}
	}
	
	public List<offerDTO> searchAllOffer() throws SQLException { //발주내역조회(관리자)
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;

		try {
			List<offerDTO> list = new ArrayList<offerDTO>();

			con=DBUtil.getConnection();

			String sql="select * from offer";
			ps=con.prepareStatement(sql);

			rs=ps.executeQuery();
			while(rs.next()) {
				int ordernum=rs.getInt("ordernum");
				int no=rs.getInt("no");
				int ea=rs.getInt("ea");
				String user_id=rs.getString("user_id");
				String memo=rs.getString("memo");
				Timestamp regdate=rs.getTimestamp("regdate");

				offerDTO dto = new offerDTO(ordernum,no,ea,user_id,memo,regdate);
				list.add(dto);
			}

			System.out.println("발주내역 조회 결과 : "+list.size());
			System.out.println("----------------------------------------------------------------------------------------------------");
			System.out.println("주문번호\t상품코드\t수량\t아이디\t\t\t배송메모\t\t\t등록일");
			System.out.println("----------------------------------------------------------------------------------------------------");

			return list;

		}finally {
			DBUtil.dbClose(rs, ps, con);
		}
	}
	
	public List<UserProductDTO> selectByP_name2(String p_name) throws SQLException { //상품명조회(사용자)
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;

		try {
			List<UserProductDTO> list = new ArrayList<UserProductDTO>();

			con=DBUtil.getConnection(USERID, USERPW);

			String sql="select * from javauser.v_product where p_name like ?";
			ps=con.prepareStatement(sql);

			ps.setString(1, "%" + p_name + "%");

			rs=ps.executeQuery();
			while(rs.next()) {
				int no=rs.getInt("no");
				String p_name1=rs.getString("p_name");
				int s_price=rs.getInt("s_price");
				int ea=rs.getInt("ea");
				String thumbnail=rs.getString("thumbnail");
				Timestamp regdate=rs.getTimestamp("regdate");

				UserProductDTO dto = new UserProductDTO(no,p_name1,s_price,ea,thumbnail,regdate);
				list.add(dto);
			}
			System.out.println("조회 결과 : "+list.size());

			return list;

		}finally {
			DBUtil.dbClose(rs, ps, con);
		}
	}

	public UserProductDTO selectByNo2(int no) throws SQLException { //번호로 조회(사용자)
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;

		try {
			con=DBUtil.getConnection(USERID, USERPW);

			String sql="select * from javauser.v_product where no=?";
			ps=con.prepareStatement(sql);

			ps.setInt(1, no);

			UserProductDTO dto=new UserProductDTO();

			rs=ps.executeQuery();
			if(rs.next()) {
				String p_name=rs.getString("p_name");
				int s_price=rs.getInt("s_price");
				int ea=rs.getInt("ea");
				String thumbnail=rs.getString("thumbnail");
				Timestamp regdate=rs.getTimestamp("regdate");

				dto.setNo(no);
				dto.setP_name(p_name);
				dto.setS_price(s_price);
				dto.setEa(ea);
				dto.setThumbnail(thumbnail);
				dto.setRegdate(regdate);
			}

			return dto;
		}finally {
			DBUtil.dbClose(rs, ps, con);
		}
	}

	public int insertOffer(offerDTO dto) throws SQLException { //발주요청(사용자)
		Connection con=null;
		PreparedStatement ps=null;
		try {
			con=DBUtil.getConnection(USERID,USERPW);

			String sql="insert into javauser.v_offer(ordernum,no,ea,user_id,memo)"
					+ " values("+dto.getIndex()+ ","+dto.getNo()+","+dto.getEa()+",'"+USERID+"','"+dto.getMemo()+"')";
			ps=con.prepareStatement(sql);

			int cnt=ps.executeUpdate();
			System.out.println("발주 요청 결과 cnt="+cnt);

			return cnt;
		}finally {
			DBUtil.dbClose(ps, con);
		}
	}

	public List<offerDTO> searchOffer() throws SQLException { //발주내역조회(사용자)
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;

		try {
			List<offerDTO> list = new ArrayList<offerDTO>();

			con=DBUtil.getConnection(USERID, USERPW);

			String sql="select * from javauser.v_offer where user_id like ?";
			ps=con.prepareStatement(sql);

			ps.setString(1, "%" + USERID + "%");

			rs=ps.executeQuery();
			while(rs.next()) {
				int ordernum=rs.getInt("ordernum");
				int no=rs.getInt("no");
				int ea=rs.getInt("ea");
				String user_id=rs.getString("user_id");
				String memo=rs.getString("memo");
				Timestamp regdate=rs.getTimestamp("regdate");

				offerDTO dto = new offerDTO(ordernum,no,ea,user_id,memo,regdate);
				list.add(dto);
			}

			System.out.println("발주내역 조회 결과 : "+list.size());
			System.out.println("----------------------------------------------------------------------------------------------------");
			System.out.println("주문번호\t상품코드\t수량\t아이디\t\t\t배송메모\t\t\t등록일");
			System.out.println("----------------------------------------------------------------------------------------------------");

			return list;

		}finally {
			DBUtil.dbClose(rs, ps, con);
		}
	}

	public void deleteMember() throws SQLException { //회원삭제(관리자)
		Connection con=null;
		PreparedStatement ps=null, ps2=null;

		try {
			con=DBUtil.getConnection();

			String sql="delete from member where c_id='"+USERID+"'";
			ps=con.prepareStatement(sql);

			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			DBUtil.dbClose(ps, con);
		}try {
			con=DBUtil.getConnection();

			String sql="delete from offer where user_id='"+USERID+"'";
			ps=con.prepareStatement(sql);

			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			DBUtil.dbClose(ps, con);
		}try {
			con=DBUtil.getConnection();
			String sql="alter session set \"_ORACLE_SCRIPT\"=true";
			ps = con.prepareStatement(sql);

			ps.executeQuery();
		
			sql="drop user "+USERID;
			ps2 = con.prepareStatement(sql);

			ps2.executeUpdate();
			

		} finally {
			DBUtil.dbClose(ps, con);
		}
		System.out.println("회원탈퇴 성공");
	}
}
