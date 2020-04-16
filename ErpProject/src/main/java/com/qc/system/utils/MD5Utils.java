package com.qc.system.utils;

import org.apache.shiro.crypto.hash.Md5Hash;

import java.util.UUID;

public class MD5Utils {

    /**
     * 加密密码
     * @param source
     * @param salt
     * @param hashIterations
     * @return
     */
    public static String md5(String source,Object salt,Integer hashIterations){
        return new Md5Hash(source,salt,hashIterations).toString();
    }

    /**
     * 生成盐
     * @return
     */
    public static String getSalt(){
        return UUID.randomUUID().toString().replace("-", "").toUpperCase();
    }

}