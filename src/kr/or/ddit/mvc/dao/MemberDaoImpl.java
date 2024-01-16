package kr.or.ddit.mvc.dao;

import kr.or.ddit.mvc.vo.MemberVo;
import kr.or.ddit.util.JDBCUtil3;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MemberDaoImpl implements IMemberDao {

    @Override
    public int insertMember(MemberVo memVo) {
        int cnt = 0; // 반환값이 저장될 변수

        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = JDBCUtil3.getConnection();
            String sql = " INSERT INTO MYMEMBER (  MEM_ID, MEM_PASS, MEM_NAME, MEM_TEL, MEM_ADDR) " +
                         " VALUES( ?, ?, ?, ?, ? ) ";
            ps = conn.prepareStatement(sql);
            ps.setString(1, memVo.getMem_id());
            ps.setString(2, memVo.getMem_pass());
            ps.setString(3, memVo.getMem_name());
            ps.setString(4, memVo.getMem_tel());
            ps.setString(5, memVo.getMem_addr());
            cnt = ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("오류가 발생 했습니다.");
            e.printStackTrace();
        } finally {
            if(null != ps) try { ps.close(); } catch (SQLException e ) { e.printStackTrace(); }
            if(null != conn) try { conn.close(); } catch (SQLException e ) { e.printStackTrace(); }
        }

        return cnt;
    }

    @Override
    public int deleteMember(String memId) {
        int cnt = 0;

        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = JDBCUtil3.getConnection();
            String sql = " DELETE FROM MYMEMBER " +
                         " WHERE 1=1 " +
                         " AND MEM_ID = ? ";
            ps = conn.prepareStatement(sql);
            ps.setString(1, memId);
            cnt = ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("오류가 발생 했습니다.");
            e.printStackTrace();
        } finally {
            if(null != ps) try { ps.close(); } catch (SQLException e ) { e.printStackTrace(); }
            if(null != conn) try { conn.close(); } catch (SQLException e ) { e.printStackTrace(); }
        }

        return cnt;
    }

    @Override
    public int updateMember(MemberVo memVo) {
        int cnt = 0;

        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = JDBCUtil3.getConnection();
            String sql = " UPDATE MYMEMBER SET " +
                         "     MEM_PASS = TRIM(?) , " +
                         "     MEM_NAME = TRIM(?) , " +
                         "     MEM_TEL = TRIM(?) , " +
                         "     MEM_ADDR = TRIM(?) " +
                         " WHERE 1=1 " +
                         " AND MEM_ID = ? ";
            ps = conn.prepareStatement(sql);
            ps.setString(1, memVo.getMem_pass());
            ps.setString(2, memVo.getMem_name());
            ps.setString(3, memVo.getMem_tel());
            ps.setString(4, memVo.getMem_addr());
            ps.setString(5, memVo.getMem_id());
            cnt = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if(null != ps) try { ps.close(); } catch (SQLException e ) { e.printStackTrace(); }
            if(null != conn) try { conn.close(); } catch (SQLException e ) { e.printStackTrace(); }
        }

        return cnt;
    }

    @Override
    public List<MemberVo> getAllMember() {
        List<MemberVo> memberList = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = JDBCUtil3.getConnection();
            String sql = "select * from mymember";
            ps = conn.prepareStatement(sql);

            rs = ps.executeQuery();

            memberList = new ArrayList<MemberVo>();
            while (rs.next()) {
                // 1개의 레코드가 저장될 VO객체 생성
                MemberVo memVo = new MemberVo();

                // ResultSet객체의 데이터를 꺼내서 VO객체에 저장한다.
                memVo.setMem_id(rs.getString("MEM_ID"));
                memVo.setMem_pass(rs.getString("MEM_PASS"));
                memVo.setMem_name(rs.getString("MEM_NAME"));
                memVo.setMem_tel(rs.getString("MEM_TEL"));
                memVo.setMem_addr(rs.getString("MEM_ADDR"));

                // List에 VO객체를 추가한다.
                memberList.add(memVo);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if(null != rs) try { rs.close(); } catch (SQLException e ) { e.printStackTrace(); }
            if(null != ps) try { ps.close(); } catch (SQLException e ) { e.printStackTrace(); }
            if(null != conn) try { conn.close(); } catch (SQLException e ) { e.printStackTrace(); }
        }


        return memberList;
    }

    @Override
    public int getMemberCount(String memId) {
        int cnt = 0;

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try{
            conn = JDBCUtil3.getConnection();
            String sql = " SELECT COUNT(MEM_ID) AS IDCHK " +
                         " FROM MYMEMBER " +
                         " WHERE 1=1 " +
                         " AND MEM_ID = TRIM(?) ";
            ps = conn.prepareStatement(sql);
            ps.setString(1, memId);
            rs = ps.executeQuery();

            if(rs.next()) {
                cnt = rs.getInt("IDCHK");
            } else {
                System.out.println("오류 발생!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if(null != rs) try { rs.close(); } catch (SQLException e ) { e.printStackTrace(); }
            if(null != ps) try { ps.close(); } catch (SQLException e ) { e.printStackTrace(); }
            if(null != conn) try { conn.close(); } catch (SQLException e ) { e.printStackTrace(); }
        }

        return cnt;
    }
}
