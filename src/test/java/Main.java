import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.AntPathMatcher;

/**
 * @author yuit
 * @create 2018/10/22 17:38
 * @description
 * @modify
 */
public class Main {

    public static void main(String[] args) throws InstantiationException, IllegalAccessException{

        String h = "123456";

        System.out.println(false||true);
        System.out.println(true|false);
        System.out.println(BCryptPasswordEncoder.class.newInstance().encode("123456"));
        System.out.println(BCryptPasswordEncoder.class.newInstance().encode("secret"));
    }

}

/**




 */