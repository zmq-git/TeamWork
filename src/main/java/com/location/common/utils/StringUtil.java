package com.location.common.utils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by wangcl on 2019/8/23.
 */
public class StringUtil {
    /**
     * 截取字符
     *
     * @param str
     * @param bytes
     * @return
     */
    public static List<String> SplitString(String str, int bytes) {
        //截取长度
        int length = 0;
        // 统计字节数
        int count = 0;
        // 返回字符串
        StringBuffer reStr = new StringBuffer();
        //存放截取的字段
        List<String> list = new ArrayList<String>();

        if (str == null) {
            list.add(str);
            return list;
        }
        char[] tempChar = str.toCharArray();
        if(tempChar.length > bytes/2){
            for (int i = 0; i < tempChar.length; i++) {
                if (!strIfChinese(tempChar[i])) {
                    count = count + 1;
                } else {
                    count = count + 2;
                }
                if (count <= bytes) {
                    reStr.append(tempChar[i]);
                    if(i != tempChar.length-1){
                        length++;
                    }else{
                        list.add(reStr.toString());
                        tempChar = new char[0];
                    }

                }else{
                    list.add(reStr.toString());
                    tempChar = str.substring(length, str.length()).toCharArray();
                    count = 0;
                    reStr = new StringBuffer();
                    i = -1;
                }
            }
            return list;
        }else{
            list.add(str);
            return list;
        }
    }

    /*
     * 判断是否为汉字
     *
     * @param cc 输入字符
     */
    private static boolean strIfChinese(char cc) {
        String ccStr = String.valueOf(cc);
        return ccStr.getBytes().length > 1 ? true : false;
    }

    /**
     * 获取系统当前时间
     * @param
     * @throws Exception
     */
    public static int getNowTime(){
        int time = 1;
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMM");
        time = Integer.valueOf(dateFormat.format( now ));
        return time;
    }
    public static void main(String[] args) throws Exception{
/*        List<String> stringList = StringUtil.SplitString("你好", 3);
        System.out.println(stringList);*/
        String a = "";
        if(null!=a){
            if(a.length()<1){
                System.out.println("a为空");
            }
        }
    }

    /**
     * 纯数字(不为空)正则校验
     */
    public static boolean strIfAllNum(String str)throws Exception{
        if(null==str&&str.length()<1) {
            return false;
        }else {
            Pattern pattern = Pattern.compile("[0-9]*");
            Matcher isNum=pattern.matcher(str);
            if (!isNum.matches()){
                return false;
            }
            return true;
        }
    }
    public static String getAsString(Map map, String key){
        if(map!=null){
            return map.get(key)==null?"":map.get(key).toString();
        }
        return "";
    }


    // 截取非数字
    public static String splitNotNumber(String content) {
        Pattern pattern = Pattern.compile("\\D+");
        Matcher matcher = pattern.matcher(content);
        while (matcher.find()) {
            return matcher.group(0);
        }
        return "";
    }
    public static String getPinyin(String str) throws Exception {
        if (str== null || str.length()==0) {
            return "";
        }
        char[] t1 = null;
        t1 = str.toCharArray();
        String[] t2 = new String[t1.length];
        // 设置汉字拼音输出的格式
        HanyuPinyinOutputFormat t3 = new HanyuPinyinOutputFormat();
        t3.setCaseType(HanyuPinyinCaseType.LOWERCASE);// 小写
        t3.setToneType(HanyuPinyinToneType.WITHOUT_TONE);// 不带声调
        t3.setVCharType(HanyuPinyinVCharType.WITH_V);

        String t4 = "";
        int t0 = t1.length;
        try {
            for (int i = 0; i < t0; i++) {
                // 判断是否为汉字字符
                if (Character.toString(t1[i]).matches("[\\u4E00-\\u9FA5]+")) {
                    // 将汉字的几种全拼都存到t2数组中
                    t2 = PinyinHelper.toHanyuPinyinStringArray(t1[i], t3);
                    t4 += t2[0];// 取出该汉字全拼的第一种读音并连接到字符串t4后
                } else {
                    // 如果不是汉字字符，直接取出字符并连接到字符串t4后
                    t4 += Character.toString(t1[i]);
                }
            }
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            throw e;
        }
        return t4;
    }
}
