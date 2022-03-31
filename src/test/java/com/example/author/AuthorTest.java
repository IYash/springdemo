package com.example.author;

import org.junit.Test;

/**
 * @Author: shiguang
 * @Date: 2022/3/9
 * @Description:
 **/
public class AuthorTest {

    @Test
    public void testT(){
        String uid = "123456";
        AuthorFBackService service = new AuthorFBackService();
        service.resolveNoteVerifiedAuthor(uid);
    }

    @Test
    public void testT2(){
        String uid = "456";
        AuthorFBackService service = new AuthorFBackService();
        service.resolveNoteVerifiedAuthor(uid);
        service.enhanceAuthorTaskNotice(uid);
        System.out.println("====================");
        service.enhanceAuthorTaskNotice(uid);
    }
    @Test
    public void testT3(){

    }
    @Test
    public void testT5(){

    }
}
