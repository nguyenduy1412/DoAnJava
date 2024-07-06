package demo;

import org.springframework.context.annotation.Configuration;

import jakarta.servlet.http.HttpServletRequest;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

@Configuration
public class VNPayConfig {

	public static String vnp_PayUrl = "https://sandbox.vnpayment.vn/paymentv2/vpcpay.html";
	public static String vnp_TmnCode = "5XXU4I5K";
	public static String vnp_Version = "2.1.0";
	public static String vnp_Command = "pay";
	public static String secretKey = "F8EUYXXD76WX07963C8CH4YUSLZ137I8";
	public static String vnp_ApiUrl = "https://sandbox.vnpayment.vn/merchant_webapi/api/transaction";
	public static String vnp_ReturnUrl = "http://localhost:8080/vnp-return";
	public static String vnp_OrderType = "other";

	public static String hmacSHA512(final String key, final String data) {
		try {

			if (key == null || data == null) {
				throw new NullPointerException();
			}
			final Mac hmac512 = Mac.getInstance("HmacSHA512");
			byte[] hmacKeyBytes = key.getBytes();
			final SecretKeySpec secretKey = new SecretKeySpec(hmacKeyBytes, "HmacSHA512");
			hmac512.init(secretKey);
			byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
			byte[] result = hmac512.doFinal(dataBytes);
			StringBuilder sb = new StringBuilder(2 * result.length);
			for (byte b : result) {
				sb.append(String.format("%02x", b & 0xff));
			}
			return sb.toString();

		} catch (Exception ex) {
			return "";
		}
	}

	public static String getIpAddress(HttpServletRequest request) {
		String ipAdress;
		try {
			ipAdress = request.getHeader("X-FORWARDED-FOR");
			if (ipAdress == null) {
				ipAdress = request.getRemoteAddr();
			}
		} catch (Exception e) {
			ipAdress = "Invalid IP:" + e.getMessage();
		}
		return ipAdress;
	}

	public static String getRandomNumber(int len) {
		Random rnd = new Random();
		String chars = "0123456789";
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++) {
			sb.append(chars.charAt(rnd.nextInt(chars.length())));
		}
		return sb.toString();
	}

	public static String getPaymentURL(Map<String, String> paramsMap, boolean encodeKey) {
		return paramsMap.entrySet().stream().filter(entry -> entry.getValue() != null && !entry.getValue().isEmpty())
				.sorted(Map.Entry.comparingByKey())
				.map(entry -> (encodeKey ? URLEncoder.encode(entry.getKey(), StandardCharsets.US_ASCII)
						: entry.getKey()) + "=" + URLEncoder.encode(entry.getValue(), StandardCharsets.US_ASCII))
				.collect(Collectors.joining("&"));
	}
}
