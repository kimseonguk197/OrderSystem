//package com.example;
//
//import com.example.domain.*;
//import com.example.domain.item.Book;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.annotation.PostConstruct;
//import javax.persistence.EntityManager;
//
//@Component
//@RequiredArgsConstructor
//public class InitDb {
//
//    private final InitService initService;
//
//    @PostConstruct
//    public void init() {
//        initService.dbInit1();
//        initService.dbInit2();
//    }
//
//
//    @Component
//    @Transactional
//    @RequiredArgsConstructor
//    static class InitService {
//
//        private final EntityManager em;
//
//        public void dbInit1() {
//            Member member = createMember("UserA", "서울", "1", "1111");
//            em.persist(member);
//
//            Book book1 = createBook("JPA1 BOOK", 10000);
//            em.persist(book1);
//
//            Book book2 = createBook("JPA2 BOOK", 20000);
//            em.persist(book2);
//
//            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 10000, 1);
//            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 20000, 2);
//
//            Delivery delivery = createDelivery(member);
//            Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);
//            em.persist(order);
//        }
//
//        public void dbInit2() {
//            Member member = createMember("UserB","서울22", "2", "2222");
//            em.persist(member);
//
//            Book book1 = createBook("SPRING1 BOOK", 10000);
//            em.persist(book1);
//
//            Book book2 = createBook("SPRING2 BOOK", 20000);
//            em.persist(book2);
//
//            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 10000, 1);
//            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 20000, 2);
//
//            Delivery delivery = createDelivery(member);
//            Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);
//            em.persist(order);
//        }
//    }
//
//    private static Delivery createDelivery(Member member) {
//        Delivery delivery = new Delivery();
//        delivery.setAddress(member.getAddress());
//        return delivery;
//    }
//
//    private static Member createMember(String name, String city, String street, String zipcode) {
//        Member member = new Member();
//        member.setName(name);
//        member.setAddress(new Address(city, street, zipcode));
//        return member;
//    }
//
//    private static Book createBook(String price, int stockQuantity) {
//        Book book1 = new Book();
//        book1.setName(price);
//        book1.setPrice(stockQuantity);
//        book1.setStockQuantity(100);
//        return book1;
//    }
//}
