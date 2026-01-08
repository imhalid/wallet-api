package wallet.api.money.utils;

import org.springframework.stereotype.Component;
import wallet.api.money.repository.AccountRepository;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.SecureRandom;

@Component
public class WalletUtils {
    private static final SecureRandom random = new SecureRandom();

    public static String generateAccountCode(
            Long userId,
            AccountRepository.Currency currency
    ) {
        int randomPart = 100000 + random.nextInt(900000); // 6 haneli

        return String.format(
                "ACC-%s-%d-%d",
                currency.name(),
                userId,
                randomPart
        );
    }

    public static String generateTrIban(
            String bankCode,
            String accountCode
    ) {
        String accountNumber = accountCodeToNumeric(accountCode);

        String countryCode = "TR";
        String checkDigits = "00";

        String ibanBody = bankCode + accountNumber;
        String rearranged = ibanBody + countryCode + checkDigits;

        String numeric = rearranged
                .replace("T", "29")
                .replace("R", "27");

        int mod97 = mod97(numeric);
        int check = 98 - mod97;

        return countryCode + String.format("%02d", check) + ibanBody;
    }

    /**
     * AccountCode → 16 haneli numeric hesap no
     */
    private static String accountCodeToNumeric(String accountCode) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(accountCode.getBytes());

            BigInteger number = new BigInteger(1, hash);
            String numeric = number.toString();

            // 16 hane garantisi
            if (numeric.length() < 16) {
                numeric = String.format("%016d", Long.parseLong(numeric));
            }

            return numeric.substring(0, 16);

        } catch (Exception e) {
            throw new RuntimeException("IBAN generation failed", e);
        }
    }

    private static int mod97(String number) {
        int result = 0;
        for (int i = 0; i < number.length(); i++) {
            result = (result * 10 + Character.getNumericValue(number.charAt(i))) % 97;
        }
        return result;
    }
}
