package kr.or.ddit.mvc.service;

import kr.or.ddit.mvc.vo.BoardVo;

import java.util.List;

public interface IBoardService  {
    /**
     * 게시판 전체 글 목록 조회
     * @return BoardVo의 리스트 반환
     */
    public List<BoardVo> selectBoardAllList();

    /**
     * 게시판의 작성한 새글을 등록한다.
     * @param boardVo 게시글의 정보를 담고 있는 vo
     * @return DB 등록 성공한 갯수 반환(성공:1, 실패:0)
     */
    public int insertBoard(BoardVo boardVo);

    /**
     * 게시글번호에 해당하는 정보를 조회한다
     * @param boardNo 게시글번호
     * @return 게시글에 해당하는 정보를 반환한다.
     */
    public BoardVo selectBoard(int boardNo);

    /**
     * 게시글의 제목과 내용을 수정한다.
     * @param boardVo 수정할 내용이 담긴 객체이다
     * @return DB 수정 성곤한 갯수 반환(성공:1, 실패:0)
     */
    public int updateBoard(BoardVo boardVo);

    /**
     * 게시글번호에 해당하는 게시글을 삭제한다.
     * @param boardNo 삭제 할 게시글의 글번호
     * @return DB 삭제 성곤한 갯수 반환(성공:1, 실패:0)
     */
    public int deleteBoard(int boardNo);

    /**
     * 검색기능 - 게시글제목으로 검색
     * @param boardTitle 입력된 검색어
     * @return 검색어로 검색된 목록 반환
     */
    public List<BoardVo> selectSearchBoard(String boardTitle);

    /**
     * 게시글 조회수 증가
     * @return 증가 실행 성공한 게시글 수
     */
    public int updateBoardCnt(int boardNo);
}
