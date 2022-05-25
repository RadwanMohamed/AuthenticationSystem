package com.blog.app.ws.shared;

import java.security.SecureRandom;
import java.util.Random;

import org.springframework.stereotype.Component;

@Component
public class Utils {
	private final Random RANDOM = new SecureRandom();
	private final String ALPHBET = "0123456789abcdefghijklmnopqrstuvwxyz";
	private final int ITERATIONS = 10000;
	private final int KEY_LENGTH = 256;
	public String generateUserId(int length) {
		return generateRandomStrong(length);
	}
	private String generateRandomStrong(int length) {
		StringBuilder returnValue = new StringBuilder(length);
		for(int i=0; i < length;i++) {
			returnValue.append(ALPHBET.charAt(RANDOM.nextInt(ALPHBET.length())));
		}
		return new String(returnValue);
	}
}
