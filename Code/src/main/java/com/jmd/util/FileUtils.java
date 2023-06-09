package com.jmd.util;

import java.io.*;

public class FileUtils {

    // 读取文件
    public static byte[] readFile(String path) throws IOException {
        var file = new File(path);
        if (!file.exists() || file.isDirectory()) {
            return new byte[0];
        }
        // 传统IO方式
        //1、定义一个Byte字节数组输出流，设置大小为文件大小
        //2、将打开的文件输入流转换为Buffer输入流，循环 读取buffer输入流到buffer[]缓冲，并将buffer缓冲数据输入到目标输出流。
        //3、将目标输出流转换为字节数组。
        var bos = new ByteArrayOutputStream((int) file.length());
        BufferedInputStream bin = null;
        try {
            bin = new BufferedInputStream(new FileInputStream(file));
            byte[] buffer = new byte[1024];
            while (bin.read(buffer) > 0) {
                bos.write(buffer);
            }
            return bos.toByteArray();
        } finally {
            assert bin != null;
            bin.close();
            bos.close();
        }
    }

    // 对象转文件
    public static void saveObj2File(Object obj, String filePath) throws IOException {
        FileOutputStream fos = new FileOutputStream(filePath);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fos);
        objectOutputStream.writeObject(obj);
        objectOutputStream.close();
        fos.close();
    }

    // 文件转对象
    public static Object readFile2Obj(String filePath) throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(filePath);
        ObjectInputStream ois = new ObjectInputStream(fis);
        Object obj = ois.readObject();
        fis.close();
        return obj;
    }

    // 文件转对象
    public static Object readFile2Obj(File file) throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(file);
        ObjectInputStream ois = new ObjectInputStream(fis);
        Object obj = ois.readObject();
        fis.close();
        return obj;
    }

    // 检查并更正文件路径
    public static String checkFilePathAndName(String pathAndName) {
        return pathAndName.replace("\\", "/");
    }

}
