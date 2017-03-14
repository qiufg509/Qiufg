// IManager.aidl
package com.qiufg;
import com.qiufg.model.Person;
// Declare any non-default types here with import statements

interface IManager {

    String switchMoney(String money);

    List<Person> getPerson();

// in 表示数据只能由客户端流向服务端， out 表示数据只能由服务端流向客户端，而 inout 则表示数据可在服务端与客户端之间双向流通。
//http://www.jianshu.com/p/ddbb40c7a251
    void addPerson(inout Person person);
}
