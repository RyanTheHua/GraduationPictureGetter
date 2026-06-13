import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import javax.imageio.ImageIO;

public class GraduationPictureGetter {
    public static void main(String[] args) {
        // Code to get graduation pictures
        System.out.println("Getting graduation pictures...");
        // This is a placeholder for the actual implementation
  BufferedImage image;
 BufferedImage combinedImage = new BufferedImage(800, 800, BufferedImage.TYPE_INT_ARGB);
 int tempx =0;
 int tempy =0;
int firstrun = 0;
int gap = 0;
Scanner scan = new Scanner(System.in);








System.out.println("Enter the unique ID for the image from a site like flash photography by opening the zoomed in hd picture in its own tab and look at the url it should look like &O=27341768&R=10001&F=0276&A=71994&rand=0.8262668656003157 and is right after the value for the y coordinate:");
                String uniqueid = scan.nextLine();
 scan.close();
   for(int y = 63; y<= 528;y+= 115){ // 528 +=132 for y
    tempx=0;
    if(firstrun>=1){
    gap = 7;
    }
    for( int x =57; x<= 368;x+= 92){ //368 +=184 for x 
      
            try {
                Files.createDirectories(Paths.get("./Pictures"));
                
                String url = "http://magnifier.flashphotography.com/MagnifyRender.ashx?X="+x+"&Y="+y+ uniqueid;

        downloadUsingNIO(url, "./Pictures/MagnifyRender"+x+"_"+y+".png");
        image = ImageIO.read(new File("./Pictures","MagnifyRender"+x+"_"+y+".png"));

        image = image.getSubimage(36,36,119,117);
   
   Graphics g = combinedImage.getGraphics();
   g.drawImage(image, tempx, tempy, null); // need to have overlap -3 pixels from the previous image

  
  // filling in the void because that one pixel box is annoying

  //using first run because the first run is actually fine looking, but then the next ones don't look right
if(firstrun>=2){
    //introduce gap because the white box so we add gap so that we can patch it up with image that matches what should be at that white box
    gap = 7;
  y+=10;
   downloadUsingNIO(url, "./Pictures/MagnifyRender"+x+"_"+y+".png");
   image = ImageIO.read(new File("./Pictures","MagnifyRender"+x+"_"+y+".png"));

        image = image.getSubimage(36,27,119, 6);
   
    g = combinedImage.getGraphics();
   g.drawImage(image, tempx, tempy-6, null); // need to have overlap -3 pixels from the previous image
y-=10;
     // g.drawImage(image, x,y, null); // need to have overlap -3 pixels from the previous image
            }
            
   ImageIO.write(combinedImage, "PNG", new File("./Pictures", "combined.png"));

 tempx+=94;
   
   
   
   
    } 
    
    catch (IOException e) {
        System.err.println("Download failed: " + e.getMessage());
    }

    
        }
        firstrun++;
          tempy+=115 + gap;
       
    }
    

    
    }


     private static void downloadUsingNIO(String urlStr, String file) throws IOException {
        // Use URI to avoid the deprecated URL(String) constructor
        URL url = URI.create(urlStr).toURL();
        try (ReadableByteChannel rbc = Channels.newChannel(url.openStream());
             FileOutputStream fos = new FileOutputStream(file)) {
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        }
        

        
    }



}

