import java.util.ArrayList;
import java.util.Scanner;

public class StudentSystem {
    //定义一个集合用来存储信息
    static ArrayList<Student> stuList = new ArrayList<>();

    //使用static静态代码块对数据进行初始化
    //静态代码块只在类加载时执行一次
    static {
        stuList.add(new Student("001","lisi",19,"beijing"));
    }


    public static void startStudentSystem() {
        //定义常量来表示用户选择
        //ctrl+shift+u 转换字符串大小写
        //常量名全为大写并在字母间用下划线隔开
        final String ADD_STUDENT = "1";
        final String DELETE_STUDENT = "2";
        final String UPDATE_STUDENT = "3";
        final String ENQUIRE_STUDENT = "4";
        final String EXIT = "5";


        while (true) {
            System.out.println("---------欢迎来到学生管理系统---------");
            System.out.println("1:添加学生");
            System.out.println("2:删除学生");
            System.out.println("3:修改学生");
            System.out.println("4:查询学生");
            System.out.println("5:退出");
            System.out.println("请输入你的选项");
            Scanner sc = new Scanner(System.in);
            String num = sc.next();
            switch (num) {
                case ADD_STUDENT: {
                    System.out.println("1:添加学生");
                    addStudent(stuList);
                    break;
                }
                case DELETE_STUDENT: {
                    System.out.println("2:删除学生");
                   deleteStudent(stuList);
                    break;
                }
                case UPDATE_STUDENT: {
                    System.out.println("3:修改学生");
                    updateStudent(stuList);
                    break;
                }
                case ENQUIRE_STUDENT: {
                    System.out.println("4:查询学生");
                    enquireStudent(stuList);
                    break;
                }
                case EXIT: {
                    //停止虚拟机运行
                    System.exit(0);
                }
                default: {
                    System.out.println("输入错误，没有此选项");
                    break;
                }
            }
        }


    }

    //添加学生
    public static void addStudent(ArrayList<Student> stuList){
        Student stu = new Student();

        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("请输入要添加的学生id");
            String id = sc.next();
            //判断id唯一性
            if (idContains(stuList, id)) {
                System.out.println("id已存在，请重新添加");
            } else{
                stu.setId(id);
                break;
            }
        }

        System.out.println("请输入学生姓名");
        String name = sc.next();
        stu.setName(name);
        System.out.println("请输入学生年龄");
        int age = sc.nextInt();
        stu.setAge(age);
        System.out.println("请输入学生地址");
        String address = sc.next();
        stu.setAddress(address);

        stuList.add(stu);
        System.out.println("学生信息添加成功！");
    }

    //删除学生
    public static void deleteStudent(ArrayList<Student> stuList){
        System.out.println("请输入要删除的学生id");
        Scanner sc = new Scanner(System.in);
        String id = sc.next();
        int index = getIndex(stuList,id);
        if (index >= 0){
            stuList.remove(index);
            System.out.println("id为"+id+"的学生信息删除成功！");
        }else
        System.out.println("未查询到相同id的学生，请添加后再操作！");
    }

    //修改学生信息
    public static void updateStudent(ArrayList<Student> stuList){
        System.out.println("请输入要修改的学生id");
        Scanner sc = new Scanner(System.in);
        String id = sc.next();
        for (int i = 0; i < stuList.size(); i++) {
            Student stu = stuList.get(i);
            if (id.equals(stu.getId())){
                System.out.println("请输入要修改的学生信息");
                System.out.println("请输入学生姓名");
                String name = sc.next();
                stu.setName(name);
                System.out.println("请输入学生年龄");
                int age = sc.nextInt();
                stu.setAge(age);
                System.out.println("请输入学生家庭住址");
                String address = sc.next();
                stu.setAddress(address);
            }else {
                System.out.println("id不存在！");
                break;
            }
        }
        System.out.println("学生信息修改成功！");
    }

    //查询学生信息
    public static void enquireStudent(ArrayList<Student> stuList){
        if (stuList.size() == 0){
            System.out.println("无学生信息，请添加后再查询！");
        }
        System.out.println("id"+"\t\t"+"姓名"+"\t"+"年龄"+"\t"+"家庭住址");
        for (int i = 0; i < stuList.size(); i++) {
            Student stu = stuList.get(i);
            System.out.println(stu.getId()+"\t\t"+stu.getName()+"\t"
            +stu.getAge()+"\t\t"+stu.getAddress());
        }
    }

    //判断id唯一
    public static boolean idContains(ArrayList<Student> stuList,String id){
        int index = getIndex(stuList, id);
        if (index>=0){
            return true;
        }else
            return false;
    }

    //判断id的索引
    public static int getIndex(ArrayList<Student> stuList,String id){
        for (int i = 0; i < stuList.size(); i++) {
            Student stu = stuList.get(i);
            if (stu.getId().equals(id)){
                return i;
            }
        }
        return -1;
    }
}
