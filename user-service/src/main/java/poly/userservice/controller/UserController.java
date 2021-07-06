package poly.userservice.controller;

import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.convention.NameTokenizers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import poly.userservice.dto.MailDto;
import poly.userservice.dto.UserDto;
import poly.userservice.jpa.UserEntity;
import poly.userservice.jpa.UserRepository;
import poly.userservice.service.MailService;
import poly.userservice.service.UserService;
import poly.userservice.util.CmmUtil;
import poly.userservice.util.EncryptUtil;
import poly.userservice.vo.*;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
//@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/user-service")
public class UserController {

    private final Environment env;
    private final UserService userService;
    private final UserRepository userRepository;
    private final MailService mailService;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/hello")
    public String Hello() {
        return "Hello";
    }

    @GetMapping("/health_check")
    @Timed(value="users.status", longTask = true)
    public String status() {
        return String.format("It's Working in User Service"
                + ", port(local.server.port)=" + env.getProperty("local.server.port")
                + ", port(server.port)=" + env.getProperty("server.port")
                + ", token secret=" + env.getProperty("token.secret")
                + ", token expiration time=" + env.getProperty("token.expiration_time"));
    }

    // 회원 가입
    @PostMapping("/users")
    public ResponseEntity<ResponseUser> createUser(@RequestBody RequestUser user) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        UserDto userDto = mapper.map(user, UserDto.class);
        ResponseUser responseUser = new ResponseUser();
        String studentId = userDto.getStudentId();
        String email = userDto.getEmail();
//        boolean check = (email.substring(0,10).equals(studentId));

