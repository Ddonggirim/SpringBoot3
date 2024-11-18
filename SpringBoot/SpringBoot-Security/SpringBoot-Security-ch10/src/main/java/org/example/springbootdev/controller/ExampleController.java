package org.example.springbootdev.controller;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.List;

@Controller
public class ExampleController {
    @GetMapping("/thymeleaf/example")
    public String thymeleafEx(Model model){
       Person p1 = new Person();
       p1.setId(1L);
       p1.setName("홍길동");
       p1.setAge(18);
       p1.setHobbies(List.of("독서","수영","승마"));
       model.addAttribute("person", p1);
       model.addAttribute("today", LocalDate.now());
       return "example";
    }
}
@Getter
@Setter
class Person{
    private Long id;
    private String name;
    private int age;
    private List<String> hobbies;
}
