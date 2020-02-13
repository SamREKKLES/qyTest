package zju.edu.qyTest.qytest;

import zju.edu.qyTest.util.Pbkdf2Sha256;
import java.io.IOException;

public class TestPbkdf2 {

    public static void main(String[] args) throws IOException {

        //获取密文（密码加盐）
        String salt = Pbkdf2Sha256.encode("111111111");
        System.out.println("salt===" + salt);
        boolean verification = Pbkdf2Sha256.verification("111111111", salt);
        System.out.println(verification);


        String oldPassword7 = "pbkdf2:sha256:150000$Uc2BWxIz$c08d2a4801af4d5c5e2f9c5038a517cdd5947e54d8759c488d88fa9dc5601530";
        String oldPassword8 = "pbkdf2:sha256:150000$vMQTfXCD$8122ff34f618670810e58acd1a7ad627b5d37c76180703081c8841c85dc9ee49";
        String oldPassword9 = "pbkdf2:sha256:150000$Gnzz5MvS$ea3a77590bdc1c203012926f4f93208b9b8984ca2ed18c563a34d27390d1c43b";
        boolean verification7 = Pbkdf2Sha256.verification("111111111", oldPassword7);
        boolean verification8 = Pbkdf2Sha256.verification("111111111", oldPassword8);
        boolean verification9 = Pbkdf2Sha256.verification("111111111", oldPassword9);
        System.out.println(verification7);
        System.out.println(verification8);
        System.out.println(verification9);
    }
}

