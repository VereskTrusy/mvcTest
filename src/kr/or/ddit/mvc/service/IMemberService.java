package kr.or.ddit.mvc.service;

import kr.or.ddit.mvc.vo.MemberVo;

import java.util.List;
import java.util.Map;

/**
 * Service객체는 DAO에 만들어진 메서드를 원하는 작업에 맞게 호출하여
 * 실행 결과를 받아오고, 받아온 결과를 Controller에 보내주는 역활을 한다.
 *
 * 우리 시간에는 DAO의 interface와 구조가 같게 만든다.
 *
 */
public interface IMemberService {
    /**
     * MemberVo 객체에 담겨진 자료를 insert하는 메서드
     *
     * @param memVo DB에 insert할 자료가 저장된 MemberVO객체
     * @return 작업성공:1, 작업실패:0 을 반환한다.
     */
    public int insertMember(MemberVo memVo);

    /**
     * 회원 ID를 매개변수로 받아서 해당 회원 정보를 삭제하는 메서드
     *
     * @param memId 삭제할 회원ID
     * @return 작업성공:1, 작업실패:0 을 반환한다.
     */
    public int deleteMember(String memId);

    /**
     * MemberVo 객체 자료를 이용하여 DB에 update하는 메서드
     *
     * @param memVo update할 회원 정보가 저장된 MemberVo 객체
     * @return 작업성공:1, 작업실패:0 을 반환한다.
     */
    public int updateMember(MemberVo memVo);

    /**
     * DB의 전체회원정보를 가져와서 List에 담아서 반환하는 메서드
     *
     * @return MemberVo객체가 저장된 List 객체
     */
    public List<MemberVo> getAllMember();

    /**
     * 회원ID를 매개변수로 받아서 회원ID의 갯수를 반환하는 메서드
     * @param memId 검색할 회원ID
     * @return 검색된 회원ID의 갯수
     */
    public int getMemberCount(String memId);

    /**
     * 수정할 필드명과 회원ID를 입력 받아서 필드명에 해당하는 정보만 update 하는 메서드
     * @param info
     * @return
     */
    public int updateInfo(Map<String, Object> info);
}
