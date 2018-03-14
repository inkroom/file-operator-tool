package cn.inkroom.software.linux.copy;

import java.io.*;
import java.util.regex.Pattern;

public class Main {

    /**
     * 第一个源文件目录，第二个目标文件夹目录，第三个文件名正则
     *
     * @param args
     */
    public static void main(String[] args) {
        //解析命令
        if (args.length > 0) {
            switch (args[0]) {
                case "--help":
                    System.out.println("命令格式为：copy 源文件夹 目标文件夹 正则表达式");
                    System.out.println("命令格式为：del 源文件夹 正则表达式 [-l]");
                    break;
                case "copy":
                    if (args.length >= 4) {
                        copy(args[1], args[2], args[3]);
                    } else {
                        System.out.println("命令格式为：copy 源文件夹 目标文件夹 正则表达式");
                    }
                    break;
                case "del":
                    if (args.length >= 3) {
                        if (args.length == 4 && args[3].equals("-l")) {
                            delete(args[1], args[2], true);
                        } else if (!args[3].equals("-l")) {
                            System.out.println("命令格式为：del 源文件夹 正则表达式 [-l]");
                        }
                    } else {
                        System.out.println("命令格式为：del 源文件夹 正则表达式 [-l]");
                    }
                    break;
                default:
                    System.out.println("错误命令，输入--help查看帮助");
            }
        } else {
            System.out.println("输入--help查看帮助");
        }
    }

    /**
     * 删除文件
     *
     * @param os    源文件夹
     * @param regex 正则
     * @param l     是否输出详细信息
     */
    public static void delete(String os, String regex, boolean l) {
        File org = new File(os);
        if (!org.exists() && org.isFile()) {
            System.out.println("源文件夹不存在");
            return;
        }
        int count = 0;
        File files[] = org.listFiles();
        for (File file : files) {
            if (file.isFile() && file.getName().matches(regex)) {
                boolean result = file.delete();
                if (l) System.out.println("删除" + file.getName() + (result ? "成功" : "失败"));
                if (result)
                    count++;
            }
        }
        System.out.println("删除" + count + "个文件");
    }


    public static void copy(String os, String targetS, String regex) {
        File org = new File(os);
        if (!org.exists() && org.isFile()) {
            System.out.println("源文件夹不存在");
        }

        File target = new File(targetS);

        if (!target.exists())
            target.mkdirs();
        Pattern pattern = Pattern.compile(regex);

        File[] files = org.listFiles();

        int count = 0;

        for (File file : files) {

//            System.out.println(file.getName());
//            System.out.println(pattern.matcher(file.getName()).matches());
            if (pattern.matcher(file.getName()).find()) {
                File targetFile = new File(target.getAbsoluteFile(), file.getName());
                copy(file, targetFile);
                count++;
            }
        }
        System.out.println("复制成功" + count + "个文件");
    }

    public static void copy(File org, File target) {


        try {
            FileInputStream input = new FileInputStream(org);
            FileOutputStream output = new FileOutputStream(target);

            int length;
            byte bytes[] = new byte[10 * 1024];

            while ((length = input.read(bytes)) != -1) {
                output.write(bytes, 0, length);
            }
            input.close();
            output.close();


        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
