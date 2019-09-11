package com.pipi.email;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * todo 二期功能待开发
 *
 * 后期可以考虑做个浏览器后台,把这一系列功能做出来,邮件定时自动发送
 * 你只需要专注做一件事:回复邮件写什么内容
 */
public class ExtractEmail {

    public static void main(String[] args) {
        writeTxtFileByLine(
                extractEmailNotInChinaByLine(
                       readTxtFileByLine(
                               "C:\\Users\\Administrator\\IdeaProjects\\weather_advertise\\src\\main\\java\\com\\pipi\\email\\file\\src")),
                               "C:\\Users\\Administrator\\IdeaProjects\\weather_advertise\\src\\main\\java\\com\\pipi\\email\\file\\dest");
    }

    private static List<String> readTxtFileByLine(String srcFileName)  {
        BufferedReader br = null;
        List<String> list = new LinkedList<>();
        try{
            //todo IO(NIO)分类不太熟悉,需要系统学习
            br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(srcFileName)),"UTF-8"));
            String lineTxt;
            while ((lineTxt = br.readLine()) != null) {
                 list.add(lineTxt);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
             if(null != br){
                 try {
                     br.close();
                 } catch (IOException e) {
                     e.printStackTrace();
                 }
             }
        }
        return list;
    }


    private static Set<String> extractEmailNotInChinaByLine(List<String> lines){
        Set<String> emailList = new HashSet<>();//剔除重复邮箱

        Set<String> homeEmailSuffixList = new HashSet<>();//国内邮箱后缀大全,
        homeEmailSuffixList.add("qq.com");
        homeEmailSuffixList.add("163.com");
        homeEmailSuffixList.add("abuse");//剔除恶意单词

        //todo 待检验正则的准确性,正则不熟,系统学习
        String emailRegExp = "[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\\w](?:[\\w-]*[\\w])?\\.)+[\\w](?:[\\w-]*[\\w])?";
        for (String line:lines) {
            Pattern pattern = Pattern.compile(emailRegExp);
            Matcher matcher = pattern.matcher(line);
            while(matcher.find()){
                String email = matcher.group();

                boolean isHomeEmail = false;
                for (String homeEmailSuffix : homeEmailSuffixList){
                    if(email.contains(homeEmailSuffix)){//剔除国内邮箱
                        isHomeEmail = true;
                        break;
                    }
                }

                if(!isHomeEmail){
                    emailList.add(email);
                }
            }
        }
        return emailList;
    }

    private static void writeTxtFileByLine(Set<String> emails,String destFileName){
        BufferedWriter bw = null;
        try{
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(destFileName)), "UTF-8"));
            for (String email: emails) {
                bw.write(email);
                bw.newLine();
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            if(null != bw){
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
