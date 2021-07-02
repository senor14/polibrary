package poly.userservice.vo;

import lombok.Data;

import javax.validation.constraints.Size;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;


@Data
public class RequestUser {
    @NotNull(message = "Email cannot be null")
    @Size(min = 2, max = 64, message = "Email not be less than two character")
    @Email
    private String email;

    @NotNull(message = "Name cannot be null")
    @Size(max = 10, message = "Name not be less than two characters")
    private String name;

    @NotNull(message = "Student Id cannot be null")
    @Size(min = 10, message = "Student Id must be equal 8 characters")
    private String studentId;

    @NotNull(message = "Password cannot be null")
    @Size(min = 8, max = 64, message = "Password must be equal or grater than 8 characters")
    private String pwd;


    @NotNull(message = "Department cannot be null")
    @Size(message = "Department must be equal or less than 20 characters")
    private String department;

    private String nickname;
    

}
