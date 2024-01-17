package kr.or.ddit.mvc.controller;

import kr.or.ddit.mvc.service.IMemberService;
import kr.or.ddit.mvc.service.MemberServiceImpl;
import kr.or.ddit.mvc.vo.MemberVo;

import java.lang.reflect.Member;
import java.util.*;

public class MemberController {
    // Service객체의 참조값이 저장될 변수 선언
    private IMemberService service;
    Scanner scan;

    // 생성자
    public MemberController() {
        scan = new Scanner(System.in);

        // Service 객체 생성
        service = MemberServiceImpl.getInstance();
    }

    public static void main(String[] args) {
        new MemberController().startMember();
    }

    /**
     * 시작 메서드
     */
    public void startMember() {
        while (true) {
            // 메뉴 출력 및 선택
            switch (displayMenu()) {
                case 1 :
                    insertMember();
                    break;
                case 2 :
                    deleteMember();
                    break;
                case 3 :
                    updateInfoAll();
                    break;
                case 4 :
                    selectAllMymember();
                    break;
                case 5 :
                    updateInfo();
                    break;
                case 0 :
                    return;
                default :
                    System.out.println("올바른 입력이 아닙니다.");
                    System.out.println("메뉴를 다시 선택 해주세요.");
                    break;
            }
        }
    }

    /**
     * 5. 개별 자료 수정
     */
    private void updateInfo() {
        System.out.println("5. 개별 자료 수정 입니다.");

        Map<String, Object> memberMap = new HashMap<String, Object>(); // 사용자 점보가 담길 변수
        String inMemId; // 사용자로부터 입력받은 아이디

        while (true) {
            System.out.println("수정 할 아이디를 입력하세요 > ");
            inMemId = scan.nextLine();

            int idChkResult = service.getMemberCount(inMemId);

            if(idChkResult > 0){
                System.out.println("계정 확인이 완료됐습니다.");
                break;
            } else {
                System.out.println("입력하신 아이디와 암호를 다시 확인 해주세요.");
            }
        }

        int fieldNum = 0;
        String updateField = null;
        String updateTitle = null;

        do{
            System.out.println();
            System.out.println("회원님의 정보 중 수정할 항목을 선택하세요");
            System.out.println("1.비밀번호수정  2.이름수정  3.전화번호수정  4.주소수정");
            System.out.println("5.나가기");

            // 어떤 메뉴 수정할지 선택
            fieldNum = scan.nextInt();
            scan.nextLine(); // 버퍼 비우기

            switch (fieldNum) {
                case 1 :
                    updateField = "MEM_PASS";
                    updateTitle = "비밀번호";
                    break;
                case 2 :
                    updateField = "MEM_NAME";
                    updateTitle = "회원명";
                    break;
                case 3 :
                    updateField = "MEM_TEL";
                    updateTitle = "전화번호";
                    break;
                case 4 :
                    updateField = "MEM_ADDR";
                    updateTitle = "주소";
                    break;
                default :
                    System.out.println("수정 항목을 잘못 선택하셨습니다. 다시 선택하세요.");
                    break;
            }
        }while (fieldNum < 1 || fieldNum > 4);

        System.out.println();
        System.out.print("새로운 " + updateTitle + " > ");
        String updateData = scan.nextLine();


        memberMap.put("updateField", updateField);
        memberMap.put("updateData", updateData);
        //memberMap.put("inMemId", inMemId);
        MemberVo memberVo = new MemberVo();
        memberVo.setMem_id(inMemId);
        memberMap.put("memberVo", memberVo);

        int updateResult = service.updateInfo(memberMap);

        if(updateResult > 0) System.out.println("수정 작업 성공");
        else System.out.println("수정 작업 실패");
    }

    /**
     * 모든 회원정보를 출력한다.
     */
    private void selectAllMymember() {
        List<MemberVo> memberVoList;

        System.out.println("4. 전체 자료 출력 입니다.");

        memberVoList = service.getAllMember();

        System.out.println("---------------------------------------------------");
        System.out.println("ID\t\tPASS\tNAME\t\tTEL\t\t\tADDR");
        System.out.println("---------------------------------------------------");
        if(null == memberVoList || memberVoList.isEmpty()) {
            System.out.println("   등록된 회원 목록이 없습니다.");
        } else {
            for (MemberVo row : memberVoList) {
                System.out.print(row.getMem_id() + "\t");
                System.out.print(row.getMem_pass() + "\t");
                System.out.print(row.getMem_name() + "\t");
                System.out.print(row.getMem_tel() + "\t");
                System.out.print(row.getMem_addr() + "\t");
                System.out.println();
            }
        }
        System.out.println("출력을 완료했습니다.");
        System.out.println("---------------------------------------------------");
    }

