package com.example.ecommerce.controller.order;

import com.example.ecommerce.enums.EnumOrder;
import com.example.ecommerce.model.cart.Cart;
import com.example.ecommerce.model.cart.ItemCart;
import com.example.ecommerce.model.dto.order.OrderProductCreate;
import com.example.ecommerce.model.order.OrderProduct;
import com.example.ecommerce.model.product.Product;
import com.example.ecommerce.model.shop.Shop;
import com.example.ecommerce.model.user.User;
import com.example.ecommerce.service.cart.ICartService;
import com.example.ecommerce.service.itemcart.ItemCartService;
import com.example.ecommerce.service.order.IOrderProductService;
import com.example.ecommerce.service.product.IProductService;
import com.example.ecommerce.service.shop.IShopService;
import com.example.ecommerce.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/orders")
public class OrderProductRestController {
    @Autowired
    private IOrderProductService orderProductService;
    @Autowired
    private IUserService userService;
    @Autowired
    private ItemCartService itemCartService;
    @Autowired
    private ICartService cartService;
    @Autowired
    private IShopService shopService;
    @Autowired
    private IProductService productService;

    @GetMapping
    public ResponseEntity<Iterable<OrderProduct>> findAll(){
        return new ResponseEntity<>(orderProductService.findAll(), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<OrderProduct> findById(@PathVariable Long id){
        Optional<OrderProduct> currentOrderProduct = orderProductService.findById(id);
        if (!currentOrderProduct.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(currentOrderProduct.get(), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<?> save(@RequestBody OrderProductCreate orderProductCreate){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String username = userDetails.getUsername();
        Optional<User> currentUser = userService.findByUsername(username);
        if (!currentUser.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (orderProductCreate.getMoneyOrder() < currentUser.get().getWallet()){
            return new ResponseEntity<>("Không đủ tiền", HttpStatus.ACCEPTED);
        }
        Optional<Cart> currentCart = cartService.findByUser(currentUser.get());
        if (!currentCart.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<ItemCart> currentItemCarts = orderProductCreate.getItemCarts();
        List<Shop> currentShops = new ArrayList<>();
        for (int i = 0; i < currentItemCarts.size(); i++){
            boolean check = false;
            Shop shop = currentItemCarts.get(i).getProduct().getShop();
            for (int j = 0; j < currentShops.size(); j++){
                if (shop.getId() == currentShops.get(j).getId()){
                    check = true;
                }
            }
            if (check){
                continue;
            }
            currentShops.add(shop);
        }
        for (int i = 0; i < currentShops.size(); i++){
            OrderProduct orderProduct = orderProductService.save(new OrderProduct());
            orderProduct.setEnumOrder(EnumOrder.PENDING);
            orderProduct.setName(orderProductCreate.getName());
            orderProduct.setEmail(orderProductCreate.getEmail());
            orderProduct.setPhone(orderProductCreate.getPhone());
            orderProduct.setAddress(orderProductCreate.getAddress());
            orderProduct.setUser(currentUser.get());
            orderProduct.setItemCarts(new ArrayList<>());
            orderProduct.setMoneyOrder(0L);
            for (int j = 0; j < currentItemCarts.size(); j++){
                Shop shop = currentItemCarts.get(j).getProduct().getShop();
                if (shop.getId() == currentShops.get(i).getId()){
                    Long money = currentItemCarts.get(j).getProduct().getPrice() * currentItemCarts.get(j).getQuantity();
                    currentCart.get().setTotalMoney(currentCart.get().getTotalMoney() - money);
                    orderProduct.setMoneyOrder(orderProduct.getMoneyOrder() + money);
                    currentItemCarts.get(j).setStatus(true);
                    currentItemCarts.get(j).setOrderProduct(orderProduct);
                    itemCartService.save(currentItemCarts.get(j));
                }
            }
            orderProductService.save(orderProduct);
        }
        for (int j = 0; j < currentItemCarts.size(); j++){
            currentCart.get().getItemCarts().remove(currentItemCarts.get(j));
        }
        cartService.save(currentCart.get());
        currentUser.get().setLockWallet(currentUser.get().getLockWallet() + orderProductCreate.getMoneyOrder());
        currentUser.get().setWallet(currentUser.get().getWallet() - orderProductCreate.getMoneyOrder());
        return new ResponseEntity<>(userService.save(currentUser.get()),HttpStatus.ACCEPTED);
    }
    @GetMapping("/pending/{shopId}")
    public ResponseEntity<Iterable<OrderProduct>> findPendingOrderByShopId(@PathVariable Long shopId){
        return new ResponseEntity<>(orderProductService.findPendingOrderByShopId(shopId), HttpStatus.OK);
    }
    @GetMapping("/all/{shopId}")
    public ResponseEntity<Page<OrderProduct>> findAllByShopId(@PathVariable Long shopId, @PageableDefault(value = 6) Pageable pageable){
        return new ResponseEntity<>(orderProductService.findAllByShopId(shopId, pageable), HttpStatus.OK);
    }
    @GetMapping("/confirm/{shopId}")
    public ResponseEntity<Iterable<OrderProduct>> findConfirmOrderByShopId(@PathVariable Long shopId){
        return new ResponseEntity<>(orderProductService.findConfirmOrderByShopId(shopId), HttpStatus.OK);
    }
    @GetMapping("/complete/{shopId}")
    public ResponseEntity<Iterable<OrderProduct>> findCompleteOrderByShopId(@PathVariable Long shopId){
        return new ResponseEntity<>(orderProductService.findCompleteOrderByShopId(shopId), HttpStatus.OK);
    }
    @PutMapping("/confirm-order/{orderId}")
    public ResponseEntity<?> confirmOrder(@PathVariable Long orderId){
        Optional<OrderProduct> currentOrderProduct = orderProductService.findById(orderId);
        if (!currentOrderProduct.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Product currentProduct = currentOrderProduct.get().getItemCarts().get(0).getProduct();
        Optional<Shop> currentShop = shopService.findByProducts(currentProduct);
        if (!currentShop.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<ItemCart> itemCarts = currentOrderProduct.get().getItemCarts();
        currentShop.get().setTurnover(currentShop.get().getTurnover() + currentOrderProduct.get().getMoneyOrder());
        User user = currentOrderProduct.get().getUser();
        user.setLockWallet(user.getLockWallet() - currentOrderProduct.get().getMoneyOrder());
        for (int i = 0; i < itemCarts.size(); i++){
            if (itemCarts.get(i).getQuantity() > itemCarts.get(i).getProduct().getQuantity()){
                return new ResponseEntity<>("Không đủ sản phẩm trong kho", HttpStatus.ACCEPTED);
            }
            Product product = itemCarts.get(i).getProduct();
            product.setQuantity(product.getQuantity() - itemCarts.get(i).getQuantity());
            product.setCountBuy(product.getCountBuy() + 1);
            productService.save(product);
        }
        currentOrderProduct.get().setEnumOrder(EnumOrder.CONFIRM);
        userService.save(user);
        shopService.save(currentShop.get());
        return new ResponseEntity<>(orderProductService.save(currentOrderProduct.get()), HttpStatus.ACCEPTED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable Long id){
        orderProductService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @GetMapping("/user/{action}")
    public ResponseEntity<Iterable<OrderProduct>> findAllByUserAndEnumOrder(@PathVariable String action){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String username = userDetails.getUsername();
        Optional<User> currentUser = userService.findByUsername(username);
        if (!currentUser.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Iterable<OrderProduct> orderProducts;
        switch (action){
            case "Đang chờ": orderProducts = orderProductService.findAllByUserAndEnumOrder(currentUser.get(), EnumOrder.PENDING); break;
            case "Đã mua": orderProducts = orderProductService.findAllByUserAndEnumOrder(currentUser.get(), EnumOrder.COMPLETE); break;
            case "Đã xác nhận": orderProducts = orderProductService.findAllByUserAndEnumOrder(currentUser.get(), EnumOrder.CONFIRM); break;
            default: orderProducts = orderProductService.findAllByUser(currentUser.get()); break;
        }
        return new ResponseEntity<>(orderProducts, HttpStatus.OK);
    }
    @PutMapping("/user-complete/{id}")
    public ResponseEntity<OrderProduct> userCompleteOrder(@PathVariable Long id){
        Optional<OrderProduct> currentOrderProduct = orderProductService.findById(id);
        if (!currentOrderProduct.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        currentOrderProduct.get().setEnumOrder(EnumOrder.COMPLETE);
        return new ResponseEntity<>(orderProductService.save(currentOrderProduct.get()), HttpStatus.ACCEPTED);
    }
}
