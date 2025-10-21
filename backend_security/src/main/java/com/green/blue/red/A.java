package com.green.blue.red;

import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class A {
    static String slice(String str){
        return str.substring(0,str.length()-1);
    }
    static int slice2(String str){
        return Integer.parseInt(str.substring(str.length()-1));
    }
    public static void main(String[] args) {
        String[][] str = {
                {"사랑5", "믿음7"},
                {"증오9", "필승3"},
                {"별2", "기현8"}
        };
        int[] in={1,2,3,4};
        Map<String, String> map = new HashMap<>();
        String strResult=null;
        int numResult=0;
        for(int i=0;i<=1;i++){
            int index = i;
            List<String> list1=new ArrayList<>();
            List<Integer> list2=new ArrayList<>();
            Arrays.stream(str).forEach(a-> {
                list1.add(slice(a[index]));
                list2.add(slice2(a[index]));
            });
            strResult = list1.stream().reduce((a,b)->a+=b).get();
            numResult=list2.stream().reduce((a,b)-> a+=b).get();
        }
    }
}