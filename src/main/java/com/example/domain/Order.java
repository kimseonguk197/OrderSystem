package com.example.domain;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "orders")
//기본생성자만 생성. 그러나, AccessLevel.PROTECTED 를 통해 상속의 경우가 아니면 기본생성자 사용을 못하도록 막는다.
//이를 통해 기본생성자만을 만들어 값을 set하는 과정에서 발생하는 필수적인 필드의 누락을 방지한다.
//여기 코드에서는 다른 곳에서의 new Order생성을 원천적으로 차단하고, Order도메인 내부에 CreateOrder를 만들어 두었다.
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {
    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
//    name = "member_id" 을 통해 사용하고자 하는 컬럼명 지정
//    JoinColumn은 생략한다 하더라도, ManyToOne에서 참조하는 테이블의 "테이블_id"가 자동으로 id로 설정된다.
//    추가적으로 fk가 자동으로 걸리는데, 이를 제외하고 싶다면,, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT) 세팅필요
    @JoinColumn(name = "member_id")
    private Member member;

//    여기서 말하는 cascade = CascadeType.ALL은 무엇인가?
//    원래대로라면, order 객체를 사용하여 orderItems를 저장하려면
//    persist(item1) persist(item2) persist(item3)등을 각각 해주고 그다음에 persist(order)를 해줘야 하지만,
//    cascade를 쓸 경우엔 컬렉션에 item1,2,3을 담아두기만 하면 cascade는 persist를 전파하는 역할을 하게 된다.
//    delete할때도 마찬가지로 지우게 되는데, 여기서 말하는건 mysql의 제약조건이 아니라, order객체를 통해 여러 orderItems를 지운다는 의미.
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate;    // 주문 시간

    @Enumerated(EnumType.STRING)
    private OrderStatus status;         // 주문 상태 [ORDER, CANCEL]

//    연관관계 메서드의 경우 원래 대로라면 아래와 같이 코딩을 해줘야 한다.
//    public static void main(String[] args) {
//        Member member = new Member();
//        Order order = new Order();
//        member.getOrders().add(order);
//        order.setMember(member);
//    }
//    이 편의메서드와 cascade의 persist와 엮여 order만 save하면 연관관계에 있는 테이블들에도 save가 됨
    //==연관관계 메서드==//
    public void setMember(Member member) {
        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    //==생성 메서드==//
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems) {
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        for (OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }
        order.setStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());
        return order;
    }

    //==비즈니스 로직==//
    /**
     * 주문 취소
     */
    public void cancel() {
        if (delivery.getStatus() == DeliveryStatus.COMP)
            throw new IllegalStateException("이미 배송완료된 상품은 취소가 불가능합니다.");

        this.setStatus(OrderStatus.CANCEL);
        for (OrderItem orderItem: orderItems) {
            orderItem.cancel();
        }
    }

    //==조회 로직==//

    /**
     * 전체 주문 가격 조회
     * @return
     */
    public int getTotalPrice() {
        int totalPrice = 0;
        for (OrderItem orderItem : orderItems) totalPrice += orderItem.getTotalPrice();
        return totalPrice;
    }
}
