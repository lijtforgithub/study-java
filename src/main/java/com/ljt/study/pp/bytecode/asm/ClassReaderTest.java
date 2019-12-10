package com.ljt.study.pp.bytecode.asm;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;

import java.io.IOException;

import static org.objectweb.asm.Opcodes.ASM4;

/**
 * @author LiJingTang
 * @date 2019-12-10 16:06
 */
public class ClassReaderTest extends ClassVisitor {

    public static void main(String[] args) throws IOException {
        ClassReaderTest cp = new ClassReaderTest();
        new ClassReader("java.lang.Runnable").accept(cp, 0);
        new ClassReader(ClassReaderTest.class.getClassLoader()
                .getResourceAsStream("com/ljt/study/pp/bytecode/asm/ClassReaderTest$T.class")).accept(cp, 0);
    }

    public ClassReaderTest() {
        super(ASM4);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        System.out.println(name + " extends " + superName + "{");
    }

    @Override
    public FieldVisitor visitField(int access, String name, String descriptor, String signature, Object value) {
        System.out.println("    " + name);
        return null;
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        System.out.println("    " + name + "()");
        return null;
    }

    @Override
    public void visitEnd() {
        System.out.println("}");
    }

    private static class T {
        int i = 0;

        public void m() {
            int j = 1;
        }
    }

}
