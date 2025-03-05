package dasturlash.topnews.util.random;

import java.security.SecureRandom;

public class RandomUtil {


    public static int getRandomCode(){
        SecureRandom random = new SecureRandom();
        return random.nextInt(9000) + 1000;
    }

}
