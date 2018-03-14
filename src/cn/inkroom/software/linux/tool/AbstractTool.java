package cn.inkroom.software.linux.tool;

import java.util.ArrayList;
import java.util.List;

/**
 * 文件操作基类
 */
public abstract class AbstractTool {

    protected StringBuilder message;

    private ArrayList<String> values;
    private boolean isPrint = false;
    private boolean ignored = false;

    public boolean isIgnored() {
        return ignored;
    }

    public void setIgnored(boolean ignored) {
        this.ignored = ignored;
    }

    public final void setPrint(boolean print) {
        isPrint = print;
    }

    public AbstractTool() {
        values = new ArrayList<>();
        message = new StringBuilder();
    }

    public void appendValue(String value){
        values.add(value);
    }

    /**
     * 执行命令
     *
     * @param values 参数，不带-的参数
     * @return 执行成功返回true，参数解析错误返回false
     * @throws Exception 执行失败输出错误信息
     */
    public abstract boolean execute(List<String> values) throws Exception;

    public void execute() {
        try {
            boolean result = execute(values);
            if (!result) {//参数解析错误
                help();
                return;
            }
            //命令执行完后是否输出详细信息
            if (isPrint) {
                print();
            }
        } catch (Exception e) {
            e.printStackTrace();
            appendValue(e.getMessage());
            print();
        }
    }

    /**
     * 输出详细信息
     */
    public void print() {
        System.out.println(message.toString());
    }

    /**
     * 输出帮助信息
     */
    public abstract void help();

    protected void appendMessage(String message) {
        this.message.append(message);
        this.message.append("\n");
    }

    public static String getOs() {
        return System.getProperty("os.name");
    }

    public void clearMessage() {
        message.reverse();
    }

}
