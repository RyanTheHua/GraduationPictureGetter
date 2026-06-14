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
 BufferedImage combinedImage = new BufferedImage(460, 600, BufferedImage.TYPE_INT_ARGB);
 int tempx =0;
 int tempy =0;
int firstrun = 0;


int gap = 0;
boolean gapSetting = false;
Scanner scan = new Scanner(System.in);






System.out.println("Enter yes (y) for no Squares or no (n) for squares (has some visual glitches) (y/n)");

String pictureTpe = scan.nextLine();

if(pictureTpe.equalsIgnoreCase("y")){

System.out.println("NO SQUARES: (Warning! there are some visual glitches with this type)\nEnter the unique ID for the image from a site like flash photography by opening the zoomed in hd picture in its own tab and look at the url it should look like &O=27341768&R=10001&F=0276&A=71994&rand=0.8262668656003157\nit is right after the value for the y coordinate:");
                String uniqueid = scan.nextLine();

                System.out.println("Gap or no Gap? (y/n)");
                String gapinput = scan.nextLine();
                int runSelection = 0;
                int gapheight =0;
                if(gapinput.equalsIgnoreCase("y")){
                    gapSetting = true;
                    System.out.println("Enter the run selection # for where there should be a gap this is where there is a visual issue (1 - 5):");
                 runSelection = scan.nextInt();
                    System.out.println("Enter the gap height in pixels: (7 is usually good)");
                     gapheight = scan.nextInt();

                     scan.nextLine();
                }
   for(int y = 63; y<= 528;y+= 115){ // 528 +=132 for y
    firstrun++;
    tempx=0;
    

   


    gap = 0;
    
    for( int x =57; x<= 460;x+= 92){ //368 +=184 for x 
      
            try {
                Files.createDirectories(Paths.get("Pictures"));
                
                String url = "http://magnifier.flashphotography.com/MagnifyRender.ashx?X="+x+"&Y="+y+ uniqueid;

        downloadUsingNIO(url, "Pictures/MagnifyRender"+x+"_"+y+".png");
        image = ImageIO.read(new File("Pictures","MagnifyRender"+x+"_"+y+".png"));

        image = image.getSubimage(36,36,119,117);
   
   Graphics g = combinedImage.getGraphics();
   g.drawImage(image, tempx, tempy, null); // need to have overlap -3 pixels from the previous image

  
  // filling in the void because that one pixel box is annoying

  //using first run because the first run is actually fine looking, but then the next ones don't look right
   
if(firstrun==runSelection){
    //introduce gap because the white box so we add gap so that we can patch it up with image that matches what should be at that white box
   
    if(gapSetting){
    gap = gapheight;
    }
    
   
  y+=15;
   url = "http://magnifier.flashphotography.com/MagnifyRender.ashx?X="+x+"&Y="+y+ uniqueid;
   downloadUsingNIO(url, "Pictures/MagnifyRender"+x+"_"+y+".png");
   image = ImageIO.read(new File("Pictures","MagnifyRender"+x+"_"+y+".png"));

        image = image.getSubimage(36,135,119, gapheight);
   
    g = combinedImage.getGraphics();
   g.drawImage(image, tempx, tempy+115, null); // need to have overlap -3 pixels from the previous image
y-=15;
     // g.drawImage(image, x,y, null); // need to have overlap -3 pixels from the previous image
     
     
            }
          
   ImageIO.write(combinedImage, "PNG", new File("Pictures", "combined.png"));

 tempx+=92;
   
   
   
   
    } 
    
    catch (IOException e) {
        System.err.println("Download failed: " + e.getMessage());
    }

    
        }
     
        

          
          tempy+=115 + gap;
       
    }
    

    
    }

    else{
        
System.out.println("SQUARES: Enter the unique ID for the image from a site like flash photography by opening the zoomed in hd picture in its own tab and look at the url\nit should look like &O=27341768&R=10001&F=0276&A=71994&rand=0.8262668656003157 and is right after the value for the y coordinate:");
                String uniqueid = scan.nextLine();

               
                
 
   for(int y = 63; y<= 575;y+= 115){ // 528 +=132 for y
    firstrun++;
    tempx=0;
    

   


    
    for( int x =57; x<= 575;x+= 115){ //368 +=184 for x 
      
            try {
                Files.createDirectories(Paths.get("Pictures"));
                
                String url = "http://magnifier.flashphotography.com/MagnifyRender.ashx?X="+x+"&Y="+y+ uniqueid;

        downloadUsingNIO(url, "Pictures/MagnifyRender"+x+"_"+y+".png");
        image = ImageIO.read(new File("Pictures","MagnifyRender"+x+"_"+y+".png"));

        image = image.getSubimage(35,35,120,150);
   
   Graphics g = combinedImage.getGraphics();
   g.drawImage(image, tempx, tempy-5, null); // need to have overlap -3 pixels from the previous image

  
          
   ImageIO.write(combinedImage, "PNG", new File("Pictures", "combined.png"));

 tempx+=115;
   
   
   
   
    } 
    
    catch (IOException e) {
        System.err.println("Download failed: " + e.getMessage());
    }

    
        }
     
        

          
          tempy+=115;
       
    }
    

    }

    System.out.println("Finished: Check the pictures folder for the picture\nEnter anything to exit");
    String exit = scan.nextLine();
    System.out.println("Exiting...");
    scan.close();
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

