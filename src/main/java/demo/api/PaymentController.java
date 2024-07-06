package demo.api;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
//import com.google.gson.Gson;
//import com.google.gson.JsonObject;
import org.springframework.web.servlet.view.RedirectView;

import demo.VNPayConfig;
import demo.modelApi.PaymentDTO;
import demo.modelApi.TransactionStatusDTO;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {
	
	@GetMapping("/create_payment")
	public RedirectView createPayment(HttpServletRequest req){
        long amount = Integer.parseInt(req.getParameter("amount"))*100L;
//        String bankCode = req.getParameter("bankCode");
        String vnp_TxnRef = VNPayConfig.getRandomNumber(8);
        String vnp_IpAddr = VNPayConfig.getIpAddress(req);

        String vnp_TmnCode = VNPayConfig.vnp_TmnCode;
        
        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", VNPayConfig.vnp_Version);
        vnp_Params.put("vnp_Command", VNPayConfig.vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_TxnRef", VNPayConfig.getRandomNumber(8));
        vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang"+VNPayConfig.getRandomNumber(8));
        vnp_Params.put("vnp_OrderType", VNPayConfig.vnp_OrderType);
        //vnp_Params.put("vnp_BankCode",bankCode);
        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_ReturnUrl",VNPayConfig.vnp_ReturnUrl);
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang:" + vnp_TxnRef);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
        
        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);
        
        String queryUrl = VNPayConfig.getPaymentURL(vnp_Params, true);
        String hashData= VNPayConfig.getPaymentURL(vnp_Params, false);
        String vnpSecureHash=VNPayConfig.hmacSHA512(VNPayConfig.secretKey, hashData);
        
        queryUrl += "&vnp_SecureHash=" + vnpSecureHash;
        String paymentUrl = VNPayConfig.vnp_PayUrl + "?" + queryUrl;
        System.out.println("Link"+paymentUrl);
        
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", paymentUrl);

        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(paymentUrl);
        return redirectView;
	}
	
}
