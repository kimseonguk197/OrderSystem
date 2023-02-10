package com.example.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

//    @NotEmpty 는 null 과 "" 둘 다 허용하지 않는다. @NotNull은 Null만 허용하지 않음.
//    특이한건 DB에 @NotNull의 경우 not null 제약조건을 추가하지만, NotEmpty는 추가하지 않음
    @NotEmpty
    private String name;

//    Embedded 어노테이션을 통해 Address객체를 한번에 표현. DB에는 각각 컬럼이 만들어짐을 알수 있따.
    @Embedded
    private Address address;

//    mappedBy는 연관관계의 주인을 정하는 것 : join된 테이블 중 fk의 값을 누가 변경할 수 있냐는 것
//    mappedBy = "member" 이것은 연관관계의 주인이 fk가 설정돼 있는 Order에 있다는 것을 의미
    @JsonIgnore
    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();
}
