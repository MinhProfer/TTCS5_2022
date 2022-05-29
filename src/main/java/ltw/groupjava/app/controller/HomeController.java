package ltw.groupjava.app.controller;

import lombok.RequiredArgsConstructor;
import ltw.groupjava.app.entity.Role;
import ltw.groupjava.app.service.ProductService;
import ltw.groupjava.app.service.RoleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;

@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class HomeController {
    private final RoleService roleService;
    private final ProductService productService;

    @GetMapping
    public String index() {
        return "redirect:/products";
    }

    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("products", productService.findAll());

        return "redirect:/products";
    }

    @GetMapping("/insert-role")
    @ResponseBody
    public String insertRole() {
        Role userRole = new Role();
        userRole.setRoleName("USER");
        Role admin = new Role();
        admin.setRoleName("ADMIN");
        roleService.saveAll(Arrays.asList(userRole, admin));
        return "inserted";
    }
}
