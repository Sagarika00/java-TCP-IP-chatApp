package client;

import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.text.DecimalFormat;


public class AttachmentThread implements Runnable{
    
    private Socket socket;
    private DataOutputStream dos;
    private File filename;
    private String recipient, myusername;
    private AttachmentForm form;
    private DecimalFormat df = new DecimalFormat("##,#00");
    
    public AttachmentThread(Socket socket, File filename, String recipient, String myusername, AttachmentForm form){
        this.socket = socket;
        this.filename = filename;
        this.recipient = recipient;
        this.myusername = myusername;
        this.form = form;
    }

    @Override
    public void run() {
        try {
            System.out.println("Sending File..!");
            dos = new DataOutputStream(socket.getOutputStream());
            /** Write filename, recipient, username  **/
            //  Format: CMD_SENDFILE [Filename] [Recipient] [Consignee]
            String clean_filename = filename.getName();
            dos.writeUTF("CMD_SENDFILE "+ clean_filename.replace(" ", "_") +" "+ recipient +" "+ myusername);
            /** Create an stream **/
            OutputStream output = socket.getOutputStream();
            /** Read file ***/
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(filename));
            /** Create a temporary file storage **/
            byte[] buffer = new byte[1024];
            int count, percent = 1;
            while((count = bis.read(buffer)) > 0){
                percent = percent + 1;
                form.setMyTitle(df.format(percent) +"% Sending File...");
                output.write(buffer, 0, count);
            }
            /* Update AttachmentForm GUI */
            form.disableMyGUI(false);
            /* Close Streams */
            output.flush();
            output.close();
            System.out.println("File was sent..!");
        } catch (IOException e) {
            System.out.println("[SendFile]: "+ e.getMessage());
        }
    }
}
