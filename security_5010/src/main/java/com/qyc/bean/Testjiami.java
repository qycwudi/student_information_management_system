package com.qyc.bean;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;

/**
 * @author qyc
 * @time 2020/11/15 - 18:05
 */
public class Testjiami {
   void aaa(String[] args) {
//      byte[] key = SecureUtil.generateKey(SymmetricAlgorithm.AES.getValue()).getEncoded();
        byte[] keys = {-38,93,-5,86,37,-8,-49,100,-23,122,96,37,-109,-124,92,-67};
        SymmetricCrypto aes = new SymmetricCrypto(SymmetricAlgorithm.AES,keys);
        String encryptHex = aes.encryptHex("123456");
        String decryptStr = aes.decryptStr(encryptHex, CharsetUtil.CHARSET_UTF_8);
    }
}
