//package com.xinyunfu.product.utils;
//
//import com.jcraft.jsch.*;
//import com.jcraft.jsch.ChannelSftp.LsEntry;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Scope;
//import org.springframework.stereotype.Service;
//
//import java.io.*;
//import java.util.*;
//
///**
// * sftp工具类
// *
// * @author allenssd
// */
//@Service
//@Scope("singleton")
//public class SftpUtil {
//    private static final Logger log = LoggerFactory.getLogger(SftpUtil.class);
//
//    /**
//     * 服务器连接ip
//     */
//    @Value("${sftp.host}")
//    private String host;
//
//    /**
//     * 用户名
//     */
//    @Value("${sftp.username}")
//    private String username;
//
//
//    /**
//     * 密码
//     */
//    @Value("${sftp.password}")
//    private String password;
//
//    /**
//     * 根目录
//     */
//    @Value("${sftp.rootPath}")
//    private String rootPath;
//
//    /**
//     * 端口号
//     */
//    @Value("${sftp.port:22}")
//    private int port;
//    private ChannelSftp sftp = null;
//    private Session sshSession = null;
//
//
//    /**
//     * 通过SFTP连接服务器
//     */
//    public void connect() {
//        try {
//            JSch jsch = new JSch();
//            jsch.getSession(username, host, port);
//            sshSession = jsch.getSession(username, host, port);
//            if (log.isInfoEnabled()) {
//                log.info("Session created.");
//            }
//            sshSession.setPassword(password);
//            Properties sshConfig = new Properties();
//            sshConfig.put("StrictHostKeyChecking", "no");
//            sshSession.setConfig(sshConfig);
//            sshSession.connect();
//            if (log.isInfoEnabled()) {
//                log.info("Session connected.");
//            }
//            Channel channel = sshSession.openChannel("sftp");
//            channel.connect();
//            if (log.isInfoEnabled()) {
//                log.info("Opening Channel.");
//            }
//            sftp = (ChannelSftp) channel;
//            if (log.isInfoEnabled()) {
//                log.info("Connected to " + host + ".");
//            }
//        } catch (Exception e) {
//            log.error("连接SFTP服务器错误:" + e.getMessage(), e);
//        }
//    }
//
//    /**
//     * 关闭连接
//     */
//    public void disconnect() {
//        if (this.sftp != null) {
//            if (this.sftp.isConnected()) {
//                this.sftp.disconnect();
//                if (log.isInfoEnabled()) {
//                    log.info("sftp is closed already");
//                }
//            }
//        }
//        if (this.sshSession != null) {
//            if (this.sshSession.isConnected()) {
//                this.sshSession.disconnect();
//                if (log.isInfoEnabled()) {
//                    log.info("sshSession is closed already");
//                }
//            }
//        }
//    }
//
//    /**
//     * 批量下载文件
//     *
//     * @param remotePath    ：远程下载目录(以路径符号结束,可以为相对路径eg:/assess/sftp/jiesuan_2/2014/)
//     * @param localPath     ：本地保存目录(以路径符号结束,D:\Duansha\sftp\)
//     * @param fileFormat    ：下载文件格式(以特定字符开头,为空不做检验)
//     * @param fileEndFormat ：下载文件格式(文件格式)
//     * @param del           ：下载后是否删除sftp文件
//     * @return
//     */
//    public List<String> batchDownLoadFile(String remotePath, String localPath, String fileFormat, String fileEndFormat, boolean del) {
//        List<String> filenames = new ArrayList<String>();
//        try {
//            // connect();
//            Vector v = listFiles(remotePath);
//            if (v.size() > 0) {
//                System.out.println("本次处理文件个数不为零,开始下载...fileSize=" + v.size());
//                Iterator it = v.iterator();
//                while (it.hasNext()) {
//                    LsEntry entry = (LsEntry) it.next();
//                    String filename = entry.getFilename();
//                    SftpATTRS attrs = entry.getAttrs();
//                    if (!attrs.isDir()) {
//                        boolean flag = false;
//                        String localFileName = localPath + filename;
//                        fileFormat = fileFormat == null ? "" : fileFormat.trim();
//                        fileEndFormat = fileEndFormat == null ? "" : fileEndFormat.trim();
//                        // 三种情况
//                        if (fileFormat.length() > 0 && fileEndFormat.length() > 0) {
//                            if (filename.startsWith(fileFormat) && filename.endsWith(fileEndFormat)) {
//                                flag = downloadFile(remotePath, filename, localPath, filename);
//                                if (flag) {
//                                    filenames.add(localFileName);
//                                    if (flag && del) {
//                                        deleteSFTP(remotePath, filename);
//                                    }
//                                }
//                            }
//                        } else if (fileFormat.length() > 0 && "".equals(fileEndFormat)) {
//                            if (filename.startsWith(fileFormat)) {
//                                flag = downloadFile(remotePath, filename, localPath, filename);
//                                if (flag) {
//                                    filenames.add(localFileName);
//                                    if (flag && del) {
//                                        deleteSFTP(remotePath, filename);
//                                    }
//                                }
//                            }
//                        } else if (fileEndFormat.length() > 0 && "".equals(fileFormat)) {
//                            if (filename.endsWith(fileEndFormat)) {
//                                flag = downloadFile(remotePath, filename, localPath, filename);
//                                if (flag) {
//                                    filenames.add(localFileName);
//                                    if (flag && del) {
//                                        deleteSFTP(remotePath, filename);
//                                    }
//                                }
//                            }
//                        } else {
//                            flag = downloadFile(remotePath, filename, localPath, filename);
//                            if (flag) {
//                                filenames.add(localFileName);
//                                if (flag && del) {
//                                    deleteSFTP(remotePath, filename);
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//            if (log.isInfoEnabled()) {
//                log.info("download file is success:remotePath=" + remotePath + "AND localPath=" + localPath + ",file size is" + v.size());
//            }
//        } catch (SftpException e) {
//            log.error("批量下载文件错误:" + e.getMessage(), e);
//        } finally {
//        }
//        return filenames;
//    }
//
//    /**
//     * 下载单个文件
//     *
//     * @param remotePath     ：远程下载目录(以路径符号结束)
//     * @param remoteFileName ：下载文件名
//     * @param localPath      ：本地保存目录(以路径符号结束)
//     * @param localFileName  ：保存文件名
//     * @return
//     */
//    public boolean downloadFile(String remotePath, String remoteFileName, String localPath, String localFileName) {
//        FileOutputStream fieloutput = null;
//        try {
//            File file = new File(localPath + localFileName);
//            fieloutput = new FileOutputStream(file);
//            sftp.get(rootPath + remotePath + remoteFileName, fieloutput);
//            if (log.isInfoEnabled()) {
//                log.info("===DownloadFile:" + remoteFileName + " success from sftp.");
//            }
//            return true;
//        } catch (FileNotFoundException e) {
//            log.error("下载单个文件:" + e.getMessage(), e);
//        } catch (SftpException e) {
//            log.error("下载单个文件:" + e.getMessage(), e);
//        } finally {
//            if (null != fieloutput) {
//                try {
//                    fieloutput.close();
//                } catch (IOException e) {
//                    log.error("下载单个文件:" + e.getMessage(), e);
//                }
//            }
//        }
//        return false;
//    }
//
//    /**
//     * 下载单个文件
//     *
//     * @param remotePath     ：远程下载目录(以路径符号结束)
//     * @param remoteFileName ：下载文件名
//     * @param fieloutput     ：下载文件流
//     * @return
//     */
//    public boolean downloadFile(String remotePath, String remoteFileName, FileOutputStream fieloutput) {
//        try {
//            sftp.get(rootPath + remotePath + remoteFileName, fieloutput);
//            if (log.isInfoEnabled()) {
//                log.info("===DownloadFile:" + remoteFileName + " success from sftp.");
//            }
//            return true;
//        } catch (SftpException e) {
//            log.error("下载单个文件:" + e.getMessage(), e);
//        } finally {
//            if (null != fieloutput) {
//                try {
//                    fieloutput.close();
//                } catch (IOException e) {
//                    log.error("下载单个文件:" + e.getMessage(), e);
//                }
//            }
//        }
//        return false;
//    }
//
//    /**
//     * 上传单个文件
//     *
//     * @param remotePath     ：远程保存目录
//     * @param remoteFileName ：保存文件名
//     * @param localPath      ：本地上传目录(以路径符号结束)
//     * @param localFileName  ：上传的文件名
//     * @return
//     */
//    public boolean uploadFile(String remotePath, String remoteFileName, String localPath, String localFileName) {
//        FileInputStream in = null;
//        try {
//            createDir(rootPath + remotePath);
//            File file = new File(localPath + localFileName);
//            in = new FileInputStream(file);
//            sftp.put(in, remoteFileName);
//            return true;
//        } catch (FileNotFoundException e) {
//            log.error(e.getMessage(), e);
//        } catch (SftpException e) {
//            log.error(e.getMessage(), e);
//        } finally {
//            if (in != null) {
//                try {
//                    in.close();
//                } catch (IOException e) {
//                    log.error("上传单个文件:" + e.getMessage(), e);
//                }
//            }
//        }
//        return false;
//    }
//
//    /**
//     * 上传单个文件
//     *
//     * @param remotePath     ：远程保存目录
//     * @param remoteFileName ：保存文件名
//     * @param localFile      ：本地上传文件流(以路径符号结束)
//     * @return
//     */
//    public boolean uploadFile(String remotePath, String remoteFileName, InputStream localFile) {
//        try {
//            createDir(rootPath + remotePath);
//            sftp.put(localFile, remoteFileName);
//            return true;
//        } catch (SftpException e) {
//            log.error(e.getMessage(), e);
//        } finally {
//            if (localFile != null) {
//                try {
//                    localFile.close();
//                } catch (IOException e) {
//                    log.error("上传单个文件:" + e.getMessage(), e);
//                }
//            }
//        }
//        return false;
//    }
//
//    /**
//     * 上传单个文件
//     *
//     * @param remotePath     ：远程保存目录
//     * @param remoteFileName ：保存文件名
//     * @param localFile      ：本地文件(以路径符号结束)
//     * @return
//     */
//    public boolean uploadFile(String remotePath, String remoteFileName, File localFile) {
//        FileInputStream in = null;
//        try {
//            in = new FileInputStream(localFile);
//            createDir(rootPath + remotePath);
//            sftp.put(in, remoteFileName);
//            return true;
//        } catch (SftpException e) {
//            log.error(e.getMessage(), e);
//        } catch (IOException e) {
//            log.error(e.getMessage(), e);
//        } finally {
//            if (in != null) {
//                try {
//                    in.close();
//                } catch (IOException e) {
//                    log.error("上传单个文件:" + e.getMessage(), e);
//                } finally {
//                    localFile.delete();
//                }
//            }
//        }
//        return false;
//    }
//
//    /**
//     * 复制文件
//     *
//     * @param srcPath      源文件目录
//     * @param srcFileName  源文件名称
//     * @param copyPath     复制的目录
//     * @param copyFileName 复制文件名称
//     * @return
//     */
//    public boolean copyFile(String srcPath, String srcFileName, String copyPath, String copyFileName) {
//        File tmpFile = null;
//        try {
//            //创建临时文件
//            tmpFile = File.createTempFile(copyFileName, null);
//            System.out.println("临时文件所在的本地路径：" + tmpFile.getCanonicalPath());
//            downloadFile(srcPath, srcFileName, new FileOutputStream(tmpFile));
//            uploadFile(copyPath, copyFileName, new FileInputStream(tmpFile));
//            return true;
//        } catch (IOException e) {
//            log.error(e.getMessage(), e);
//        } finally {
//            if (tmpFile != null) {
//                tmpFile.delete();//程序退出时删除临时文件
//            }
//        }
//        return false;
//    }
//
//    /**
//     * 批量上传文件
//     *
//     * @param remotePath ：远程保存目录
//     * @param localPath  ：本地上传目录(以路径符号结束)
//     * @param del        ：上传后是否删除本地文件
//     * @return
//     */
//    public boolean bacthUploadFile(String remotePath, String localPath, boolean del) {
//        try {
//            connect();
//            File file = new File(localPath);
//            File[] files = file.listFiles();
//            for (int i = 0; i < files.length; i++) {
//                if (files[i].isFile() && files[i].getName().indexOf("bak") == -1) {
//                    if (this.uploadFile(remotePath, files[i].getName(), localPath, files[i].getName()) && del) {
//                        deleteFile(localPath + files[i].getName());
//                    }
//                }
//            }
//            if (log.isInfoEnabled()) {
//                log.info("upload file is success:remotePath=" + remotePath + "AND localPath=" + localPath + ",file size is " + files.length);
//            }
//            return true;
//        } catch (Exception e) {
//            log.error("批量上传文件错误:" + e.getMessage(), e);
//        } finally {
//            this.disconnect();
//        }
//        return false;
//
//    }
//
//    /**
//     * 删除本地文件
//     *
//     * @param filePath
//     * @return
//     */
//    public boolean deleteFile(String filePath) {
//        File file = new File(filePath);
//        if (!file.exists()) {
//            return false;
//        }
//
//        if (!file.isFile()) {
//            return false;
//        }
//        boolean rs = file.delete();
//        if (rs && log.isInfoEnabled()) {
//            log.info("delete file success from local.");
//        }
//        return rs;
//    }
//
//    /**
//     * 创建目录
//     *
//     * @param createpath
//     * @return
//     */
//    public boolean createDir(String createpath) {
//        try {
//            if (isDirExist(createpath)) {
//                this.sftp.cd(createpath);
//                return true;
//            }
//            String[] pathArry = createpath.split("/");
//            StringBuffer filePath = new StringBuffer("/");
//            for (String path : pathArry) {
//                if (path.equals("")) {
//                    continue;
//                }
//                filePath.append(path + "/");
//                if (isDirExist(filePath.toString())) {
//                    sftp.cd(filePath.toString());
//                } else {
//                    // 建立目录
//                    sftp.mkdir(filePath.toString());
//                    // 进入并设置为当前目录
//                    sftp.cd(filePath.toString());
//                }
//
//            }
//            return true;
//        } catch (SftpException e) {
//            log.error("创建目录错误:" + e.getMessage(), e);
//        }
//        return false;
//    }
//
//    /**
//     * 判断目录是否存在
//     *
//     * @param directory
//     * @return
//     */
//    public boolean isDirExist(String directory) {
//        boolean isDirExistFlag = false;
//        try {
//            SftpATTRS sftpATTRS = sftp.lstat(directory);
//            isDirExistFlag = true;
//            return sftpATTRS.isDir();
//        } catch (Exception e) {
//            if (e.getMessage().toLowerCase().equals("no such file")) {
//                isDirExistFlag = false;
//            }
//        }
//        return isDirExistFlag;
//    }
//
//    /**
//     * 删除stfp文件
//     *
//     * @param directory  ：要删除文件所在目录
//     * @param deleteFile ：要删除的文件
//     */
//    public void deleteSFTP(String directory, String deleteFile) {
//        try {
//            sftp.rm(directory + deleteFile);
//            if (log.isInfoEnabled()) {
//                log.info("delete file success from sftp.");
//            }
//        } catch (Exception e) {
//            log.error("删除stfp文件错误:" + e.getMessage(), e);
//        }
//    }
//
//    /**
//     * 如果目录不存在就创建目录
//     *
//     * @param path
//     */
//    public void mkdirs(String path) {
//        File f = new File(path);
//
//        String fs = f.getParent();
//
//        f = new File(fs);
//
//        if (!f.exists()) {
//            f.mkdirs();
//        }
//    }
//
//    /**
//     * 读取文件流
//     *
//     * @param filePath
//     * @return
//     * @throws SftpException
//     */
//    public InputStream getInputStream(String filePath) throws SftpException {
//        return sftp.get(rootPath + filePath);
//    }
//
//    /**
//     * 列出目录下的文件
//     *
//     * @param directory ：要列出的目录
//     * @return
//     * @throws SftpException
//     */
//    public Vector listFiles(String directory) throws SftpException {
//        return sftp.ls(directory);
//    }
//
//    public String getHost() {
//        return host;
//    }
//
//    public void setHost(String host) {
//        this.host = host;
//    }
//
//    public String getUsername() {
//        return username;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public int getPort() {
//        return port;
//    }
//
//    public void setPort(int port) {
//        this.port = port;
//    }
//
//    public String getRootPath() {
//        return rootPath;
//    }
//
//    public void setRootPath(String rootPath) {
//        this.rootPath = rootPath;
//    }
//
//    public ChannelSftp getSftp() {
//        return sftp;
//    }
//
//    public void setSftp(ChannelSftp sftp) {
//        this.sftp = sftp;
//    }
//}
