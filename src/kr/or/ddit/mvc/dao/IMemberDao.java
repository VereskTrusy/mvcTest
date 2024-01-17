package kr.or.ddit.mvc.dao;

import kr.or.ddit.mvc.vo.MemberVo;

import java.util.List;
import java.util.Map;

/**
 * 실제 DB와 연결해서 SQL문을 수행하여 결과를 작성해서
 * Service에 전달하는 DAO의 interface이다
 *
 * 메서드 하나가 DB와 관련된 작업 1개를 수행하도록 작성한다.
 *
 *
 * @author VereskTrusy
 */
public interface IMemberDao {
    // 작업을 생각해서 DB에 어떤 작업을 해야하는지 생각해서 메서드를 만들어 주면된다.
    // 매개변수는 되도록 한개를 사용해서 처리할 수 있는 결과로 메서드를 작성하면 좋다

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
    public int updateInfo(Map<String,Object> info);
}
