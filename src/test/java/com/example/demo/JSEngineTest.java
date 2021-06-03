package com.example.demo;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.js.RuleInfo;
import org.junit.Test;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

/**
 * @Author: shiguang
 * @Date: 2021/4/21
 * @Description:
 **/
public class JSEngineTest {
    public static ScriptEngineManager engineManager = new ScriptEngineManager();
    ScriptEngine engine = engineManager.getEngineByName("javascript");
    @Test
    public void digitTest() throws Exception{
        String function = "function checkDigit(s){ var reg=/^[0-9]{1,20}$/;return reg.test(s);}";
        engine.eval(function);
        Invocable invocable = (Invocable) engine;
        Object res = invocable.invokeFunction("checkDigit","109");
        System.out.println(res);
    }
    @Test
    public void chineseTest() throws Exception{//可以完成标点，数字，字母的判断，非中文的使用
        String function = "function checkChinese(s){var reg =new RegExp(\"[\\\\u4E00-\\\\u9FFF]+\",\"g\"); return reg.test(s);}";
        System.out.println(function);
        engine.eval(function);
        Invocable invocable = (Invocable) engine;
        Object res = invocable.invokeFunction("checkChinese","牛");
        System.out.println(res);
        //"function checkChinese(s){var reg =new RegExp(\"[\\\\u4E00-\\\\u9FFF]+\",\"g\");return !reg.test(s);}"
    }

    @Test
    public void pTest() throws Exception{
        String function = "function checkFormat(s){var reg =new RegExp(\"[\\\\u4E00-\\\\u9FFF]+\");" +
                "var len= s.length; if(!reg.test(s.charAt(len-1))) return true;" +
                "var subStr=\"R]\"; if(s.match(subStr)) return true;" +
                "return reg.test(s);}";
        System.out.println(function);
        engine.eval(function);
        Invocable invocable = (Invocable) engine;
        Object res = invocable.invokeFunction("checkFormat","人累");
        System.out.println(res);
        //"function checkFormat(s){var reg =new RegExp(\"[\\\\u4E00-\\\\u9FFF]+\",\"g\");var len= s.length; if(!reg.test(s.charAt(len-1))) return true;var subStr=\"R]\"; if(s.match(subStr)) return true;return !reg.test(s);}"
    }
    @Test
    public void jsJsonTest() throws Exception{
        String text = "{\"scriptParamList\":[{\"scriptName\":\"checkFormat\",\"scriptContent\":\"function checkFormat(s){ var reg =/R]/; if(reg.test(s)) return true; return false;}\"}]}";
        RuleInfo info = JSONObject.parseObject(text,RuleInfo.class);
        System.out.println(info.getScriptParamList().get(0).getScriptContent());
        engine.eval(info.getScriptParamList().get(0).getScriptContent());
        Invocable invocable = (Invocable) engine;
        Object res = invocable.invokeFunction("checkFormat","R]");
        System.out.println(res);
    }

    @Test
    public void testConsole() throws Exception{// 注意g全局属性的范围
        String function = "function checkFormat(s){var reg =new RegExp(\"[\\\\u4E00-\\\\u9FFF]+\",\"g\");" +
                "var len= s.length; if(!reg.test(s.charAt(len-1))) return true; return !reg.test(s);}";
        System.out.println(function);
        engine.eval(function);
        Invocable invocable = (Invocable) engine;
        Object res = invocable.invokeFunction("checkFormat","牛");
        System.out.println(res);
    }
    @Test
    public void testPunctuation() throws Exception{// 注意g全局属性的范围
        String function = "function checkFormat(s){ var reg =/[R]]/; if(reg.test(s)) return true; var len = s.length; s = s.charAt(len-1); var enPattern = /[,;:'\"(@[{/\\\\]/; if(enPattern.test(s)) return true;var chPattern =/[，；：'\"（@【「/、]/; if(chPattern.test(s)) return true; return false; }";
        System.out.println(function);
        engine.eval(function);
        Invocable invocable = (Invocable) engine;
        Object res = invocable.invokeFunction("checkFormat","AA");
        System.out.println(res);
    }
    @Test
    public void testContain() throws Exception{// 注意g全局属性的范围
        String function = "function checkNumber(s){ var reg = /^[0-9]*$/;if(reg.test(s)){var source = [233,111,666]; var len = source.length;var i =0 ;for (;i<len;i++){ if(s.indexOf(source[i])!=-1) return false;} return true;} return false;}";
        System.out.println(function);
        engine.eval(function);
        Invocable invocable = (Invocable) engine;
        Object res = invocable.invokeFunction("checkNumber","AA");
        System.out.println(res);
    }
    @Test
    public void testR() throws Exception{
        String function = "function checkFormat(s){ var reg =/R]/; if(reg.test(s)) return true; return false;}";
        System.out.println(function);
        engine.eval(function);
        Invocable invocable = (Invocable) engine;
        Object res = invocable.invokeFunction("checkFormat","R]");
        System.out.println(res);
    }
    @Test
    public void testContentPunctuation() throws Exception{
        String function = "function checkPunctuation(s){var reg =new RegExp(\"[\\\\u4E00-\\\\u9FFF]+\");if(reg.test(s)) return false;else{var pattern = /[0-9a-zA-Z]+/;if(pattern.test(s)) return false;}return true;}";
        System.out.println(function);
        engine.eval(function);
        Invocable invocable = (Invocable) engine;
        Object res = invocable.invokeFunction("checkPunctuation","1,3e");
        System.out.println(res);
    }
}
