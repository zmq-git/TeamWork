package com.location.common.utils;

import java.util.regex.Pattern;

/**
 * Created by shijian on 2019/10/11.
 */
public class RegPatternUtil {
    final static String certPattern = "^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$|^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X|x)$";     //身份证校验
    final static String passPortPattern = "^1[45][0-9]{7}$|([P|p|S|s]\\d{7}$)|([S|s|G|g|E|e]\\d{8}$)|([Gg|Tt|Ss|Ll|Qq|Dd|Aa|Ff]\\d{8}$)|([H|h|M|m]\\d{8,10})$";     //护照校验
    final String hKMacaoPattern = "/^[HMhm]{1}([0-9]{10}|[0-9]{8})$/";     //港澳通行证校验
    final String taiWanPattern = "";     //台湾通行证校验
    public static void main(String[] args){
        System.out.println(isMapperReg("G28233515",2));
    }
    public static boolean isMapperReg(String certCode,int type){
        //身份证校验
        if(type==1){
            return Pattern.matches(certPattern, certCode);
        }
        //护照校验
        if(type==2){
            return Pattern.matches(passPortPattern, certCode);
        }
        return true;
    }
}
