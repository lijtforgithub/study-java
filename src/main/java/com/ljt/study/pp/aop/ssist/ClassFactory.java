package com.ljt.study.pp.aop.ssist;

import javassist.*;
import lombok.SneakyThrows;

import javax.swing.filechooser.FileSystemView;

/**
 * @author LiJingTang
 * @date 2022-05-09 15:16
 */
class ClassFactory {

    @SneakyThrows
    public static void main(String[] args) {
        ClassPool pool = ClassPool.getDefault();
        CtClass superClass = pool.getCtClass(UserMapper.class.getName());
        String superName = superClass.getName();
        String className = superName + "Impl";

        CtClass ctClass = pool.makeClass(className);
        ctClass.addInterface(superClass);

        CtField descField = CtField.make("private String desc;", ctClass);
        ctClass.addField(descField);

        CtConstructor noArgsConstructor = new CtConstructor(new CtClass[0], ctClass);
        noArgsConstructor.setBody("{desc = \"无参构造方法\";}");
        ctClass.addConstructor(noArgsConstructor);

        CtConstructor argsConstructor = new CtConstructor(new CtClass[] {pool.getCtClass(String.class.getName())}, ctClass);
        // $0=this / $1,$2,$3... 代表方法参数
        argsConstructor.setBody("{$0.desc = $1;}");
        ctClass.addConstructor(argsConstructor);

        ctClass.addMethod(CtNewMethod.getter("getDesc", descField));

        CtMethod saveUser = new CtMethod(CtClass.voidType, "saveUser", new CtClass[]{pool.getCtClass(User.class.getName())}, ctClass);
        saveUser.setModifiers(Modifier.PUBLIC);
        StringBuilder saveUserBody = new StringBuilder("{")
                .append("System.out.println(\"保存用户： \" +  $1);")
                .append("System.out.println(java.time.LocalDateTime.now());")
                .append("}");
        saveUser.setBody(saveUserBody.toString());
        ctClass.addMethod(saveUser);

        ctClass.writeFile(FileSystemView.getFileSystemView().getHomeDirectory().getAbsolutePath());

        Class<?> clazz = ctClass.toClass();
        Object obj = clazz.newInstance();
        UserMapper userMapper = (UserMapper) obj;
        User user = new User();
        user.setId(100);
        user.setName("动态加载");
        userMapper.saveUser(user);
    }

}
