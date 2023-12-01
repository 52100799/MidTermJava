package com.example.gk.Controller;

import com.example.gk.DTO.CartDTOTotalPrice;
import com.example.gk.Model.Cart;
import com.example.gk.Model.Order;
import com.example.gk.Model.Product;
import com.example.gk.Service.CartService;
import com.example.gk.Service.OrderService;
import com.example.gk.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Controller
public class HomeController {
    private CartService cartService;
    private ProductService productService;
    private OrderService orderService;
    private final RestTemplate restTemplate;

    @Autowired
    public HomeController(RestTemplate restTemplate, CartService cartService, ProductService productService, OrderService orderService) {
        this.restTemplate = restTemplate;
        this.cartService = cartService;
        this.productService = productService;
        this.orderService = orderService;
    }

    @GetMapping
    public String getProduct(Model model) {
        String cartApiUrl = "http://localhost:8081/api/carts";
        String productApiUrl = "http://localhost:8081/api/products";

        ResponseEntity<List<Cart>> cartResponse = restTemplate.exchange(cartApiUrl, HttpMethod.GET, null, new ParameterizedTypeReference<List<Cart>>() {});
        ResponseEntity<List<Product>> productResponse = restTemplate.exchange(productApiUrl, HttpMethod.GET, null, new ParameterizedTypeReference<List<Product>>() {});

        if (cartResponse.getStatusCode().is2xxSuccessful() && productResponse.getStatusCode().is2xxSuccessful()) {
            List<Cart> cart = cartResponse.getBody();
            List<Product> products = productResponse.getBody();

            List<Object[]> productCounts = productService.countProductsByCategory();

            long countCart = cartService.countCarts();

            model.addAttribute("cart", cart);
            model.addAttribute("products", products);
            model.addAttribute("productCounts", productCounts);
            model.addAttribute("countCart", countCart);

            return "index";
        } else {
            return "error";
        }
    }

    @GetMapping("/cart")
    public String getCart(Model model) {
        String cartApiUrl = "http://localhost:8081/api/carts";

        ResponseEntity<List<Cart>> response = restTemplate.exchange(cartApiUrl, HttpMethod.GET, null, new ParameterizedTypeReference<List<Cart>>() {});
        if (response.getStatusCode().is2xxSuccessful()) {
            List<Cart> cart = response.getBody();

            List<CartDTOTotalPrice> cartDTOTotalPriceList = new ArrayList<>();
            double sumPrice = 0;
            for (Cart item : cart) {
                double totalPrice = item.getProduct().getPrice() * item.getQuantity();
                CartDTOTotalPrice cartDTOTotalPrice = new CartDTOTotalPrice();
                cartDTOTotalPrice.setCart(item);
                cartDTOTotalPrice.setTotalPrice(totalPrice);
                cartDTOTotalPriceList.add(cartDTOTotalPrice);
                sumPrice += totalPrice;
            }

            long countCart = cartService.countCarts();

            model.addAttribute("countCart", countCart);
            model.addAttribute("sumPrice", sumPrice);
            model.addAttribute("cart", cartDTOTotalPriceList);
            return "cart";
        } else {
            return "error";
        }
    }

    @GetMapping("/shop")
    public String getShop(Model model) {
        String productApiUrl = "http://localhost:8081/api/products";

        ResponseEntity<List<Product>> productResponse = restTemplate.exchange(productApiUrl, HttpMethod.GET, null, new ParameterizedTypeReference<List<Product>>() {});

        if (productResponse.getStatusCode().is2xxSuccessful()) {
            List<Product> products = productResponse.getBody();

            List<Object[]> productCounts = productService.countProductsByCategory();

            long countCart = cartService.countCarts();

            model.addAttribute("products", products);
            model.addAttribute("productCounts", productCounts);
            model.addAttribute("countCart", countCart);

            return "shop";
        } else {
            return "error";
        }
    }

    @GetMapping("/detail/{id}")
    public String getDetail(@PathVariable("id") Long id, Model model) {
        String productApiUrl = "http://localhost:8081/api/products/{id}";

        ResponseEntity<Product> productResponse = restTemplate.getForEntity(productApiUrl, Product.class, id);

        if (productResponse.getStatusCode().is2xxSuccessful()) {
            Product product = productResponse.getBody();
            long countCart = cartService.countCarts();

            model.addAttribute("product", product);
            model.addAttribute("countCart", countCart);

            return "detail";
        } else {
            return "error";
        }
    }

    @GetMapping("/checkout")
    public String getCheckout(Model model) {
        String cartApiUrl = "http://localhost:8081/api/carts";

        double sumPrice = 0;
        ResponseEntity<List<Cart>> response = restTemplate.exchange(cartApiUrl, HttpMethod.GET, null, new ParameterizedTypeReference<List<Cart>>() {});
        if (response.getStatusCode().is2xxSuccessful()) {
            List<Cart> cart = response.getBody();
            for (Cart item : cart) {
                double totalPrice = item.getProduct().getPrice() * item.getQuantity();
                sumPrice += totalPrice;
            }

            long countCart = cartService.countCarts();

            model.addAttribute("countCart", countCart);
            model.addAttribute("cart", cart);
            model.addAttribute("orderForm", new Order());
            model.addAttribute("sumPrice", sumPrice);
            return "checkout";
        } else {
            return "error";
        }
    }

