package utils.myJudge;

/**
 * @Author: Sky
 * @Date: 2021/4/24 21:23
 */
public class DigitJudge {
    public static boolean isNumeric(String str){
        for (int i = 0; i < str.length(); i++){
            System.out.println(str.charAt(i));
            if (!Character.isDigit(str.charAt(i))){
                return false;
            }
        }
        return true;
    }
}
