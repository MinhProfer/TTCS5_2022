package ltw.groupjava.app.controller;

import lombok.RequiredArgsConstructor;
import ltw.groupjava.app.entity.AppResource;
import ltw.groupjava.app.entity.Product;
import ltw.groupjava.app.entity.ProductType;
import ltw.groupjava.app.entity.User;
import ltw.groupjava.app.entity.dto.ProductDto;
import ltw.groupjava.app.service.AppResourceService;
import ltw.groupjava.app.service.ProductService;
import ltw.groupjava.app.service.StorageService;
import ltw.groupjava.app.service.UserService;
import ltw.groupjava.app.utils.UuidUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final UserService userService;
    private final StorageService storageService;
    private final AppResourceService resourceService;

    @GetMapping("")
    public String displayAllProducts(
            @RequestParam("type") Optional<String> type,
            @RequestParam("sort") Optional<String> sort, Model model) {
        List<Product> products;
        String typeSelected = "";
        String sortCondition = sort.map(String::trim).orElse("");
        if (type.isPresent() && ProductType.fromId(type.get()) != ProductType.OTHER) {
            products = productService.findByType(ProductType.fromId(type.get()), sortCondition);
            typeSelected = type.get();
        } else {
            products = productService.findAll(sortCondition);
        }
        model.addAttribute("products", products);
        model.addAttribute("typeSelected", typeSelected);
        addProductTypeToPage(model);
        return "products";
    }

    @GetMapping("/{productId}")
    public String showProductDetails(@PathVariable("productId") String productId, Model model) {
        UUID id = UuidUtils.parse(productId);
        Product product = null;
        if (id != null) {
            product = productService.findById(id);
        }
        if (product == null) {
            return "redirect:/products";
        }
        model.addAttribute("product", product);
        return "productDetails";
    }

    @GetMapping("/add")
    public String getAddProductPage(Model model) {
        model.addAttribute("productDto", new ProductDto());
        addProductTypeToPage(model);
        return "addProduct";
    }
    private User getLoggedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String loggedUser = auth.getName();
        return userService.findByUsername(loggedUser);
    }
    private String fileHandle(MultipartFile file) {
        String imgId = null;
        if (!file.isEmpty()) {
            User uploader = getLoggedUser();
            LocalDateTime now = LocalDateTime.now();
            Path path = storageService.getPath(now);

            // save info to db
            AppResource appResource = resourceService.init(uploader, file, path.toString(), now);
            appResource = resourceService.save(appResource);
            storageService.saveFile(appResource.getId().toString().toLowerCase(), file, path);
            imgId = appResource.getId().toString();
        }
        return imgId;
    }

    @PostMapping("/add")
    public String saveProducts(
            @RequestParam("file") MultipartFile file,
            @ModelAttribute("productDto") ProductDto productDto,
            Model model) {
        String imgId = fileHandle(file);
        if (imgId != null) {
            productDto.setImgId(imgId);
        }
        int resultCode = productService.validate(productDto);
        if (resultCode == ProductService.VALID_INPUT) {
            productService.saveDto(productDto);
            return "redirect:/products";
        } else {
            model.addAttribute("productDto", productDto);
            return "redirect:/products/add";
        }
    }

    @GetMapping("/update")
    public String getUpdatePage(@RequestParam("id") UUID productId, Model model) {
        Product product = productService.findById(productId);
        if (product == null) {
            return "redirect:/add";
        }
        model.addAttribute("productDto", productService.toDto(product));
        addProductTypeToPage(model);
        return "addProduct";
    }

    private void addProductTypeToPage(Model model) {
        model.addAttribute("types", ProductType.values());
    }
}
