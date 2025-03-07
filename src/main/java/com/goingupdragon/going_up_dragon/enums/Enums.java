package com.goingupdragon.going_up_dragon.enums;

public class Enums {
    public enum SignupType {
        normal, naver, google
    }

    public enum UserStatus {
        active, inActive
    }

    public enum Role {
        student, instructor
    }

    public enum CourseLevel {
        입문, 중급, 고급, 모두
    }

    public enum CourseLanguage {
        한국어, 영어, 일본어, 모두
    }

    public enum EnrollmentStatus {
        대기, 장바구니, 수강중, 환불요청중, 환불완료
    }
}
