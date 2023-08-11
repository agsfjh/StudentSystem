import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;


public class App {

    //使用static静态代码块对数据进行初始化
    //静态代码块只在类加载时执行一次
    static ArrayList<User> userList = new ArrayList<>();

    static {
        userList.add(new User("user","y123","376657699221283675","12378654631"));
    }

    public static void main(String[] args) {
        //定义常量来表示用户选择
        //ctrl+shift+u 转换字符串大小写
        //常量名全为大写并在字母间用下划线隔开
        final String LOGIN = "1";
        final String REGISTER = "2";
        final String FORGET_PASSWORD = "3";
        final String EXIT = "4";


        Scanner sc = new Scanner(System.in);

        //ctrl+alt+t包裹代码
        //用户页面
        while (true) {
            System.out.println("欢迎来到登录页面");
            System.out.println("请选择你的操作：1、登录 2、注册 3、忘记密码 4、退出");

            String choose = sc.next();
            switch(choose){
                //用常量来表示用户的选择，使代码更容易理解
                case LOGIN:{
                    login(userList);
                    break;
                }
                case REGISTER:{
                    register(userList);
                    break;
                }
                case FORGET_PASSWORD:{
                    forgetPassword(userList);
                    break;
                }
                case EXIT:{
                    System.out.println("欢迎下次使用！");
                    System.exit(0);
                }
                default:{
                    System.out.println("没有此选项！");
                    break;
                }
            }
        }

    }


    //注册
    private static void register(ArrayList<User> userList) {
        Scanner sc = new Scanner(System.in);
        String userName;
        String passWord1;
        String passWord2;
        String idCard;
        String phoneNumber;
        //输入用户名
        while (true) {
            System.out.println("请输入用户名！");
            userName = sc.next();
            //判断用户名
            //优先判断格式最后判断唯一性
            //使用方法判断checkUserName()
            //判断格式
            boolean flag1 = checkUserName(userName);
            if (!flag1){
                System.out.println("用户名格式错误！");
                continue;
            }
            //判断是否唯一
            boolean flag2 = contains(userList,userName);
            if (flag2){
                System.out.println("用户名"+userName+"已存在，请重新输入！");
            }else {
                System.out.println("用户名"+userName+"可用！");
                break;
            }
        }

        //输入密码
        while (true) {
            System.out.println("请输入密码：");
            passWord1 = sc.next();
            System.out.println("请再次确认密码：");
            passWord2 = sc.next();
            if (!passWord1.equals(passWord2)){
                System.out.println("两次密码不一致，请重新输入！");
                continue;
            }else {
                System.out.println("密码设置成功！");
                break;
            }
        }

        //输入身份证号码
        while (true) {
            //
            System.out.println("请输入身份证号码！");
            idCard = sc.next();
            boolean flag = checkId(idCard);
            if (flag){
                System.out.println("身份证录入成功！");
                break;
            }else {
                System.out.println("身份证格式错误！");
                continue;
            }
        }

        //输入手机号码
        while (true) {
            System.out.println("请输入手机号码！");
            phoneNumber = sc.next();
            boolean flag = checkPhone(phoneNumber);
            if (flag){
                System.out.println("手机号录入成功！");
                break;
            }else {
                System.out.println("手机号录入失败！请重新录入");
                continue;
            }
        }

        //创建用户对象
        User user = new User(userName,passWord1,idCard,phoneNumber);
        userList.add(user);
        System.out.println("注册成功！");

        printList(userList);
    }

    //登录
    private static void login(ArrayList<User> userList) {
        Scanner sc = new Scanner(System.in);
        for (int i = 0; i < 5; i++) {
            System.out.println("请输入同户名！");
            String userName = sc.next();
            //判断同户名是否存在
            boolean flag = contains(userList,userName);
            if (!flag){
                System.out.println("用户名未注册，请先注册再登录！");
                return;
            }
            System.out.println("请输入密码！");
            String passWorld = sc.next();

            while (true) {
                //生成验证码
                String captcha = getCaptcha();
                System.out.println("验证码为："+captcha);
                System.out.println("请输入验证码！");
                String newCaptcha = sc.next();
                if (newCaptcha.equalsIgnoreCase(captcha)){
                    System.out.println("验证码正确！");
                    break;
                }else {
                    System.out.println("验证码错误，请重新输入！");
                    continue;
                }
            }

            //验证用户名和密码是否正确
            //判断集合中是否有用户名和验证码
            //创建方法判断
            User userInfo = new User(userName,passWorld,null,null);
            boolean loginResult = checkUserInfo(userList,userInfo);
            if (loginResult){
                System.out.println("登录成功！");
                //创建学生管理系统对象,直接通过类本省调用方法
                StudentSystem.startStudentSystem();
                break;
            }else {
                if (i == 4){
                    System.out.println("你以及连续登录失败五次，当前账号"+userName+"已被锁定，请稍后再试！");
                    //账号被锁定后直接结束方法
                    return;
                }else {
                    System.out.println("用户名或密码错误，请重新输入！");
                    System.out.println("你还剩下"+(4-i)+"次机会！");
                }

            }
        }
    }

