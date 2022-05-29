package ltw.groupjava.app.controller;

import lombok.RequiredArgsConstructor;
import ltw.groupjava.app.entity.Cart;
import ltw.groupjava.app.entity.CartItem;
import ltw.groupjava.app.entity.Product;
import ltw.groupjava.app.entity.User;
import ltw.groupjava.app.service.CartItemService;
import ltw.groupjava.app.service.CartService;
import ltw.groupjava.app.service.ProductService;
import ltw.groupjava.app.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class CartController {
    private final ProductService productService;
    private final CartService cartService;
    private final UserService userService;
    private final CartItemService cartItemService;
    @GetMapping("/cart")
    private String showCart(Model model) {
        User loggedUser = getLoggedUser();
        Cart userCart = cartService.findByOwner(loggedUser);
        model.addAttribute("cartItems", cartItemService.findByCart(userCart));

        return "cart";
    }

    @GetMapping("/addToCart")
    private String addToCart(@RequestParam("productID") String productID, Model model) {
        UUID productUUID = UUID.fromString(productID);

        User loggedUser = getLoggedUser();
        Cart cart = cartService.findByOwner(loggedUser);
        Product product = productService.findById(productUUID);
        CartItem cartItem = cartItemService.findByCartAndProduct(cart, product);
        if (cartItem == null) {
            cartItem = new CartItem();
            cartItem.setQuantity(1);
        } else {
            cartItem.setQuantity(cartItem.getQuantity() + 1);
        }
        cartItem.setProduct(product);
        cartItem.setCart(cart);

        cartItemService.save(cartItem);
        model.addAttribute("cartItems", cartItemService.findByCart(cart));

        return "cart";
    }

    @PostMapping("/updateQuantity")
    private String updateQuantity(@RequestParam("cartItemID") String cartItemID, @RequestParam("cartItemQuantity") String cartItemQuantity, Model model) {
        CartItem currentCartItem = cartItemService.findByID(cartItemID);
        currentCartItem.setQuantity(Integer.parseInt(cartItemQuantity));

        if (currentCartItem.getQuantity() > 0) {
            cartItemService.save(currentCartItem);
        }
        else {
            cartItemService.delete(currentCartItem);
        }

        model.addAttribute("cartItems", cartItemService.findByCart(currentCartItem.getCart()));

        return "cart";
    }

    @GetMapping("/buy")
    private String buy(Model model) {
        User loggedUser = getLoggedUser();
        Cart userCart = cartService.findByOwner(loggedUser);
        List<CartItem> currentCartItems = cartItemService.findByCart(userCart);

        double totalPrice = 0;
        LocalDateTime orderTime = LocalDateTime.now();

        for (CartItem i : currentCartItems) {
            i.setStatus(1);
            i.setOrderTime(orderTime);

            totalPrice = totalPrice + (i.getQuantity() * i.getProduct().getPrice());

            Integer currentRemaining = i.getProduct().getRemaining();
            i.getProduct().setRemaining(currentRemaining - i.getQuantity());
            productService.save(i.getProduct());
        }

        model.addAttribute("cartItems", cartItemService.findByCart(userCart));
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("orderTime", orderTime);

        return "bought";
    }

    private User getLoggedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String loggedUser = auth.getName();
        return userService.findByUsername(loggedUser);
    }

}