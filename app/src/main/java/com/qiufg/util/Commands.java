package com.qiufg.util;

import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Author qiufg
 * Date 2017/3/9
 */

public class Commands {

    private static final String COMMAND_SH = "sh";
    private static final String COMMAND_LINE_END = "\n";
    private static final String COMMAND_EXIT = "exit\n";

    public static String exec(String cmd) throws IOException {
        StringBuilder sb = new StringBuilder();
        if (!TextUtils.isEmpty(cmd)) {
            Process p = Runtime.getRuntime().exec(cmd);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                sb.append(line).append("\n");
            }
            p.destroy();
        }
        return sb.toString();
    }

    public static String exec(String[] cmds) {
        if (cmds == null || cmds.length == 0) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        StringBuilder eb = new StringBuilder();
        Process process = null;
        BufferedReader successReader = null;
        BufferedReader errorReader = null;

        DataOutputStream dos = null;
        try {
            process = Runtime.getRuntime().exec(COMMAND_SH);
            dos = new DataOutputStream(process.getOutputStream());
            for (String command : cmds) {
                if (command == null) {
                    continue;
                }
                dos.write(command.getBytes());
                dos.writeBytes(COMMAND_LINE_END);
                dos.flush();
            }
            dos.writeBytes(COMMAND_EXIT);
            dos.flush();

            int status = process.waitFor();

            eb = new StringBuilder();
            successReader = new BufferedReader(new InputStreamReader(
                    process.getInputStream()));
            errorReader = new BufferedReader(new InputStreamReader(
                    process.getErrorStream()));
            String lineStr;
            while ((lineStr = successReader.readLine()) != null) {
                sb.append(lineStr).append("\n");
            }
            while ((lineStr = errorReader.readLine()) != null) {
                eb.append(lineStr);
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                if (dos != null) {
                    dos.close();
                }
                if (successReader != null) {
                    successReader.close();
                }
                if (errorReader != null) {
                    errorReader.close();
                }
                if (process != null) {
                    process.destroy();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.length() > 0 ? sb.toString() : eb.toString();
    }
}
