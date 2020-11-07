package com.location.common.utils;

import org.springframework.beans.BeanUtils;

import java.lang.reflect.Method;
import java.util.*;

//import com.sun.corba.se.impl.orbutil.ObjectStreamClass_1_3_1;

/**
 * Created by superLiang on 2019/9/2.
 */
public class BeanTransUtil {

    public static <T> List<T> beanTrans(Object obj, Class<T> clazz) throws Exception {
        List<Object> objs = new ArrayList<Object>();
        if (obj instanceof List<?>) {
            objs = (List<Object>) obj;
        } else {
            objs.add(obj);
        }
        List<T> list = new ArrayList<T>();
        for (Object objSource : objs) {
            T objTarget = clazz.newInstance();
            BeanUtils.copyProperties(objSource, objTarget);
            list.add(objTarget);
        }
        return list;
    }

    /**
     * @param objectList
     * @param clazz1
     * @param clazz2
     * @return
     * @throws Exception
     * @description 支持dataobject和vo之间互相转换
     */
    public static List<Object> transBean(List<Object> objectList, Class clazz1, Class clazz2) throws Exception {
        List<Object> list = new ArrayList<Object>();
        Map<String, String> methodGetSet = new HashMap<String, String>();
        Method[] methods = clazz1.getMethods();
        String keyRe = "";
        for (Method method : methods) {
            String methodName = method.getName();
            if (methodName.equals("getClass")) {
                continue;
            }
            if (methodName.startsWith("get")) {
                String after = methodName.substring(3);
                if (methodGetSet.values().contains("set" + after)) {
                    methodGetSet.put(methodName, "set" + after);
                } else {
                    methodGetSet.put(methodName, keyRe += 1);
                }
                continue;
            }
            if (methodName.startsWith("set")) {
                String after = methodName.substring(3);
                if (methodGetSet.keySet().contains("get" + after)) {
                    methodGetSet.put("get" + after, methodName);
                } else {
                    methodGetSet.put(keyRe += 1, methodName);
                }
                continue;
            }
        }

        Iterator<String> ites = methodGetSet.keySet().iterator();
        List<String> deleteKeys = new ArrayList<String>();
        while (ites.hasNext()) {
            String key = ites.next();
            if (key.contains("1")) {
                deleteKeys.add(key);
            }
        }
        for (String key : deleteKeys) {
            methodGetSet.remove(key);
        }

        for (Object obj : objectList) {
            Object obj1 = clazz2.newInstance();

            Iterator<String> keys = methodGetSet.keySet().iterator();
            while (keys.hasNext()) {
                String getMethod = keys.next();
                Method getMethodRe = clazz1.getMethod(getMethod);
                Method getMethodRe1 = clazz2.getMethod(getMethod);
                String setMethod = methodGetSet.get(getMethod);
                Method setMethodRe = clazz2.getMethod(setMethod, getMethodRe1.getReturnType());
                Object objGet = getMethodRe.invoke(obj);
                try {
                    setMethodRe.invoke(obj1, objGet);
                } catch (Exception ex) {
                    if (getMethodRe1.getReturnType().equals(Long.class)) {
                        //Method setMethodRe2 = clazz2.getMethod(setMethod,long.class);
                        if (getMethodRe1.getReturnType().equals(Long.class)) {
                            setMethodRe.invoke(obj1, Long.valueOf(objGet + ""));
                        } else {

                        }

                    } else if (getMethodRe1.getReturnType().equals(long.class)) {
                        Method setMethodRe2 = clazz2.getMethod(setMethod, Long.class);
                        setMethodRe2.invoke(obj1, objGet);
                    }
                    //System.out.println("getMethod:"+getMethod +"\n" + "setMethod" +setMethod);
                }

            }

            list.add(obj1);

        }

        return list;
    }
    public static  <T>T getAsNull(T o){
        if(o instanceof String){
            return o.toString().equals("")?null:o;
        }
        if(o instanceof Integer){
            return (Integer)o==0?null:o;
        }
        if(o instanceof Long){
            return (Long)o==0?null:o;
        }
        return null;
    }
    public static void main(String[] args) throws Exception {
        String a= BeanTransUtil.getAsNull("");
        System.out.println(a);
/*        CmCustomerDO bean = new CmCustomerDO();
        bean.setCustName("hello");
        bean.setRemarks("yes");
        List<Object> list = new ArrayList<Object>();
        list.add(bean);
        List outList = transBean(list, CmCustomerDO.class, CmCustomerVO.class);
        System.out.println("yes");*/
    }
}
