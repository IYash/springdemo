package com.example.demo;


import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import org.junit.Test;

import java.io.File;


public class CodeTest  {
    @Test
    public void testAddCode(){
        coverFile("com.example.demo.annotation","BoundInfoInjected");
    }
    private void coverFile(String packagePath, String className) {
        try {
            ClassPool cp = ClassPool.getDefault();
            CtClass clazz = cp.get(packagePath+"."+className);
            CtMethod m =CtMethod.make("public String getBoundInfo(){ return this.boundInfo;}",clazz);
            clazz.addMethod(m);
            clazz.writeFile(clazz.getClass().getResource("/").getPath());
        } catch (Exception var6) {
            var6.printStackTrace();
        }

    }
    @Test
    public void test1(){
        String path = System.getProperty("user.dir");
        StringBuilder sb = new StringBuilder();
        sb.append(path).append(File.separator).append("target").append(File.separator);
        sb.append("com.example.demo.annotation".replace(".", File.separator));
        System.out.println(sb.toString());
         }
}
