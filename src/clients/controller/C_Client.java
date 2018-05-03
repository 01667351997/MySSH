package clients.controller;

import clients.model.C_ClientSession;
import clients.model.C_ProcessCommand;
import clients.model.C_SetUpNetWorking;
import java.util.Scanner;

/**
 *
 * @author binhminh
 */
public class C_Client {

    public void go() {
        Scanner scanner = new Scanner(System.in);
        String user = "[root]:";
        C_SetUpNetWorking connect = new C_SetUpNetWorking();
        
        
        C_ProcessCommand command = null;
        boolean check = false;
        boolean flag=true;
        while (flag) {
            
            System.out.print(user+"$ ");
            String[] cmd = scanner.nextLine().split(" ");
            
            if(!check){
                switch(cmd[0]){
                    case "ssh" :
                        if(cmd[1].equals("")){
                            System.err.println("Require more argument!");
                            break;
                        }

                        // problem if user input no have @
                        String[] userName;
                        userName = cmd[1].split("@");

                        connect.connectNetworking(userName[1]);
                        System.out.println(cmd[1]+"'s password: ");
                        String password = scanner.nextLine();
                        C_ClientSession client = new C_ClientSession(connect.getSocketOfClient());

                        command = new C_ProcessCommand(client);

                        check = command.login(userName[0], password);
                        if(!check)
                            System.out.println("Login failed");
                        else{
                            System.out.println("Connect success!");
                            user = "["+userName[0]+"]:~";
                        }
                        break;
                    case "exit":
                        System.out.println("Logout SSH !");
                        flag=false;
                        break;
                    default :
                        System.out.println("Unrecognized input!");
                }
            }   
            // After login success
            else{
                switch(cmd[0]){
                    case "pwd":
                        command.pwd();
                        break;
                    case "cd":
                        if(command.cd(cmd[1])){
                            if(cmd[1].equals("..")){
                                String[] dirs = user.split("/");
                                if(dirs[dirs.length-1]==""){
                                    continue;
                                }
                                StringBuilder list = new StringBuilder();
                                for(int i=0;i<dirs.length-1;i++){
                                    if(i==dirs.length-2){
                                       list.append(dirs[i]);
                                    }else{
                                       list.append(dirs[i]+"/");
                                    }
                                }
                                user = list.toString();
                            }else{
                                user += "/"+cmd[1];
                            }
                        }else{
                            System.out.println(cmd[1]+": Not a directory or root");
                        }
                        break;
                    case "ls":
                        command.ls();
                        break;
                    case "rm":
                        if(!command.rm(cmd[1])){
                            System.out.println(cmd[1]+": No such file or directory");
                        }
                        break;
                    case "mv":
                        if (cmd.length<2) {
                            System.out.println("mv: missing destination file operand after "+cmd[1]);
                            break;
                        }
                        command.mv(cmd[1],cmd[2]);
                        break;
                    case "date":
                        command.date();
                        break;
                    case "exit":
                        System.out.println("Logout SSH !");
                        flag=false;
                        break;
                    default :
                        System.out.println("Unrecognized input!");
                }
            }

        }// end while
    }

    public static void main(String[] args) {
        C_Client client = new C_Client();
        client.go();
    }
}
