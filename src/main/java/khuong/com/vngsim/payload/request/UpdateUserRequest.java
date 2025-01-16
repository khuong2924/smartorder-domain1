package khuong.com.vngsim.payload.request;


import lombok.Data;

@Data
public class UpdateUserRequest {
    private String fullName;
    private String phone;
    private String address;
    private String gender;
}