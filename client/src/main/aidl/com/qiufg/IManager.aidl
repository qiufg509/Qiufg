// IManager.aidl
package com.qiufg;
import com.qiufg.model.Person;
// Declare any non-default types here with import statements

interface IManager {

    String switchMoney(String money);

    List<Person> getPerson();

    void addPerson(inout Person person);
}