    /**
     * 회원정보를 입력받아 기존회원정보를 업데이트 시킨다.
     */
    private void updateInfoAll() {
        System.out.println("3. 자료 수정 입니다.");

        String inMemId = ""; // 사용자로부터 입력받은 아이디

        // 사용자로부터 아이디를 입력받아 DB조회 후 일치 여부 검사
        while(true){
            System.out.println("수정 할 아이디를 입력하세요 > ");
            inMemId = scan.nextLine();

            int idChkResult = service.getMemberCount(inMemId);

            if(idChkResult > 0) {
                System.out.println("계정 확인이 완료됐습니다.");
                break;
            } else {
                System.out.println("입력하신 아이디와 암호를 다시 확인 해주세요.");
            }
        }

        System.out.println("수정하실 정보를 입력해 주세요.(아이디 수정 불가)");
        System.out.println("새로운 비밀번호를 입력하세요 > ");
        String changePass = scan.nextLine();
        System.out.println("새로운 이름을 입력하세요 > ");
        String changeName = scan.nextLine();
        System.out.println("새로운 전화번호를 입력하세요 > ");
        String changeTel = scan.nextLine();
        System.out.println("새로운 주소를 입력하세요 > ");
        String changeAddr = scan.nextLine();

        // MemberVo객체에 입력받은 정보를 세팅한다.
        MemberVo memVo = new MemberVo();
        memVo.setMem_id(inMemId);
        memVo.setMem_pass(changePass);
        memVo.setMem_name(changeName);
        memVo.setMem_tel(changeTel);
        memVo.setMem_addr(changeAddr);

        // Service의 updateMember()를 호출하여 처리한다.
        int updateResult = service.updateMember(memVo);

        if(updateResult > 0) {
            System.out.println("정보 수정이 완료됐습니다.");
        } else {
            System.out.println("정보 수정을 완료하지 못했습니다.");
        }
    }

    /**
     * 삭제할 ID를 입력받아 ID존재 여부 체크 후
     * 존재 할 시 삭제 처리, 미존재시 메뉴 화면으로 복귀
     */
    private void deleteMember() {
        System.out.println("2. 자료 삭제 입니다.");

        String inMemId = ""; // 사용자로부터 입력받은 아이디

        // 사용자로부터 아이디와 패스워드를 입력받아 DB조회 후 일치 여부 검사
        while(true){
            // 아이디와 패스워드 입력 받기
            System.out.println("삭제 할 아이디를 입력하세요 > ");
            inMemId = scan.nextLine();

            int idChkResult = service.getMemberCount(inMemId);

            if(idChkResult > 0) {
                // 아이디와 암호가 일치함
                System.out.println("계정 확인이 완료됐습니다.");
                break;
            } else {
                // 아이디와 암호가 일치하지 않음
                System.out.println("입력하신 아이디와 암호를 다시 확인 해주세요.");
            }
        }

        int delResult = service.deleteMember(inMemId);

        if( delResult > 0 ){
            // 삭제 완료된 상태
            System.out.println(inMemId + " 삭제가 완료되었습니다.");
        } else {
            // 삭제처리 안된 상태
            System.out.println("삭제 처리가 완료되지 않았습니다.");
        }
    }

    /**
     * 메인 메뉴를 출력하고 사용자가 선택하도록 메뉴선택입력을 받는다.
     * @return 메뉴번호에 해당하는 int형 값을 반환 받는다
     */
    public int displayMenu() {
        // 메뉴 출력
        System.out.println("---------------------------------------------------");
        System.out.println("1. 자료 추가");
        System.out.println("2. 자료 삭제");
        System.out.println("3. 자료 수정");
        System.out.println("4. 전체 자료 출력");
        System.out.println("5. 개별 자료 수정");
        System.out.println("0. 작업 종료");
        System.out.println("---------------------------------------------------");

        // 메뉴 입력 받기
        int menuNo = scan.nextInt();
        scan.nextLine(); // 버퍼 비우기
        return menuNo;
    }

    /**
     * 1. 자료 추가 - 회원정보 추가 메서드
     * 회원정보를 입력받고 DB에 저장 시킨다.
     */
    private void insertMember() {
        System.out.println("1. 자료 추가 입니다.");

        String inputId = ""; // 사용자입력 ID

        // 중복이 아닌 아이디를 입력할 때 까지 반족
        while (true){
            // 회원 아이디 중복값 존재 확인
            System.out.println("아이디를 입력 하세요 > ");
            inputId = scan.nextLine();

            int idCheckResult = service.getMemberCount(inputId);
            if(idCheckResult > 0){ // 가능
                System.out.println("이미 존재하는 아이디 입니다.");
                System.out.println("다시 입력해주세요.");
            } else {
                System.out.println("사용 가능한 ID입니다.");
                break;
            }
        }

        // 회원 정보 DB 삽입
        // 나머지 정보 입력 받기
        System.out.println("비밀번호를 입력하세요 > ");
        String inputPassword = scan.nextLine();
        System.out.println("성명을 입력하세요 > ");
        String inputName = scan.nextLine();
        System.out.println("전화번호를 입력하세요 > ");
        String inputTel = scan.nextLine();
        System.out.println("주소를 입력하세요 > ");
        String inputAddr = scan.nextLine();

        // 입력이 완료되면 입력한 자료들을 VO객체에 저장한다.
        MemberVo memberVo = new MemberVo();
        memberVo.setMem_id(inputId);
        memberVo.setMem_pass(inputPassword);
        memberVo.setMem_name(inputName);
        memberVo.setMem_tel(inputTel);
        memberVo.setMem_addr(inputAddr);

        // service의 insert메서드를 호출해서 처리한다.
        int insertResult = service.insertMember(memberVo);

        if(insertResult > 0) {
            System.out.println("성공적으로 입력되었습니다.");
        } else {
            System.out.println("오류가 발생 했습니다.");
        }
    }
}
