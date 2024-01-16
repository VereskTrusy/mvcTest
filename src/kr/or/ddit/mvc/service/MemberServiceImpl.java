package kr.or.ddit.mvc.service;

import kr.or.ddit.mvc.dao.IMemberDao;
import kr.or.ddit.mvc.dao.MemberDaoImpl;
import kr.or.ddit.mvc.vo.MemberVo;

import java.util.List;

public class MemberServiceImpl implements IMemberService {
    // DAO객체의 참조값이 저장될 변수 선언
    private IMemberDao dao;

    // 생성자
    public MemberServiceImpl() {
        dao = new MemberDaoImpl(); // DAO 객체 생성
    }

    @Override
    public int insertMember(MemberVo memVo) {
        return dao.insertMember(memVo);
    }

    @Override
    public int deleteMember(String memId) {
        return dao.deleteMember(memId);
    }

    @Override
    public int updateMember(MemberVo memVo) {
        return dao.updateMember(memVo);
    }

    @Override
    public List<MemberVo> getAllMember() {
        return dao.getAllMember();
    }

    @Override
    public int getMemberCount(String memId) {
        return dao.getMemberCount(memId);
    }
}
