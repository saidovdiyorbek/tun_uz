package dasturlash.tun_uz.util.random;

import java.security.SecureRandom;
import java.util.Random;

public class RandomUtil {


    public static int getRandomCode(){
        SecureRandom random = new SecureRandom();
        return random.nextInt(9000) + 1000;
    }

}
