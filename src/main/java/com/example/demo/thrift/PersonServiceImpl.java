package com.example.demo.thrift;

import com.example.demo.thrift.generated.DataException;
import com.example.demo.thrift.generated.Person;
import com.example.demo.thrift.generated.PersonService;
import org.apache.thrift.TException;

/**
 * @Author: shiguang
 * @Date: 2021/6/3
 * @Description:
 **/
public class PersonServiceImpl implements PersonService.Iface {

    @Override
    public Person getPersonByUsername(String username) throws DataException, TException {
        System.out.println("for client："+username);
        Person person = new Person();
        person.setUsername(username);
        person.setAge(20);
        person.setMarried(false);
        return person;
    }

    @Override
    public void savePerson(Person person) throws DataException, TException {
        System.out.println("for client:");
        System.out.println(person.getUsername());
        System.out.println(person.getAge());
        System.out.println(person.isMarried());
    }
}
