<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!-- Vendor JS Files -->
<script src="/assets/vendor/aos/aos.js"></script>
<script src="/assets/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
<script src="/assets/vendor/glightbox/js/glightbox.min.js"></script>
<script src="/assets/vendor/isotope-layout/isotope.pkgd.min.js"></script>
<script src="/assets/vendor/php-email-form/validate.js"></script>
<script src="/assets/vendor/purecounter/purecounter.js"></script>
<script src="/assets/vendor/swiper/swiper-bundle.min.js"></script>
<script src="/assets/vendor/typed.js/typed.min.js"></script>
<script src="/assets/vendor/waypoints/noframework.waypoints.js"></script>

<!-- Template Main JS File -->
<script src="/assets/js/main.js"></script>

<!-- jquery (cdn 사용) -->
<script src="https://code.jquery.com/jquery-3.6.0.js"></script>

<script type="text/javascript">

    // 학번에 숫자 형태만 입력되도록 함수 작성(numberOnly=true 속성)
    $(document).on("keyup", "input:text[numberOnly]", function() {
        $(this).val( $(this).val().replace(/[^0-9]/gi,"") );
    });

    var emchk = document.getElementById("emchk");
    var email = document.getElementById("email");

    //이메일 형식 저장
    // var emailJ = /^[A-Za-z0-9_\.\-]+@[A-Za-z0-9\-]+\.[A-Za-z0-9\-]+/;
    var emailJ = /^[0-9_\.\-]+@[A-Za-z0-9\-]+\.[A-Za-z0-9\-]+/;

    function emailChk() {
        if (emailJ.test($(email).val()) == false) {
            $(emchk).text("올바른 형식으로 이메일을 입력해 주세요");
            $(emchk).css('color', 'red');
        } else {
            $(emchk).hide();
        }
    }

</script>