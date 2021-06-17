package poly.frontservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import poly.frontservice.dto.ViewDTO;
import poly.frontservice.util.CmmUtil;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Controller
//@CrossOrigin(allowedHeaders = "*", origins = "*")
@RequestMapping("/")
public class MainController {

    @GetMapping("/")
    public String home() {
        return "/login";
    }

    @GetMapping("/index")
    public String Index() {
        return "/user/login";
    }

    // 로그인 jsp
    @GetMapping("/login")
    public String login() {
        return "/user/login";
    }

    // 회원가입 jsp
    @GetMapping("/user/signup")
    public String signUp() {
        return "/user/signup";
    }

    // 회원가입 인증번호 jsp
    @GetMapping("/user/signupcheck")
    public String signUpCheck() {
        return "/user/signupcheck";
    }

    // 회원 찾기 jsp
    @GetMapping("/user/finduser")
    public String findUser() {
        return "/user/finduser";
    }

    // 인증번호 입력 jsp
    @GetMapping("/user/pwcheck")
    public String pwCheck() {
        return "/user/pwcheck";
    }

    // 비밀번호 변경 jsp
    @GetMapping("/user/chgpw")
    public String chgPw() {
        return "/user/chgpw";
    }

    // 내정보 변경 jsp
    @GetMapping("/user/chginf")
    public String chgInf() {
        return "/user/chgInf";
    }

    //메인페이지
    @GetMapping("/main")
    public String main() {
        return "/main/main";
    }

    // 책 검색
    @RequestMapping(value = "/book/bookSearch")
    public String bookSearch() {
        return "/book/bookSearch";
    }

    // 내 대여 목록
    @GetMapping(value = "/rental/rentalList")
    public String rentalList(){return "/rental/rentalList"; }

    // 대여 희망 게시판
    @GetMapping(value = "/board/boardList")
    public String boardList(){return "/board/boardList"; }

    // 자신의 신청 기록
    @GetMapping(value = "/board/myList")
    public String myList(){return "/board/myList"; }

    // 내 배송 기록
    @GetMapping(value = "/board/myDelivery")
    public String myDelivery(){return "/board/myDelivery"; }

    // 관리자 홈
    @GetMapping(value = "/admin/adminhome")
    public String adminHome() {
        return "/admin/adminhome";
    }

    // 관리자 유저 수정
    @GetMapping(value = "/admin/edituser")
    public String editUser(HttpServletRequest request, ModelMap model) {
        log.info(this.getClass().getName() + "editUser. Start!");

        String uid = CmmUtil.nvl(request.getParameter("uid"));

        log.info("uid : " + uid);

        ViewDTO rDTO = new ViewDTO();

        rDTO.setUid(uid);

        model.addAttribute("rDTO", rDTO);

        log.info(this.getClass().getName() + "editUser. END!");
        return "/admin/edituser";
    }

    // 채팅방 목록
    @GetMapping("/chat/room")
    public String chatRoom() {
        return "/chat/room";
    }

    // 채팅방 상세
    @GetMapping("/room/{roomId}")
    public String roomDetail(Model model, @PathVariable String roomId) {
        model.addAttribute("roomId", roomId);
        return "/chat/roomdetail";
    }


}
