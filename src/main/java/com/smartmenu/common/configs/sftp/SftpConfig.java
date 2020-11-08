package com.smartmenu.common.configs.sftp;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
public class SftpConfig {

    @Value("${sftp.host}")
    private String sftpHost;

    @Value("${sftp.port}")
    private int sftpPort;

    @Value("${sftp.user}")
    private String sftpUser;

    @Value("${sftp.privateKey:#{null}}")
    private Resource sftpPrivateKey;

    @Value("${sftp.privateKeyPassphrase:#{null}}")
    private String sftpPrivateKeyPassphrase;

    @Value("${sftp.password:#{null}}")
    private String sftpPasword;


    public ChannelSftp getSftpSession() throws JSchException {
        JSch jsch = new JSch();
//        jsch.setKnownHosts("~/.ssh/known_hosts");
        Session jschSession = jsch.getSession(sftpUser, sftpHost, sftpPort);
        jschSession.setConfig("StrictHostKeyChecking", "no");
        jschSession.setPassword(sftpPasword);
        jschSession.connect();
        return (ChannelSftp) jschSession.openChannel("sftp");
    }

    public ChannelSftp getSftpConnection() {
        ChannelSftp channelSftp = null;
        try {
            channelSftp = getSftpSession();
            channelSftp.connect();
        } catch (JSchException e) {
            e.printStackTrace();
        }

        return channelSftp;
    }

}
