package com.substation.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.URLEncoder;

/**
 * @ClassName: FileUtils
 * @Author: zhengxin
 * @Description: 文件处理工具类
 * @Date: 2021/1/18 15:08
 * @Version: 1.0
 */
public class FileUtils {

    private final static Logger logger= LoggerFactory.getLogger(FileUtils.class);

    public static String FILENAME_PATTERN = "[a-zA-Z0-9_\\-\\|\\.\\u4e00-\\u9fa5]+";

    /**
     * 输出指定文件的byte数组
     *
     * @param filePath 文件路径
     * @param os 输出流
     * @return
     */
    public static void writeBytes(String filePath, OutputStream os) throws IOException
    {
        FileInputStream fis = null;
        try
        {
            File file = new File(filePath);
            if (!file.exists())
            {
                throw new FileNotFoundException(filePath);
            }
            fis = new FileInputStream(file);
            byte[] b = new byte[1024];
            int length;
            while ((length = fis.read(b)) > 0)
            {
                os.write(b, 0, length);
            }
        }
        catch (IOException e)
        {
            throw e;
        }
        finally
        {
            if (os != null)
            {
                try
                {
                    os.close();
                }
                catch (IOException e1)
                {
                    e1.printStackTrace();
                }
            }
            if (fis != null)
            {
                try
                {
                    fis.close();
                }
                catch (IOException e1)
                {
                    e1.printStackTrace();
                }
            }
        }
    }

    /**
     * 删除文件
     *
     * @param filePath 文件
     * @return
     */
    public static boolean deleteFile(String filePath)
    {
        boolean flag = false;
        File file = new File(filePath);
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists())
        {
            file.delete();
            flag = true;
        }
        return flag;
    }

    /**
     * 文件名称验证
     *
     * @param filename 文件名称
     * @return true 正常 false 非法
     */
    public static boolean isValidFilename(String filename)
    {
        return filename.matches(FILENAME_PATTERN);
    }

    /**
     * 下载文件名重新编码
     *
     * @param request 请求对象
     * @param fileName 文件名
     * @return 编码后的文件名
     */
    public static String setFileDownloadHeader(HttpServletRequest request, String fileName)
            throws UnsupportedEncodingException
    {
        final String agent = request.getHeader("USER-AGENT");
        String filename = fileName;
        if (agent.contains("MSIE"))
        {
            // IE浏览器
            filename = URLEncoder.encode(filename, "utf-8");
            filename = filename.replace("+", " ");
        }
        else if (agent.contains("Firefox"))
        {
            // 火狐浏览器
            filename = new String(fileName.getBytes(), "ISO8859-1");
        }
        else if (agent.contains("Chrome"))
        {
            // google浏览器
            filename = URLEncoder.encode(filename, "utf-8");
        }
        else
        {
            // 其它浏览器
            filename = URLEncoder.encode(filename, "utf-8");
        }
        return filename;
    }

    /**
     * @Method createMkdir
     * @Author zhengxin
     * @Description 判断文件夹是否存在，不存在创建文件夹
     * @Param: [path 绝对路径]
     * @Return void
     * @Date 2021/3/31 10:00
     * @Version  1.0
     */
    public static void createMkdir(String path){
        File file =new File(path);
        //不存在创建文件夹
        if(!file.exists() && !file.isDirectory())
            file.mkdirs();
    }

    /**
     * @Method videoFilePath
     * @Author zhengxin
     * @Description 获取视频绝对路径
     * @Param: [downLoadPath 下载文件路径]
     * @Return java.lang.String
     * @Date 2021/4/7 9:29
     * @Version  1.0
     */
    public static String videoFilePath(String downLoadPath){
        //获取系统路径
        String sysPath=System.getProperty("user.dir");
        String disc=sysPath.substring(0,2);
        String folderPath=disc+File.separator+ConstantUtil.HISTORY_VIDEO_FILE_PATH;
        //不存在就创建文件夹
        createMkdir(folderPath);
        //历史视频绝对路径
        String absolutePath=folderPath+File.separator+downLoadPath+".mp4";
        return absolutePath;
    }

    public static String uploadPath(){
        //获取系统路径
        String sysPath=System.getProperty("user.dir");
        String disc=sysPath.substring(0,2);
        String folderPath=disc+File.separator+ConstantUtil.UPLOAD_PATH;
        //不存在就创建文件夹
        createMkdir(folderPath);
        return folderPath;
    }

//    /**
//     * @Method videoPath
//     * @Author zhengxin
//     * @Description 获取视频系统路径
//     * @Return java.lang.String
//     * @Date 2021/4/27 14:49
//     * @Version  1.0
//     */
//    public static String videoPath(){
//        //获取系统路径
//        String sysPath=System.getProperty("user.dir");
//        String disc=sysPath.substring(0,2);
//        String folderPath=disc+File.separator+ConstantUtil.HISTORY_VIDEO_FILE_PATH;
//        return folderPath;
//    }


    /**
     * @Method getCataloguePath
     * @Author zhengxin
     * @Description 获取目录路径
     * @Param: [catalogueName 目录名]
     * @Return java.lang.String
     * @Date 2021/4/30 9:24
     * @Version  1.0
     */
    public static String getCataloguePath(String catalogueName){
        //获取系统路径
        String sysPath=System.getProperty("user.dir");
        String disc=sysPath.substring(0,2);
        String folderPath=disc+File.separator+catalogueName;
        return folderPath;
    }
    /**
     * 删除文件，可以是文件或文件夹
     *
     * @param fileName：要删除的文件名
     * @return 删除成功返回true，否则返回false
     */
    public static boolean delete(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
            logger.info("删除文件失败:" + fileName + "不存在！");
            return false;
        } else {
            if (file.isFile())
                return delFile(fileName);
            else
                return deleteDirectory(fileName);
        }
    }

    /**
     * 删除单个文件
     *
     * @param fileName：要删除的文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public static boolean delFile(String fileName) {
        File file = new File(fileName);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                HCNetUtils.DOWNLOAD_MAP.remove(fileName);
                logger.info("删除单个文件" + fileName + "成功！");
                return true;
            } else {
                logger.info("删除单个文件" + fileName + "失败！");
                return false;
            }
        } else {
            logger.info("删除单个文件失败：" + fileName + "不存在！");
            return false;
        }
    }

    /**
     * 删除目录及目录下的文件
     *
     * @param dir：要删除的目录的文件路径
     * @return 目录删除成功返回true，否则返回false
     */
    public static boolean deleteDirectory(String dir) {
        // 如果dir不以文件分隔符结尾，自动添加文件分隔符
        if (!dir.endsWith(File.separator))
            dir = dir + File.separator;
        File dirFile = new File(dir);
        // 如果dir对应的文件不存在，或者不是一个目录，则退出
        if ((!dirFile.exists()) || (!dirFile.isDirectory())) {
            logger.info("删除目录失败：" + dir + "不存在！");
            return false;
        }
        boolean flag = true;
        // 删除文件夹中的所有文件包括子目录
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            // 删除子文件
            if (files[i].isFile()) {
                flag = delFile(files[i].getAbsolutePath());
                if (!flag)
                    break;
            }
            // 删除子目录
            else if (files[i].isDirectory()) {
                flag = deleteDirectory(files[i].getAbsolutePath());
                if (!flag)
                    break;
            }
        }
        if (!flag) {
            logger.info("删除目录失败！");
            return false;
        }
        // 删除当前目录
        if (dirFile.delete()) {
            logger.info("删除目录" + dir + "成功！");
            return true;
        } else {
            return false;
        }
    }

}
