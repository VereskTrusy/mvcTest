package kr.or.ddit.mvc.dao;

import kr.or.ddit.mvc.vo.BoardVo;
import kr.or.ddit.util.JDBCUtil3;
import oracle.sql.CLOB;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BoardDaoImpl implements IBoardDao {

    // 단일 인스턴스 변수
    private static BoardDaoImpl instance = null;

    // 생성자
    private BoardDaoImpl() { }

    // 인스턴스 획득
    public static BoardDaoImpl getInstance(){
        if(instance == null) instance = new BoardDaoImpl();
        return instance;
    }

    @Override
    public List<BoardVo> selectBoardAllList() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        List<BoardVo> list = null; // 변수만 선언하고 try 안에서 인스턴스 생성이 좋다

        try {
            conn = JDBCUtil3.getConnection();
            String sql = " SELECT  " +
                         "     BOARD_NO /* 글번호 */ " +
                         "     , BOARD_TITLE /* 제목 */ " +
                         "     , BOARD_WRITER /* 작성자 */ " +
                         "     , BOARD_CNT /* 조회수 */ " +
                         " FROM JDBC_BOARD " +
                         " ORDER BY BOARD_NO DESC ";
            ps = conn.prepareStatement(sql);

            rs = ps.executeQuery();

            list = new ArrayList<BoardVo>();

            while (rs.next()) {
                BoardVo boardVo = new BoardVo();
                boardVo.setBoard_no(rs.getInt("BOARD_NO"));
                boardVo.setBoard_title(rs.getString("BOARD_TITLE"));
                boardVo.setBoard_writer(rs.getString("BOARD_WRITER"));
                boardVo.setBoard_cnt(rs.getInt("BOARD_CNT"));

                list.add(boardVo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if(null != rs) try { rs.close(); } catch (SQLException e ) { e.printStackTrace(); }
            if(null != ps) try { ps.close(); } catch (SQLException e ) { e.printStackTrace(); }
            if(null != conn) try { conn.close(); } catch (SQLException e ) { e.printStackTrace(); }
        }

        return list;
    }

    @Override
    public int insertBoard(BoardVo boardVo) {
        Connection conn = null;
        PreparedStatement ps = null;

        int insertResult = 0;

        try {
            conn = JDBCUtil3.getConnection();
            String sql = " INSERT INTO JDBC_BOARD( BOARD_NO, BOARD_TITLE, BOARD_WRITER, BOARD_DATE, BOARD_CNT, BOARD_CONTENT ) " +
                         " VALUES(board_seq.NEXTVAL, ?, ?, SYSDATE, 0, ?) ";
            ps = conn.prepareStatement(sql);
            ps.setString(1, boardVo.getBoard_title());
            ps.setString(2, boardVo.getBoard_writer());
            ps.setString(3, boardVo.getBoard_content());
            insertResult = ps.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if(null != ps) try { ps.close(); } catch (SQLException e ) { e.printStackTrace(); }
            if(null != conn) try { conn.close(); } catch (SQLException e ) { e.printStackTrace(); }
        }

        return insertResult;
    }

    @Override
    public BoardVo selectBoard(int boardNo) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        BoardVo boardVo = null;

        try {
            conn = JDBCUtil3.getConnection();
            String sql = " SELECT  " +
                         "     BOARD_NO /* 글번호 */ " +
                         "     , BOARD_TITLE /* 제목 */ " +
                         "     , BOARD_WRITER /* 작성자 */ " +
                         "     , BOARD_CONTENT /* 내용 */ " +
                         "     , BOARD_DATE /* 작성일 */ " +
                         "     , BOARD_CNT /* 조회수 */ " +
                         " FROM JDBC_BOARD " +
                         " WHERE 1=1 " +
                         " AND BOARD_NO = ? ";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, boardNo);
            rs = ps.executeQuery();

            CLOB clob = null;
            StringBuilder sb = null;

            if(rs.next()) {
                boardVo = new BoardVo();
                boardVo.setBoard_no(rs.getInt("BOARD_NO"));
                boardVo.setBoard_title(rs.getString("BOARD_TITLE"));
                boardVo.setBoard_writer(rs.getString("BOARD_WRITER"));

                clob = (CLOB) rs.getClob("BOARD_CONTENT");
                sb = new StringBuilder();
                try (Reader reader = clob.getCharacterStream();
                     BufferedReader br = new BufferedReader(reader)){
                    String line;
                    while ((line = br.readLine()) != null){
                        //sb.append(line);
                        System.out.println(line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }


//                boardVo.setBoard_content(String.valueOf(rs.getClob("BOARD_CONTENT").toString()));
                boardVo.setBoard_date(String.valueOf(rs.getDate("BOARD_DATE")));
                boardVo.setBoard_cnt(rs.getInt("BOARD_CNT"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if(null != rs) try { rs.close(); } catch (SQLException e ) { e.printStackTrace(); }
            if(null != ps) try { ps.close(); } catch (SQLException e ) { e.printStackTrace(); }
            if(null != conn) try { conn.close(); } catch (SQLException e ) { e.printStackTrace(); }
        }
        return boardVo;
    }

    @Override
    public int updateBoard(BoardVo boardVo) {
        Connection conn = null;
        PreparedStatement ps = null;

        int updateResult = 0;

        try {
            conn = JDBCUtil3.getConnection();
            String sql = " UPDATE JDBC_BOARD SET  " +
                         "    BOARD_TITLE = ? /* 제목 */ " +
                         "    , BOARD_CONTENT = ? /* 내용 */ " +
                         " WHERE 1=1 " +
                         " AND BOARD_NO = ? ";
            ps = conn.prepareStatement(sql);
            ps.setString(1, boardVo.getBoard_title());
            ps.setString(2, boardVo.getBoard_content());
            ps.setInt(3, boardVo.getBoard_no());
            updateResult = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if(null != ps) try { ps.close(); } catch (SQLException e ) { e.printStackTrace(); }
            if(null != conn) try { conn.close(); } catch (SQLException e ) { e.printStackTrace(); }
        }

        return updateResult;
    }

    @Override
    public int deleteBoard(int boardNo) {
        Connection conn = null;
        PreparedStatement ps = null;

        int deleteResult = 0;

        try {
            conn = JDBCUtil3.getConnection();
            String sql = " DELETE FROM JDBC_BOARD " +
                         " WHERE 1=1 " +
                         " AND BOARD_NO = ? ";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, boardNo);
            deleteResult = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if(null != ps) try { ps.close(); } catch (SQLException e ) { e.printStackTrace(); }
            if(null != conn) try { conn.close(); } catch (SQLException e ) { e.printStackTrace(); }
        }

        return deleteResult;
    }

    @Override
    public List<BoardVo> selectSearchBoard(String boardTitle) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        List<BoardVo> list = null;

        try {
            conn = JDBCUtil3.getConnection();
            String sql = " SELECT  " +
                         "     BOARD_NO /* 글번호 */ " +
                         "     , BOARD_TITLE /* 제목 */ " +
                         "     , BOARD_WRITER /* 작성자 */ " +
                         "     , BOARD_CNT /* 조회수 */ " +
                         " FROM JDBC_BOARD " +
                         " WHERE 1=1 " +
                         " AND BOARD_TITLE LIKE CONCAT(CONCAT('%', TRIM(?)), '%') ";
            ps = conn.prepareStatement(sql);
            ps.setString(1, boardTitle);

            rs = ps.executeQuery();

            list = new ArrayList<BoardVo>();
            while (rs.next()) {
                BoardVo boardVo = new BoardVo();
                boardVo.setBoard_no(rs.getInt("BOARD_NO"));
                boardVo.setBoard_title(rs.getString("BOARD_TITLE"));
                boardVo.setBoard_writer(rs.getString("BOARD_WRITER"));
                boardVo.setBoard_cnt(rs.getInt("BOARD_CNT"));
                list.add(boardVo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if(null != rs) try { rs.close(); } catch (SQLException e ) { e.printStackTrace(); }
            if(null != ps) try { ps.close(); } catch (SQLException e ) { e.printStackTrace(); }
            if(null != conn) try { conn.close(); } catch (SQLException e ) { e.printStackTrace(); }
        }

        return list;
    }

    @Override
    public int updateBoardCnt(int boardNo) {
        Connection conn = null;
        PreparedStatement ps = null;

        int updateResult = 0;

        try {
            conn = JDBCUtil3.getConnection();
            String sql = " UPDATE JDBC_BOARD  " +
                         " SET " +
                         "     BOARD_CNT = NVL(BOARD_CNT,0)+1 " +
                         " WHERE 1=1 " +
                         " AND BOARD_NO = ? ";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, boardNo);
            updateResult = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //if(null != rs) try { rs.close(); } catch (SQLException e ) { e.printStackTrace(); }
            if(null != ps) try { ps.close(); } catch (SQLException e ) { e.printStackTrace(); }
            if(null != conn) try { conn.close(); } catch (SQLException e ) { e.printStackTrace(); }
        }

        return updateResult;
    }
}