        log.info("email:"+email);
        log.info("stId:"+studentId);

//        if (!check) {
//            responseUser.setMsg("학번과 메일 앞자리를 확인해주세요.");
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseUser);
//        } else if (studentId.length() != 10) {
//            responseUser.setMsg("학번을 확인해주세요.");
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseUser);
//        } else if (!(email.substring(email.length()-10, email.length()).equals("kopo.ac.kr"))) {
//            responseUser.setMsg("kopo.ac.kr 이메일을 입력해주세요");
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseUser);
//        }
        userService.createUser(userDto);

        responseUser = mapper.map(userDto, ResponseUser.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseUser);
    }

    // 전체 사용자 회원 조회
    @GetMapping("/users")
    public ResponseEntity<List<ResponseUser>> getUsers() {
        Iterable<UserEntity> userList = userService.getUserByAll();

        List<ResponseUser> result = new ArrayList<>();

        userList.forEach(v -> {
            result.add(new ModelMapper().map(v, ResponseUser.class));
        });

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    // 특정 사용자 조회
    @GetMapping("/users/{userId}")
    public ResponseEntity<ResponseUser> getUser(@PathVariable("userId") String userUuid) {
        UserDto userDto = userService.getUserByUserUuid(userUuid);

        ResponseUser returnValue = modelMapper.map(userDto, ResponseUser.class);

//        ResponseUser returnValue = new ModelMapper().map(userDto, ResponseUser.class);

        return ResponseEntity.status(HttpStatus.OK).body(returnValue);
    }

    
    // 특정 사용자 회원 삭제
    @DeleteMapping("/users/{userId}")
    public ResponseEntity<ResponseUser> deleteUser(@PathVariable("userId") String userUuid) {

        ResponseUser returnValue = new ResponseUser();

        returnValue.setMsg("삭제 되었습니다");
        returnValue.setUrl("/redirect");

        userRepository.deleteByUserUuid(userUuid);

        return ResponseEntity.status(HttpStatus.OK).body(returnValue);
    }

    // 특정 사용자 회원 정보 수정
    @PutMapping("/users/{userId}")
    public ResponseEntity<ResponseUser> updateUser(@PathVariable("userId") String userUuid,
                                                   @RequestBody  RequestUser requestUser) {

        UserEntity userEntity = userRepository.findByUserUuid(userUuid);
        modelMapper.map(requestUser, userEntity);


        userRepository.save(userEntity);

        ResponseUser returnValue = modelMapper.map(userEntity, ResponseUser.class);

        return ResponseEntity.status(HttpStatus.OK).body(returnValue);
    }

    // 가입 학번 중복 체크
    @GetMapping("/users/studentId/{studentId}/check")
    public ResponseEntity<ResponseModel> existsById(@PathVariable("studentId") String studentId) {

        boolean existCheck = userRepository.existsByStudentId(studentId);

        ResponseModel returnValue = new ResponseModel();

        if (studentId.length() != 10) {
            returnValue.setMsg("잘못된 학번입니다.");
        } else if (existCheck) {
            returnValue.setMsg("이미 존재하는 학번입니다.");
        } else {
            returnValue.setMsg("사용 가능한 학번입니다.");
        }

        return ResponseEntity.status(HttpStatus.OK).body(returnValue);
    }

    // 가입 닉네임 중복 체크
    @GetMapping("/users/nickname/{nickname}/check")
    public ResponseEntity<ResponseModel> existsByNickname(@PathVariable("nickname") String nickname) {

        boolean existCheck = userRepository.existsByNickname(nickname);

        ResponseModel returnValue = new ResponseModel();

        if (existCheck) {
            returnValue.setMsg("이미 존재하는 닉네임입니다.");
        } else {
            returnValue.setMsg("사용 가능한 닉네임입니다.");
        }

        return ResponseEntity.status(HttpStatus.OK).body(returnValue);
    }

    // 가입 이메일 중복 체크
    @GetMapping("/users/email/{email}/check")
    public ResponseEntity<ResponseModel> existsByEmail(@PathVariable("email") String email) {

        boolean existCheck = userRepository.existsByEmail(email);

        ResponseModel returnValue = new ResponseModel();

        if (existCheck) {
            returnValue.setMsg("이미 존재하는 이메일입니다.");
        } else {
            returnValue.setMsg("사용 가능한 이메일입니다.");
        }

        return ResponseEntity.status(HttpStatus.OK).body(returnValue);
    }



    // 로그인
    @PostMapping(value = "/login")
    public ResponseEntity<ResponseModel> getLogin(@RequestBody RequestLogin requestLogin,
                                                  HttpSession session) throws Exception {
        log.info("getLogin Start!");

        String email = requestLogin.getEmail();
        String password = requestLogin.getPassword();

        log.info("String user_email : " + email);
        log.info("String password : " + password);

        RequestUser requestUser = new RequestUser();

        log.info("pDTO.set 시작");
        requestUser.setEmail(email);
        requestUser.setPwd(password);

        log.info("pDTO.email : " + requestUser.getEmail());
        log.info("pDTO.password : " + requestUser.getPwd());

        log.info("userService.getLogin 시작");
        UserEntity userEntity = userRepository.findByEmail(email);

        log.info("로그인 userEntity: {}", userEntity);
        log.info("rDTO null? " + (userEntity == null));

        String msg = "";
        String url = "";
        log.info("EncrytedPwd:"+userEntity.getEncryptedPwd());
        log.info("password:"+password);
        ResponseModel result = new ResponseModel();
        // 로그인에 실패한 경우
        if (userEntity == null || !passwordEncoder.matches(password, userEntity.getEncryptedPwd())) {
            msg = "로그인에 실패했습니다. 다시 시도해 주세요.";
            url = "/login"; //재 로그인
            result.setUrl(url);
            result.setMsg(msg);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        }
        // 로그인 성공한 경우(rDTO != null)
        else {

            log.info("rDTO.getEmail : " + userEntity.getEmail());
            log.info("rDTO.getMember_name: " + userEntity.getName());
            msg = "환영합니다!";

            // 회원 번호로 세션 올림, "ㅇㅇㅇ님, 환영합니다" 같은 문구 표시를 위해 user_name도 세션에 올림
            // 작성자와 현재 로그인한 사용자를 구분해주기 위해 닉네임도 세션에 올림
            session.setAttribute("SS_MEMBER_ID", userEntity.getUserUuid());
            session.setAttribute("SS_MEMBER_EMAIL", userEntity.getEmail());
            session.setAttribute("SS_MEMBER_NAME", userEntity.getName());
            session.setAttribute("SS_MEMBER_NIC", userEntity.getNickname());
            session.setAttribute("SS_MEMBER_DP", userEntity.getDepartment());
            session.setAttribute("SS_MEMBER_ST_ID", userEntity.getStudentId());

            result.setUserUuid(userEntity.getUserUuid());
            result.setEmail(userEntity.getEmail());
            result.setName(userEntity.getName());
            result.setNickname(userEntity.getNickname());
            result.setStudentId(userEntity.getStudentId());
            result.setDepartment(userEntity.getDepartment());


            url = "/main"; //로그인 성공 후 리턴할 페이지
        }

        result.setMsg(msg);
        result.setUrl(url);

        log.info("msg : " + msg);
        log.info("url : " + url);

        userEntity = null;
        log.info("getLogin end");

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    // 로그아웃
    @GetMapping("/logOut")
    public ResponseEntity<ResponseModel> logOut(HttpSession session) throws Exception {
        log.info("logOut Start!");

        String msg = "로그아웃 되었습니다.";
        String url = "/login";

        // 세션 삭제(user_name, user_no) - invalidate() 또는 removeAttribute 함수 사용
        session.removeAttribute("SS_MEMBER_ID");
        session.removeAttribute("SS_MEMBER_EMAIL");
        session.removeAttribute("SS_MEMBER_NAME");
        session.removeAttribute("SS_MEMBER_NIC");
        session.removeAttribute("SS_MEMBER_ST_ID");
        session.removeAttribute("SS_MEMBER_DP");

        // 세션이 정상적으로 삭제되었는지 로그를 통해 확인
        log.info("session deleted ? : " + session.getAttribute("SS_MEMBER_ID"));

        ResponseModel result = new ResponseModel();

        result.setMsg(msg);
        result.setUrl(url);

        log.info("session delete, model.addAttribute 끝!");
        log.info("logOut End!");

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    // 아이디 찾기
    @PostMapping("/users/findEmailByStId")
    public ResponseEntity<ResponseUser> findEmailByStId(@RequestBody RequestFindInfo requestStId ) {


//        log.info("studentId: {}",request.getParameter("studentId"));
        log.info("studentId: {}",requestStId.getStudentId());
//        UserEntity userEntity = userRepository.findByStudentId(request.getParameter("studentId"));
        UserEntity userEntity = userRepository.findByStudentId(requestStId.getStudentId());
        log.info("userEntity: {}", userEntity);
        ResponseUser responseUser = new ResponseUser();
        responseUser.setEmail(userEntity.getEmail());
        log.info("responseUser: {}", responseUser);
        return ResponseEntity.status(HttpStatus.OK).body(responseUser);
    }


    // 이메일 인증, 비밀번호 찾기 인증번호 보내기
    @PostMapping("/users/authByEmail")
    public ResponseEntity<ResponseEmail> authByEmail(@RequestBody RequestFindInfo requestEmail, HttpServletRequest request, ModelMap model, HttpSession session)
            throws InvalidAlgorithmParameterException, UnsupportedEncodingException, NoSuchPaddingException,
            IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {

        String email = requestEmail.getEmail();
        session.setAttribute("SS_EMAIL", email);
        log.info("email: {}",email);


        String auth = "";
        String msg = "";
        String url = "";


        // 랜덤한 8자 인증번호값 담기
        auth = UUID.randomUUID().toString().substring(0,8);

        // 인증번호
        log.info("인증번호 : " + auth);
        MailDto mDTO = new MailDto();

        ResponseEmail result = new ResponseEmail();
        try {
            // 이메일 보내기 위해 다시 암호화 디코딩
            log.info("이메일 : " + requestEmail);

            // 인증번호 메일 발송 로직
            mDTO.setToMail(requestEmail.getEmail());
            mDTO.setTitle("Polibrary 인증번호 이메일입니다.");
            mDTO.setContents("인증번호는 :  " + auth + "  입니다.");
            result = new ModelMapper().map(mDTO, ResponseEmail.class);
            // 최종 전송
            mailService.doSendMail(mDTO);

            msg = "이메일로 인증번호를 발송하였습니다.";
            url = "/user/pwcheck";

            result.setAuth(auth);
            result.setMsg(msg);
            result.setUrl(url);

            session.setAttribute("SS_AUTH", auth);

            // 변수와 메모리 초기화
            msg = "";
            url = "";
            mDTO = null;
            log.info("인증번호발사try: {}", session.getAttribute("SS_EMAIL").toString());
            log.info("인증번호 발송 END!!");

    //            return "user/find_pw_check";
            return ResponseEntity.status(HttpStatus.OK).body(result);

        } catch (Exception e) {
            msg = "실패하였습니다. : " + e.toString();
            url = "/";
            log.info(e.toString());
            e.printStackTrace();

            result.setMsg(msg);
            result.setUrl(url);

        } finally {
            // 변수와 메모리 초기화
            msg = "";
            url = "";
            mDTO = null;
        }

        log.info("인증번호발사: {}", session.getAttribute("SS_EMAIL").toString());
        log.info("인증번호 발송 END!!");

    //        return "/redirect";
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
    }

    // 비밀번호 변경 인증번호 검사
    @PostMapping("/users/pwCheck")
    public ResponseEntity<ResponseModel> pwCheck(@RequestBody RequestFindInfo requestAuth, HttpServletRequest request, ModelMap model,  HttpSession session){
        log.info("find_pw_result Start!");
        String clientAuth = CmmUtil.nvl(requestAuth.getClientAuth());
//        String auth = CmmUtil.nvl((String) session.getAttribute("SS_AUTH"));
        String auth = CmmUtil.nvl(requestAuth.getAuth());

        log.info("인증번호 검사clientAuth : " + clientAuth);
        log.info("인증번호 검사auth : " + auth);

        String msg = "";
        String url = "";

        // 이메일 인증번호와 사용자가 입력한 인증번호 비교
        if(clientAuth.equals(auth)){
            url = "/user/chgpw";
            msg = "인증에 성공하였습니다.";
        }
        else {
            url = "/login";
            msg = "인증번호가 틀립니다.";
        }

        ResponseModel result = new ResponseModel();
        result.setMsg(msg);
        result.setUrl(url);

        // 인증완료 후 세션 비워주기
//        session.removeAttribute("SS_AUTH");
//        log.info("session deleted ? : " + CmmUtil.nvl(session.getAttribute("SS_AUTH").toString()));
//
//        log.info("인증번호검사: {}", CmmUtil.nvl(session.getAttribute("SS_EMAIL").toString()));
//        return "redirect";
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    // 비밀번호 변경 로직 수행
    @PutMapping("/users/chgPw")
    public ResponseEntity<ResponseModel> changePassword(@RequestBody RequestLogin chgUserInfo, HttpSession session) throws Exception {


//        String email = CmmUtil.nvl((String) session.getAttribute("SS_EMAIL"));
        String email = chgUserInfo.getEmail();
        String userPw = CmmUtil.nvl(passwordEncoder.encode(chgUserInfo.getPassword()));

        log.info("email : " + email);
        log.info("userPw : " + userPw);

        String msg = "";
        String url = "";

        ResponseModel result = new ResponseModel();
        try {


            //where에 email을 사용, 수정할 비밀번호 전달
//            UserEntity userEntity = userRepository.findByEmail(session.getAttribute("SS_EMAIL").toString());
            log.info(email);
            UserEntity userEntity = userRepository.findByEmail(email);
//            log.info(userEntity.toString());
            userEntity.setEncryptedPwd(userPw);
            log.info("사이사이");
            userRepository.save(userEntity);
            log.info("오이오이");
            msg = "비밀번호가 변경되었습니다.";
            url = "/login";


            result.setUrl(url);
            result.setMsg(msg);
//            model.addAttribute("msg", msg);
//            model.addAttribute("url", url);

            // 변수와 메모리 초기화
            msg = "";
            url = "";

            log.info("비밀번호 변경 종료");

        } catch (Exception e) {
            msg = "실패하였습니다. : " + e.toString();
            url = "/login";


            log.info(e.toString());
            e.printStackTrace();
            result.setUrl(url);
            result.setMsg(msg);
//            model.addAttribute("msg", msg);
//            model.addAttribute("url", url);

        } finally {
            // 변수와 메모리 초기화
            msg = "";
            url = "";

        }

        // 세션 비워주기
        session.removeAttribute("SS_EMAIL");

        log.info("find_pw_change_update End!");
//        return "/redirect";
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    // 사용자 포인트 증가
    @PostMapping("/users/{userId}/point")
    public ResponseEntity<ResponseUser> pointUp(@PathVariable("userId") String userUuid) {
        UserEntity user = userRepository.findByUserUuid(userUuid);
        Integer current_point = user.getPoint();
        user.setPoint(current_point + 40);
        userRepository.save(user);

        ResponseUser returnValue = new ModelMapper().map(user, ResponseUser.class);

        return ResponseEntity.status(HttpStatus.OK).body(returnValue);
    }

    // 포인트 검색
    @GetMapping("/users/{userId}/point")
    public ResponseEntity<Integer> pointLoad(@PathVariable("userId") String userUuid) {
        UserEntity user = userRepository.findByUserUuid(userUuid);
        Integer current_point = user.getPoint();

        return ResponseEntity.status(HttpStatus.OK).body(current_point);
    }
}
