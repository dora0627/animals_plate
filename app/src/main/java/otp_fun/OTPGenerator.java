package otp_fun;

import java.security.SecureRandom;

public class OTPGenerator {
    private static final String NUMERIC_CHARACTERS = "0123456789";
    private static final int OTP_LENGTH = 4; // OTP 碼的長度

    public static String generateOTP() {
        SecureRandom random = new SecureRandom();
        StringBuilder otp = new StringBuilder();

        for (int i = 0; i < OTP_LENGTH; i++) {
            int index = random.nextInt(NUMERIC_CHARACTERS.length());
            char character = NUMERIC_CHARACTERS.charAt(index);
            otp.append(character);
        }

        return otp.toString();
    }

    public static void main(String[] args) {
        String otp = generateOTP();
        System.out.println("Generated OTP: " + otp);

    }
}
