package kr.or.ddit.mvc.service;

import kr.or.ddit.mvc.dao.BoardDaoImpl;
import kr.or.ddit.mvc.vo.BoardVo;

import java.util.List;

public class BoardServiceImpl implements IBoardService {

    private static BoardServiceImpl instance = null;
    private BoardDaoImpl dao;

    private BoardServiceImpl(){
        dao = BoardDaoImpl.getInstance();
    }

    public static BoardServiceImpl getInstance(){
        if(instance == null) instance = new BoardServiceImpl();
        return instance;
    }
    @Override
    public List<BoardVo> selectBoardAllList() {
        return dao.selectBoardAllList();
    }

    @Override
    public int insertBoard(BoardVo boardVo) {
        return dao.insertBoard(boardVo);
    }

    @Override
    public BoardVo selectBoard(int boardNo) {
        return dao.selectBoard(boardNo);
    }

    @Override
    public int updateBoard(BoardVo boardVo) {
        return dao.updateBoard(boardVo);
    }

    @Override
    public int deleteBoard(int boardNo) {
        return dao.deleteBoard(boardNo);
    }

    @Override
    public List<BoardVo> selectSearchBoard(String boardTitle) {
        return dao.selectSearchBoard(boardTitle);
    }

    @Override
    public int updateBoardCnt(int boardNo) {
        return dao.updateBoardCnt(boardNo);
    }
}
