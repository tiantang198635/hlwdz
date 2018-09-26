package com.sign.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 文件工具类
 * @author win7
 *
 */
public class FileUtil {
	public static Path createDirectOrFile(String dirct,String fileName) throws IOException {
		Path path = Paths.get(dirct);
        if(Files.notExists(path)) {
        	//判断文件夹是否存在，不存在则创建
        	Files.createDirectories(path);
        }
        
        path = Paths.get(dirct, fileName);
        if(Files.notExists(path)){
        	//判断文件是否存在，不存在则创建
        	Files.createFile(path);
        }
        return path;
	}
}
