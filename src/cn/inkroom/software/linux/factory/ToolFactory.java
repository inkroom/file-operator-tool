package cn.inkroom.software.linux.factory;

import cn.inkroom.software.linux.tool.AbstractTool;
import cn.inkroom.software.linux.tool.CopyTool;
import cn.inkroom.software.linux.tool.DeleteTool;

public class ToolFactory {

    public static AbstractTool createTool(String[] args) {
        if (args != null && args.length > 1) {
            AbstractTool tool = null;
            if (args[0].equals("cp")) {
                tool = new CopyTool();
            } else if (args[0].equals("del")) {
                tool = new DeleteTool();
            } else if (args[0].equals("rm")) {

            } else {
                return null;
            }

            //解析-参数
            int i;
            for (i = 1; i < args.length; i++) {
                if (args[i].contains("-") && args[i].length() > 1) {
                    switch (args[i].substring(1)) {
                        case "l":
                            tool.setPrint(true);
                            break;
                        case "":
                            break;
                    }
                } else {
                    break;
                }
            }
            //给剩余参数
            for (; i < args.length; i++) {
                tool.appendValue(args[i]);
            }
            return tool;
        }
        return null;
    }
}
