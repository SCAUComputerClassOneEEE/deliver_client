package utils.constructor;

import com.alibaba.fastjson.JSONObject;

import java.lang.reflect.Field;

/**
 * @Author: Sky
 * @Date: 2021/4/25 15:42
 */
public class ConstructorUtil {
    public static void newInstance(Object o, JSONObject parse) {
        if (parse.isEmpty()) return;
        System.out.println(parse.toString());
        try {
            Field[] declaredFields = o.getClass().getDeclaredFields();
            for (Field field : declaredFields) {
                field.setAccessible(true);
                field.set(o,
                        parse.getObject(
                                field.getName(),
                                Class.forName(field.getGenericType().getTypeName())
                        )
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(o);
    }
}
