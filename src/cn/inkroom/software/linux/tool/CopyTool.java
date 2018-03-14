package cn.inkroom.software.linux.tool;

import java.io.File;
import java.util.List;
import java.util.regex.Pattern;

public class CopyTool extends AbstractTool {

    @Override
    public boolean execute(List<String> values) throws Exception {
        if (values.size() >= 3) {
            File org = new File(values.get(0));
            File target = new File(values.get(1));
            if (org.exists()) {
                if (target.exists()) {
                    //决定复制方式
                    Copy copy = chooseMethod(getOs());
                    File files[] = org.listFiles();
                    if (files != null)
                        for (File file : files) {
                            if (match(file.getName(), values.get(2))) {
                                String orgPath = file.getAbsolutePath();
                                String targetPath = values.get(1) + File.separator + file.getName();
                                try {
                                    copy.copy(orgPath, targetPath);
                                    appendMessage("复制文件\"" + orgPath + "\"到\"" + targetPath + "\"");
                                } catch (Exception e) {
                                    appendMessage(e.getMessage());
                                    if (!isIgnored())
                                        return false;
                                }
                            }
                        }
                    return true;
                } else {
                    appendMessage(target.getAbsolutePath()+" not found");
                    return false;
                }
            } else {
                appendMessage(org.getAbsolutePath()+" not found");
                return false;
            }


        } else {
            appendMessage("参数格式为 源文件夹 目标文件夹 正则表达式");
            return false;
        }
    }

    protected Pattern pattern;

    public boolean match(String file, String regex) {
        if (pattern == null)
            pattern = Pattern.compile(regex);
        return pattern.matcher(file).find();
    }

    protected Copy chooseMethod(String osName) {
        if (osName.contains("window")) {
            return copyForWindow;
        } else if (osName.contains("Linux")) {
            return copyForLinux;
        } else {
            return copy;
        }
    }

    /**
     * windows平台的复制
     *
     * @param org
     * @param target
     */
    private Copy copyForWindow = (org, target) -> Runtime.getRuntime().exec("cmd /c XCOPY " + org + "  " + target);

    /**
     * linux平台复制
     *
     * @param org
     * @param target
     */
    private Copy copyForLinux = (org, target) -> Runtime.getRuntime().exec("cp " + org + "  " + target);

    /**
     * 其余平台复制
     *
     * @param org
     * @param target
     */
    private Copy copy = (org, target) -> {
        File orgFile = new File(org);
        File targetFile = new File(target);
        orgFile.renameTo(targetFile);

    };

    @Override
    public void help() {

    }

    protected interface Copy {
        void copy(String org, String target) throws Exception;
    }
}
