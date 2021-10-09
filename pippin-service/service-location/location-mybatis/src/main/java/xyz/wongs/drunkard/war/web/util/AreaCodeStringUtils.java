package xyz.wongs.drunkard.war.web.util;

import java.util.Random;

/**
 * @author WCNGS@QQ.CO
 * @since V1.0
 * @date 2018/7/1 1:03
 **/
public class AreaCodeStringUtils {


    public static String RESULT_KEY_FLAG = "flag";
    public static String RESULT_KEY = "location";

    /**
     * 方法实现说明
     * @method      interceptionStringByLastIndexOf
     * @author      WCNGS@QQ.COM
     * @since
     * @see
     * @param str
     * @param suffix
     * @return      java.lang.String
     * @exception
     * @date        2018/7/1 1:03
     */
    public static String interceptionStringByLastIndexOf(String str ,String suffix){

        int i = str.lastIndexOf(suffix);
        return str.substring(0,i);
    }

    /**
     * 方法实现说明
     * @author      WCNGS@QQ.COM
     * @since
     * @see
     * @param locationCode
     * @param level
     * @return      String
     * @exception
     * @date        2018/7/1 9:33
     */
    public static String getUrlStrByLocationCode(String locationCode,int level){

        int bit = (level-1)*2;
        String subStr = locationCode.substring(0,bit);
        char[] ch= subStr.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i <bit ; i++) {
            sb.append(ch[i]);
            if(i!=0 && (i+1)%2==0){
                sb.append("/");
            }
        }
        return sb.toString();
    }

    public static int getSecond(int bound){
        return new Random().nextInt(bound);
    }

}