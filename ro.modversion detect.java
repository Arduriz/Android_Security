package me.kobins;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import static java.lang.System.*;

public class Main {

    static final String DETECT = "ro.modversion";
    public static void main(String[] args) {
        out.println("----- 주석 해제 읽기 프로그램 -----");
        out.println("프로그램 실행 위치: "+getProperty("user.dir"));
        Scanner scanner = new Scanner(in);
        String path;
        File file;
        finding : while(true){
            out.print("위치를 입력해주세요 : ");
            path = scanner.next();
            if(path == null){
                err.println("올바르지 않습니다.");
                continue;
            }
            file = new File(path);

            if(!file.exists()){
                err.println("입력한 위치에서 build.prop을 찾을 수 없습니다.");
                continue;
            }
            if(file.isFile() && file.getName().equals("build.prop")){
                break;
            }
            if(file.isDirectory()){
                for(File f : Objects.requireNonNull(file.listFiles())){
                    if(f.getName().equals("build.prop")){
                        file = f;
                        break finding;
                    }
                }
                err.println("입력한 위치에서 build.prop을 찾을 수 없습니다.");
                continue;
            }
            break;
        }
        scanner.close();
        out.println("build.prop 파일 찾음 : "+file.getAbsolutePath());
        try{
            //입력 스트림 생성
            FileReader filereader = new FileReader(file);
            //입력 버퍼 생성
            BufferedReader bufReader = new BufferedReader(filereader);

            out.println("-=-=-=-=- build.prop -=-=-=-=-");
            String line;
            while((line = bufReader.readLine()) != null){
                System.out.println(line);
                line = line.trim();
                if(line.startsWith("#")){
                    line = line.replace("#", "");
                }
                if(line.contains(DETECT)){
                    out.println("!!! -=-=-=- 커스텀 롬 감지");
                }
            }
            out.println("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
            //.readLine()은 끝에 개행문자를 읽지 않는다.
            bufReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
