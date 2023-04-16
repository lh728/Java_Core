package net.javaguides.spring.ioc;

public class JHSDB_TestCase {
    static class Test{
        static ObjectHolder staticObj = new ObjectHolder();
        ObjectHolder instanceObj = new ObjectHolder();
        void foo(){
            ObjectHolder localObj = new ObjectHolder();
            System.out.println("done");
        }

        private static class ObjectHolder {
        }

        public static void main(String[] args) {
            Test test = new Test();
            test.foo();
        }
    }
}
