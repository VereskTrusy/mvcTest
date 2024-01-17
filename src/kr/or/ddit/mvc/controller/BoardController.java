package kr.or.ddit.mvc.controller;

import kr.or.ddit.mvc.service.BoardServiceImpl;
import kr.or.ddit.mvc.service.IBoardService;
import kr.or.ddit.mvc.vo.BoardVo;

import java.util.List;
import java.util.Scanner;

public class BoardController {

    private IBoardService service;
    private Scanner scanner;
    private boolean menuViewFlag = true;

    private BoardController(){
         scanner = new Scanner(System.in);
         service = BoardServiceImpl.getInstance();
    }

    public static void main(String[] args) {
        new BoardController().start();
    }

    private void start(){
        while (true) {

            // 메뉴 출력 및 선택
            int menu = displayMenu();

            switch (menu) {
                case 1 :
                    insertBoard();
                    break;
                case 2 :
                    viewBoard();
                    break;
                case 3 :
                    // 검색
                    selectSearchBoard();
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
     * 게시글 검색하기
     *
     */
    private void selectSearchBoard() {
        menuViewFlag = false;
        System.out.println("---------------------------------------------------");
        System.out.println("- 검색할 제목 입력 : ");
        String searchStr = scanner.nextLine();
        List<BoardVo> list = service.selectSearchBoard(searchStr);
        if(list.isEmpty() || null == list) {
            System.out.println("조회된 내용이 없습니다.");
            return;
        }
        viewBoardList(list);

    }

    /**
     * 2.게시글보기
     */
    private void viewBoard() {
        System.out.print("보기를 원하는 게시물 번호 입력 >> ");
        int boardNo = scanner.nextInt();
        scanner.nextLine(); // 버퍼 비우기

        int bCnt = service.updateBoardCnt(boardNo);
        if(bCnt <= 0) {
            System.out.println("게시글 조회수 수정에 실패 했습니다. 메뉴로 돌아갑니다.");
            return;
        }

        BoardVo item = service.selectBoard(boardNo);

        if(null == item) {
            System.out.println("해당 글번호로는 조회된 내용이 없습니다.");
            return;
        }

        System.out.println("---------------------------------------------------");
        System.out.println("- 제목 : " + item.getBoard_title());
        System.out.println("- 작성자 : " + item.getBoard_writer());
        System.out.println("- 내용 : " + item.getBoard_content());
        System.out.println("- 작성일 : " + item.getBoard_date());
        System.out.println("- 조회수 : " + item.getBoard_cnt());
        System.out.println("---------------------------------------------------");

        System.out.println("메뉴 : 1.수정\t2.삭제\t3.리스트로가기");
        int subMenu = scanner.nextInt();
        scanner.nextLine(); // 버퍼 비우기

        switch (subMenu) {
            case 1 :
                // 수정
                updateBoard(boardNo);
                break;
            case 2 :
                // 삭제
                deleteBoard(boardNo);
                break;
            case 3 :
                // 리스트로 돌아가기
                System.out.println("메인메뉴로 돌아 갑니다.");
                break;
            default:
                System.out.println("잘못된 입력입니다. 메인메뉴로 돌아 갑니다.");
                break;
        }
    }

    /**
     * 게시글 삭제하기
     * @param boardNo
     */
    private void deleteBoard(int boardNo) {
        int deleteResult = service.deleteBoard(boardNo);
        if(deleteResult > 0) System.out.println( boardNo + "번글이 삭제되었습니다.");
        else System.out.println("글 삭제에 실패 했습니다.");
    }

    /**
     * 게시글 수정
     */
    private void updateBoard(int boardNo) {
        System.out.println("---------------------------------------------------");
        System.out.print("- 제목 : ");
        String title = scanner.nextLine();
        System.out.print("- 내용 : ");
        String content = scanner.nextLine();

        // BoardVo에  합치기
        BoardVo boardVo = new BoardVo();
        boardVo.setBoard_no(boardNo);
        boardVo.setBoard_title(title);
        boardVo.setBoard_content(content);

        int updateResult = service.updateBoard(boardVo);

        if(updateResult > 0 ) System.out.println(boardNo + "번글이 수정되었습니다.");
        else System.out.println("글 수정에 실패 했습니다.");
    }
    /**
     * 1.새글작성 하기
     */
    private void insertBoard() {
        System.out.println("새글 작성하기");
        System.out.println("---------------------------------------------------");
        System.out.print("- 제목 : ");
        String title = scanner.nextLine();
        System.out.print("- 작성자 : ");
        String writer = scanner.nextLine();
        System.out.print("- 내용 : ");
        String content = scanner.nextLine();

        // BoardVo에 내용 합치기
        BoardVo boardVo = new BoardVo();
        boardVo.setBoard_title(title);
        boardVo.setBoard_writer(writer);
        boardVo.setBoard_content(content);

        int cnt = service.insertBoard(boardVo);
        if(cnt > 0) System.out.println("새글이 추가되었습니다.");
        else System.out.println("글 등록에 실패 했습니다.");
    }


    /**
     * 메인 메뉴를 출력하고 사용자가 선택하도록 메뉴선택입력을 받는다.
     * @return 메뉴번호에 해당하는 int형 값을 반환 받는다
     */
    public int displayMenu() {
        if(menuViewFlag){
            List<BoardVo> list = service.selectBoardAllList(); // 리스트 가져오기

            viewBoardList(list); // 리스트 출력
        }

        System.out.println("메뉴 : 1.새글작성\t2.게시글보기\t3.검색\t0.작업끝");

        // 메뉴 입력 받기
        System.out.print("작업 선택 >> ");
        int menuNo = scanner.nextInt();
        scanner.nextLine(); // 버퍼 비우기
        menuViewFlag = true;
        return menuNo;
    }

    public void viewBoardList(List<BoardVo> list){
        // 메뉴 출력
        System.out.println("---------------------------------------------------");
        System.out.println("NO\t\t제목\t\t\t작성자\t\t조회수");
        for(BoardVo item :list) {
            System.out.println();
            System.out.print(item.getBoard_no() + "\t\t");
            System.out.print(item.getBoard_title() + "\t\t");
            System.out.print(item.getBoard_writer() + "\t\t");
            System.out.print(item.getBoard_cnt());
            System.out.println();
        }
        System.out.println("---------------------------------------------------");
    }


}
