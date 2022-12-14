package com.yang.manet.Utils;

import java.util.Random;
import java.util.UUID;

public class RandomID {
    public static void main(String[] args) {
       System.out.println( getRandomOrderNumber());
    }

    public static String getRandomOrderNumber() {
        Random random = new Random();
        String randChar="";
        randChar += (char)(random.nextInt(25)+65);
        randChar += (char)(random.nextInt(25)+65);
        int hashCodeV = UUID.randomUUID().toString().hashCode();
        if(hashCodeV < 0) {//有可能是负数
            hashCodeV = - hashCodeV;
        }
        return randChar + hashCodeV + (char)(random.nextInt(25)+65);
    }
    public static int getCPUID(){
        int max = 4;
        int min = 0;
        Random random = new Random();
        int s = random.nextInt(max) % (max - min + 1) + min;
        return s;
    }
    public static int getCPUNum5(){
        int max = 50;
        int min = 40;
        Random random = new Random();
        int s = random.nextInt(max) % (max - min + 1) + min;
        return s;
    }
    public static int getCPUNum4(){
        int max = 40;
        int min = 30;
        Random random = new Random();
        int s = random.nextInt(max) % (max - min + 1) + min;
        return s;
    }
    public static int getCPUNum3(){
        int max = 30;
        int min = 20;
        Random random = new Random();
        int s = random.nextInt(max) % (max - min + 1) + min;
        return s;
    }
    public static int getCPUNum2(){
        int max = 20;
        int min = 10;
        Random random = new Random();
        int s = random.nextInt(max) % (max - min + 1) + min;
        return s;
    }
    public static int getCPUNum(){
        int max = 10;
        int min = 0;
        Random random = new Random();
        int s = random.nextInt(max) % (max - min + 1) + min;
        return s;
    }
    public static int getCPUNumTE(){
        int max = 20;
        int min = 0;
        Random random = new Random();
        int s = random.nextInt(max) % (max - min + 1) + min;
        return s;
    }
    public static int getCPUNumTE2(){
        int max = 5;
        int min = 0;
        Random random = new Random();
        int s = random.nextInt(max) % (max - min + 1) + min;
        return s;
    }
    public static int genID(){
        int max = 10;
        int min = 2;
        Random random = new Random();
        int s = random.nextInt(max) % (max - min + 1) + min;
        return s;
    }
    public static String genMatrix(){
        int max = 30;
        int min = 0;
        Random random = new Random();
        int s = random.nextInt(max) % (max - min + 1) + min;
        return String.valueOf(s);
    }
    public static String genID2(){
        int max = 2000;
        int min = 100;
        Random random = new Random();
        int s = random.nextInt(max) % (max - min + 1) + min;
        return String.valueOf(s);
    }

    public static int genIDWorker(){
        int max = 30000;
        int min = 1;
        Random random = new Random();
        int s = random.nextInt(max) % (max - min + 1) + min;
        return s;
    }

}