    @GetMapping("/add-product/{productId}")
    public String addProductToCart(@PathVariable("productId") Long productId) {
        String cartApiUrl = "http://localhost:8081/api/carts/add-product/{productId}";

        ResponseEntity<Void> response = restTemplate.exchange(cartApiUrl, HttpMethod.POST, null, Void.class, productId);
        if (response.getStatusCode().is2xxSuccessful()) {
            return "redirect:/";
        } else {
            return "error";
        }
    }

    @GetMapping("/add/{productId}")
    public String addProductFromCart(@PathVariable("productId") Long productId) {
        String cartApiUrl = "http://localhost:8081/api/carts/add-product/{productId}";

        ResponseEntity<Void> response = restTemplate.exchange(cartApiUrl, HttpMethod.POST, null, Void.class, productId);
        if (response.getStatusCode().is2xxSuccessful()) {
            return "redirect:/cart";
        } else {
            return "error";
        }
    }

    @GetMapping("/sub/{productId}")
    public String subProductFromCart(@PathVariable("productId") Long productId) {
        String cartApiUrl = "http://localhost:8081/api/carts/sub-product/{productId}";

        ResponseEntity<Void> response = restTemplate.exchange(cartApiUrl, HttpMethod.POST, null, Void.class, productId);
        if (response.getStatusCode().is2xxSuccessful()) {
            return "redirect:/cart";
        } else {
            return "error";
        }
    }

    @GetMapping("/delete/{cartId}")
    public String deleteProductFromCart(@PathVariable("cartId") Long productId) {
        String cartApiUrl = "http://localhost:8081/api/carts/{cartId}";

        ResponseEntity<Void> response = restTemplate.exchange(cartApiUrl, HttpMethod.DELETE, null, Void.class, productId);
        if (response.getStatusCode().is2xxSuccessful()) {
            return "redirect:/cart";
        } else {
            return "error";
        }
    }

    @GetMapping("/search/price-range")
    public String getProductsByPriceRange(@RequestParam("minPrice") double minPrice, @RequestParam("maxPrice") double maxPrice, Model model) {
        String apiEndpoint = "http://localhost:8081/api/products/price-range?minPrice={minPrice}&maxPrice={maxPrice}";
        ResponseEntity<Product[]> response = restTemplate.getForEntity(apiEndpoint, Product[].class, minPrice, maxPrice);

        if (response.getStatusCode().is2xxSuccessful()) {
            Product[] products = response.getBody();
            long countCart = cartService.countCarts();


            model.addAttribute("products", products);
            model.addAttribute("countCart", countCart);

            return "shop";
        } else {
            return "error";
        }
    }

    @GetMapping("/search/color")
    public String getProductsByColor(@RequestParam("color") String color, Model model) {
        String apiEndpoint = "http://localhost:8081/api/products/color?color={color}";
        ResponseEntity<Product[]> response = restTemplate.getForEntity(apiEndpoint, Product[].class, color);

        if (response.getStatusCode().is2xxSuccessful()) {
            Product[] products = response.getBody();
            long countCart = cartService.countCarts();

            model.addAttribute("products", products);
            model.addAttribute("countCart", countCart);

            return "shop";
        } else {
            return "error";
        }
    }

    @PostMapping("/checkout")
    public String placeOrder(@ModelAttribute("orderForm") Order orderForm) {
        String orderApiUrl = "http://localhost:8081/api/orders";

        String cartApiUrl = "http://localhost:8081/api/carts";
        List<Map<String, Object>> cartItems = restTemplate.getForObject(cartApiUrl, List.class);
        List<Object> productIds = new ArrayList<>();
        for (Map<String, Object> cartItem : cartItems) {
            Map<String, Object> product = (Map<String, Object>) cartItem.get("product");
            Integer productId = (Integer) product.get("id");
            productIds.add(productId);
        }
        String productIdString = StringUtils.collectionToCommaDelimitedString(productIds);

        double sumPrice = 0;
        ResponseEntity<List<Cart>> response = restTemplate.exchange(cartApiUrl, HttpMethod.GET, null, new ParameterizedTypeReference<List<Cart>>() {});
        if (response.getStatusCode().is2xxSuccessful()) {
            List<Cart> cart = response.getBody();
            for (Cart item : cart) {
                double totalPrice = item.getProduct().getPrice() * item.getQuantity();
                sumPrice += totalPrice;
            }
        }

        Order order = new Order();
        order.setName(orderForm.getName());
        order.setEmail(orderForm.getEmail());
        order.setPhone(orderForm.getPhone());
        order.setAddress(orderForm.getAddress());
        order.setProduct_id(productIdString);
        order.setSumPrice(sumPrice + 30000);

        ResponseEntity<Order> responseOrder = restTemplate.postForEntity(orderApiUrl, order, Order.class);
        if (responseOrder.getStatusCode().is2xxSuccessful()) {
            return "redirect:/";
        } else {
            return "error";
        }

    }
}
