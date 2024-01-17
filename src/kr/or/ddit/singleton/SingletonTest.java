package kr.or.ddit.singleton;

import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;

import javax.crypto.spec.PSource;

public class SingletonTest {
    // MySingleton test1 = new MySingleton(); // 외부에서 new 명령으로 객체생성ㅂㄹ=가ㅓ


    public static void main(String[] args) {
        MySingleton test2 = MySingleton.getInstance();
        MySingleton test3 = MySingleton.getInstance();

        System.out.println();
        System.out.println("test2 : " + test2.toString());
        System.out.println("test3 : " + test3.toString());
        System.out.println();


    }




}
