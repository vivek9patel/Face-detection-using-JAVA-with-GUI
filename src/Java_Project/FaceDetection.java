package Java_Project;

import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.*;

class ImagePath{
	public static String img_url;
	
}

class ObjectDetection {
    public void detectAndDisplay(Mat frame, CascadeClassifier faceCascade) {
        Mat frameGray = new Mat();
        Imgproc.cvtColor(frame, frameGray, Imgproc.COLOR_BGR2GRAY);
        Imgproc.equalizeHist(frameGray, frameGray);

        // -- Detect faces
        MatOfRect faces = new MatOfRect();
        faceCascade.detectMultiScale(frameGray, faces);

        List<Rect> listOfFaces = faces.toList();
        for (Rect face : listOfFaces) {
            Point center = new Point(face.x + face.width / 2, face.y + face.height / 2);
            Imgproc.ellipse(frame, center, new Size(face.width / 2, face.height / 2), 0, 0, 360,
                    new Scalar(0, 255, 0),3);

        }

        //-- Show what you got
        //HighGui.imshow("Capture - Face detection", frame );
        Imgcodecs.imwrite("./detected/output.jpg",frame);
        
    }

    public void run() {
        String filenameFaceCascade = "./Face_Model/haarcascade_frontalface_alt.xml";

        CascadeClassifier faceCascade = new CascadeClassifier();
        if (!faceCascade.load(filenameFaceCascade)) {
            System.err.println("--(!)Error loading face cascade: " + filenameFaceCascade);
            System.exit(0);
        }
        
       Mat frame = new Mat();
        frame = Imgcodecs.imread(ImagePath.img_url);
       //Apply the classifier to the frame
            detectAndDisplay(frame, faceCascade);
    }
}

class ObjectDetectionDemo {

    public void execute() {
    	System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        new ObjectDetection().run();
    }
}



class FileChooser extends JPanel{ 

    private static final long serialVersionUID = 1L;
    static JLabel l,l1,l2;
    static JButton button1,button2,button3;
    JFileChooser j;
    
