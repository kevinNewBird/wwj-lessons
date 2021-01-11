/*
 *	History				Who				What
 *  2016-1-12			shao.rong			Created.
 */
package com.test.situo;


import io.vavr.control.Try;

import java.math.BigInteger;
import java.security.MessageDigest;

public class MD5Util {
 
    /**
     * 
     
     * Description:  MD5加密方式（PHP模式）<BR> 
     
     * @author zhu.qiyuan
     
     * @date 2016年6月17日 下午2:25:13
     
     * @param txt
     * @return
     */
    public static final String md5ForPhp(String txt) {
        try{
             MessageDigest md = MessageDigest.getInstance("MD5");
             md.update(txt.getBytes("GBK"));    //问题主要出在这里，Java的字符串是unicode编码，不受源码文件的编码影响；而PHP的编码是和源码文件的编码一致，受源码编码影响。
             StringBuffer buf=new StringBuffer();            
             for(byte b:md.digest()){
                  buf.append(String.format("%02x", b&0xff));        
             }
            return  buf.toString();
          }catch( Exception e ){
              e.printStackTrace(); 

              return null;
           } 
   }
    
   /**
    *  
    
    * Description:  MD5加密方法<BR> 
    
    * @author zhu.qiyuan
    
    * @date 2016年6月27日 下午3:15:38
    
    * @param source
    * @return
    */
    public static final String encodeMD5Hex(String source) {
        return Try.of(() -> {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update((source).getBytes("UTF-8"));
            byte b[] = md5.digest();

            int i;
            StringBuffer buf = new StringBuffer("");

            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0) {
                    i += 256;
                }
                if (i < 16) {
                    buf.append("0");
                }
                buf.append(Integer.toHexString(i));
            }

            return buf.toString();
        }).getOrElseGet(e -> null);

    } 
    
    
    
}