    //忘记密码
    private static void forgetPassword(ArrayList<User> userList) {
        System.out.println("请输入用户名！");
        Scanner sc = new Scanner(System.in);
        String userName = sc.next();
        boolean flag = contains(userList, userName);
        if (!flag){
            System.out.println("用户名未注册，请先注册！");
            //return为结束方法
            return;
        }
        System.out.println("请输入省份证号码！");
        String userId = sc.next();
        System.out.println("请输入手机号码！");
        String userPhone = sc.next();

        //将用户对象先拿出来再进行比较
        //获取用户名在集合中的索引
        int index = findIndex(userList,userName);
        User user = userList.get(index);
        if (!(user.getIdCard().equalsIgnoreCase(userId) && user.getPhoneNumber().equals(userPhone))){
            System.out.println("省份证号码或手机号码不匹配，无法修改密码！");
            return;
        }

        //验证成功，可以修改密码
        while (true) {
            System.out.println("请输入新的密码！");
            String newPassword = sc.next();
            System.out.println("请再次输入新的密码！");
            String againPassword = sc.next();
            //对密码进行校验
            if (newPassword.equals(againPassword)){
                user.setPassWord(newPassword);
                break;
            }else {
                System.out.println("两次密码不匹配，请重新输入！");
                continue;
            }
        }
        System.out.println("密码修改成功！");
    }




    //以下为各种方法
    //忘记密码时将用户名的索引从集合中提取出来
    private static int findIndex(ArrayList<User> userList, String userName) {
        for (int i = 0; i < userList.size(); i++) {
            User user = userList.get(i);
            if (user.getUserName().equals(userName));
            return i;
        }
        //-1未错误
        return -1;
    }

    //判断集合中是否有用户名和验证码
    private static boolean checkUserInfo(ArrayList<User> userList,User userInfo) {
        //遍历集合，判断
        for (int i = 0; i < userList.size(); i++) {
            //输入的用户名和密码
            String userName = userInfo.getUserName();
            String passWord = userInfo.getPassWord();
            User user = userList.get(i);
            if (user.getUserName().equals(userName) && user.getPassWord().equals(passWord)){
                return true;
            }
        }
        return false;
    }

    //生成验证码
    private static String getCaptcha(){
        //创建集合记录所有字母
        ArrayList<Character> list = new ArrayList<>();
        //用于拼接字符
        StringBuilder sb = new StringBuilder();
        Random r = new Random();
        for (int i = 0; i < 26; i++) {
            list.add((char)('a'+i));
            list.add((char)('A'+i));
        }
        //随机生成数字
        int number = r.nextInt(10);
        for (int i = 0; i < 4; i++) {
            int index = r.nextInt(52);
            Character c = list.get(index);
            sb.append(c);
        }
        sb.append(number);
        char[] chars = sb.toString().toCharArray();
        int index = r.nextInt(chars.length);
        char temp = chars[chars.length-1];
        chars[chars.length-1] = chars[index];
        chars[index] = temp;
        StringBuilder newSB = new StringBuilder();
        newSB.append(chars);
        return newSB.toString();
    }

    //遍历集合
    private static void printList(ArrayList<User> userList) {
        System.out.println("用户名"+"\t"+"密码"+"\t"+"身份证号码"+"\t"+"电话号码");
        for (int i = 0; i < userList.size(); i++) {
            User index = userList.get(i);
            System.out.println(index.getUserName()+"\t"+index.getPassWord()+"\t"
                    +index.getIdCard()+"\t"+index.getPhoneNumber());
        }
    }

    //验证手机号码
    private static boolean checkPhone(String phoneNumber) {
        int length = phoneNumber.length();
        if (length != 11){
            return false;
        }
        if (phoneNumber.charAt(0) == 0){
            return false;
        }
        for (int i = 0; i < length; i++) {
            char c = phoneNumber.charAt(i);
            if ( !(c>= '0' && c <= '9')){
                return false;
            }
        }
        return true;
    }

    //验证身份证号码
    private static boolean checkId(String idCard) {
        int length = idCard.length();
        if (length != 18){
            return false;
        }
        if (idCard.charAt(0) == '0'){
            return false;
        }
        for (int i = 0; i < length-2; i++) {
            char c = idCard.charAt(i);
            if (c > '9'){
                System.out.println(c);
                return false;
            }
        }
        char h = idCard.charAt(length - 1);

        if (h =='x' || h =='X' || (h >= '0' && h <= '9') ){
            return true;
        }else{
            return false;
        }
    }

    //判断用户名格式是否符合要求
    private static boolean checkUserName(String userName) {
        int length = userName.length();
        if (length < 3 || length > 15){
            return false;
        }
        for (int i = 0; i < userName.length(); i++) {
            char c = userName.charAt(i);
            if (!((c >= 'a' && c <= 'z')||(c >= 'A' && c <= 'Z')||(c >= '0' && c <= '9'))){
                return false;
            }
        }
        //不能是纯数字
        int count = 0;
        for (int i = 0; i < userName.length(); i++) {
            char c = userName.charAt(i);
            if ((c >= 'a' && c <= 'z')||(c >= 'A' && c <= 'Z')){
                count++;
                break;
            }
        }
        return count > 0;
    }

    //判断用户名是否唯一
    private static boolean contains(ArrayList<User> userList,String userName){
        for (int i = 0; i < userList.size(); i++) {
            User user = userList.get(i);
            String username = user.getUserName();
            if (username.equals(userName)){
                return true;
            }
        }
        //循环完毕后还没有找到相同用户名则放回false
        return false;
    }
}