    FileChooser() {
        
        setBorder(new EmptyBorder(10,10,10,10));
        setBorder(new EtchedBorder());
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();        
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.NORTH;
        
        l = new JLabel("No Image Selected");
        l.setForeground(Color.RED);
        l.setFont(new Font("Sherif",Font.BOLD,60));
        
        l1 = new JLabel();
        l1.setFont(new Font(Font.SANS_SERIF,Font.TRUETYPE_FONT,18));
        
        l2 = new JLabel();
        
        // make a panel to add images
        JPanel p = new JPanel();
        p.setBorder(new LineBorder(Color.BLACK));
        
        p.add(l,gbc);
        p.add(l2,gbc);
        
        add(new JLabel("<html><h1><strong><i>Face Detection using Machine Learning</i></h1><hr></html>"),gbc);
        add(new JLabel("<html><h4><strong><u>TEAM MEMBERS</u> : Tirth Patel (18BCP115) "
                    +  "Tirth Raval (18BCP116)<br/>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&ensp;"
                    + "Vivek Patel (18BCP127)"
                    +  " Vivek Chauhan (18BCP128)</h4></html>"),gbc);
        setBorder(new LineBorder(Color.BLACK));
        
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // make a panel to add the buttons and labels 
        JPanel p1 = new JPanel(new GridBagLayout());
        
        //Making buttons
        button1 = new JButton("Open Image"); 
        button2 = new JButton("Detect Faces");
        button3 = new JButton("Close");

        //Styling Buttons
        button2.setBackground(Color.GREEN);
        button2.setOpaque(true);
        button3.setBackground(Color.RED);
        button3.setOpaque(true);
        button3.setForeground(Color.white);
        button2.setForeground(Color.white);


        
        // add buttons and labels to the frame 
        gbc.weighty = 1;
        p1.add(l1,gbc);
        p1.add(button2,gbc); 
        p1.add(button3,gbc); 
        p1.add(button1,gbc); 
        gbc.fill = GridBagConstraints.CENTER;
        add(p,gbc);
        add(p1,gbc);

        //Hiding 2 buttons and Image Url
        button2.setVisible(false);
        l1.setVisible(false);
        l2.setVisible(false);
        button3.setVisible(false);


        button1.addActionListener((ActionEvent e) -> {
            l.setText("");
            l.setForeground(Color.DARK_GRAY);
            l.setFont(new Font(Font.SANS_SERIF,Font.BOLD,60));

            if(!button1.getText().equals("Select New Image"))
                l.setText("WAITING....");

            if (e.getSource() == button1) {

            j = new JFileChooser();
            //JFileChooser j = new JFileChooser("..//",FileSystemView.getFileSystemView()); 
            File workingDirectory = new File(System.getProperty("user.dir"));

            j.setCurrentDirectory(workingDirectory);
            j.setAcceptAllFileFilterUsed(false);
            j.setDialogTitle("Select an Image File");

            FileNameExtensionFilter restrict = new FileNameExtensionFilter("Image Files"
                    , ImageIO
                            .getReaderFileSuffixes());
            j.addChoosableFileFilter(restrict);

            int r = j.showOpenDialog(null);

            if (r == JFileChooser.APPROVE_OPTION) {
                l.setText("");
                
                try{
                    ImagePath.img_url=j.getSelectedFile().getAbsolutePath();
                    File path = new File(ImagePath.img_url);

                    BufferedImage image1 = ImageIO.read(path);
                    Image main_img = image1.getScaledInstance(500, 250,Image.SCALE_SMOOTH);

                    l.setIcon(new ImageIcon(main_img));
                    
                    String imagDetect = "./detected/sample.png";
                    File path1 = new File(imagDetect);

                    l2.setVisible(true);
                    BufferedImage image2;
                    try {
                        image2 = ImageIO.read(path1);
                        Image main_img2 = image2.getScaledInstance(500, 250,Image.SCALE_SMOOTH);
                        l2.setIcon(new ImageIcon(main_img2));
                    } catch (IOException ex) {}

                    
                    button2.setVisible(true);

                    //l1.setText("<html><u>Image Url</u>: "+ImagePath.img_url +"</html>");
                    //l1.setVisible(true);

                    button1.setText("Select New Image");
                    button1.setBackground(Color.BLUE);
                    button1.setForeground(Color.WHITE);
                    button1.setOpaque(true);
                }
                catch(IOException imgl){
                    l.setText("Error Opening Chosen Image");
                    l.setForeground(Color.RED);
                }
            }
            }
        } //If button 1("Open image") clicks these method runs
        );

        button2.addActionListener((ActionEvent e) -> {
            if (e.getSource() == button2) {
                new ObjectDetectionDemo().execute();
            	
                /**********Our main code to detect image Runs here***********/
                button2.setVisible(false);
                
                String imagDetect1 = "./detected/output.jpg";
                File path1 = new File(imagDetect1);
                
                BufferedImage image1;
                try {
                    image1 = ImageIO.read(path1);
                    Image main_img2 = image1.getScaledInstance(500, 250,Image.SCALE_SMOOTH);
                    l2.setIcon(new ImageIcon(main_img2));
                } catch (IOException ex) {}
                
                l.setText("==>");
                l2.setVisible(true);
                button3.setVisible(true);
            }
        } //If button 2("detect") clicks these method runs
        );

        button3.addActionListener((ActionEvent e) -> {
            if (e.getSource() == button3) {
                System.exit(0);
            }
        } //If button 3("Close) clicks these method runs
        );
        
    }
}

@SuppressWarnings("serial")
public class FaceDetection extends JFrame{
    
    FaceDetection(){
        SwingUtilities.invokeLater(()->{    
            setTitle("JAVA PROJECT");
            add(new FileChooser());
            pack();
            setLocationRelativeTo(null);
            setExtendedState(JFrame.MAXIMIZED_BOTH | getExtendedState());
            setVisible(true);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        });
    }
    
    public static void main(String args[]){
            new FaceDetection();
        
    }
}

