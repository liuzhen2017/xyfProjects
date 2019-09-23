package com.ruoyi.system.feign;

import lombok.var;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author liuzhen
 * @version 1.0
 * @date 2019/8/4
 */
public class SyncProductType {
    public static void main(String[] args) throws FileNotFoundException {
        FileReader fr=new FileReader("jdType.txt");
        BufferedReader br=new BufferedReader(fr);
        String line="";
        String[] arrs=null;
        Set<String> fistSet =new HashSet<>();
        Set<String> sceSet =new HashSet<>();
        Set<String> threeSet =new HashSet<>();
        Map<String,String> map =new HashMap<>();

        try {
            while ((line=br.readLine())!=null) {
                arrs=line.split("||");
                fistSet.add(arrs[0]);
                sceSet.add(arrs[1]);
                threeSet.add(arrs[2]);
                map.put(arrs[2],arrs[3]);
            }
            br.close();
            fr.close();
        }catch (Exception e){
            e.printStackTrace();
        }



    }
}
