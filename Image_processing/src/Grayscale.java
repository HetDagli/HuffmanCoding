import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
public class Grayscale {

   BufferedImage  image;
   BufferedImage  gray_image;
   int width;
   int height;
   int mat[][]; 
   public Grayscale() {
   
      try {
	
	 String input_img_path = "C:\\Users\\Het Dagli\\Desktop\\images.jpg"; //Input image here
	 String gray_img_path = "C:\\Users\\Het Dagli\\Desktop\\images_greyscale.jpg";
         File input = new File(input_img_path);	
         // Reading image
	 image = ImageIO.read(input);
         width = image.getWidth();
         height = image.getHeight();
  
	 
         // RGB to Gray scale conversion
         for(int i=0; i<height; i++){         
            for(int j=0; j<width; j++){            
               Color c = new Color(image.getRGB(j, i));
               int red = (int)(c.getRed() * 0.299);
               int green = (int)(c.getGreen() * 0.587);
               int blue = (int)(c.getBlue() *0.114);
               Color newColor = new Color(red+green+blue,               
               red+green+blue,red+green+blue);               
               image.setRGB(j,i,newColor.getRGB());
            }
         }
         // Writing converted image to file
         File ouptut = new File(gray_img_path);
         ImageIO.write(image, "jpg", ouptut);
	 
 
	 // Original image
	 JFrame frame = new JFrame();
	  ImageIcon icon = new ImageIcon(input_img_path);
	  JLabel label = new JLabel(icon);
	  frame.add(label);
	  frame.setDefaultCloseOperation
		 (JFrame.EXIT_ON_CLOSE);
	  frame.pack();
	  frame.setVisible(true);
	  
	 // Converted image
	 JFrame frame1 = new JFrame();
	  ImageIcon icon1 = new ImageIcon(gray_img_path);
	  JLabel label1 = new JLabel(icon1);
	  frame1.add(label1);
	  frame1.setDefaultCloseOperation
		 (JFrame.EXIT_ON_CLOSE);
	  frame1.pack();
	  frame1.setVisible(true);
	 
	  // Grayscale image manipulation
	 gray_image = ImageIO.read(new File(gray_img_path));
         width = gray_image.getWidth();
         height = gray_image.getHeight();
	 Raster raster = gray_image.getData();
	 mat = new int[width][height];
	 
	 for(int i=0; i<height; i++){         
            for(int j=0; j<width; j++){            
            	mat[j][i] = raster.getSample(j, i, 0);
            }
         }
	 // Image details
	 System.out.println("Image Height :"+height);
	 System.out.println("Image Width :"+width);
	 //System.out.println("Pixel value at (100,100) :"+mat[100][100]);	// Accessing pixel values
	 // All pixel values
	 /*for(int i=0; i<height; i++){         
            for(int j=0; j<width; j++){            
		        System.out.println("Pixel value at ("+i+","+j+") :"+img_arr[j][i]);	// Accessing pixel values
            }
         }*/
	 
	 
	String path = "C:\\Users\\Het Dagli\\Desktop\\Image_greyscale2.jpg";
         for(int i=0; i<height; i++){         
            for(int j=0; j<width; j++){            
               Color newColor = new Color(mat[j][i],mat[j][i],mat[j][i]);               
               image.setRGB(j,i,newColor.getRGB());
            }
         }
         // Writing converted image to file
         File ouptut2 = new File(path);
         ImageIO.write(image, "jpg", ouptut2);
	
	 
      } catch (Exception e) {}
   }
   
    public static void main(String args[]) throws Exception 
   {
      Grayscale obj = new Grayscale();
      obj.func();
      
      
   }

	static Node node;
	static Node newRoot;
	static String codedString = "";
	public void func() {				//To find the compression ratio
		int w=width;
		int h=height;
		ArrayList<Integer> arl=new ArrayList<Integer>();
		for(int i=0;i<w;i++) {
			for(int j=0;j<h;j++) {
				if(!arl.contains(mat[i][j]))
				       arl.add(mat[i][j]);
				
			}//arl conatains all the unique values i.e 0-255 of pixels
		}
		int[] freq=new int[arl.size()];
		for(int i=0;i<freq.length;i++) {		//INitialize frequency of each element as zero
			freq[i]=0;
		}
		for(int i=0;i<arl.size();i++) {
			int chk=arl.get(i);
			for(int d=0;d<w;d++) {
				for(int v=0;v<h;v++) {
					if(chk==mat[d][v]) {
						freq[i]++;
					}
				}
				
			}
			
		}
		 int n = freq.length;
	        for (int i = 0; i < n-1; i++) {
	            for (int j = 0; j < n-i-1; j++) {
	                if (freq[j] > freq[j+1])
	                {
	                    int temp = freq[j];
	                    freq[j] = freq[j+1];
	                    freq[j+1] = temp;
	                    
	                    
	                }
	                Collections.sort(arl);
	                Collections.reverse(arl);
	        }
	        }//Sorting arl and frequency array
	Node root = null;
	Node current = null;
	Node end = null;

	for (int i = 0; i < freq.length; i++) {// Making a priority queue
		Node node = new Node(arl.get(i), freq[i]);
		if (root == null) {
			root = node;
			end = node;
		} else {
			current = root;
			while (current.linker != null) {
				current = current.linker;
			}
			current.linker = node;
			current.linker.linkerBack = current;
			end = node;
		}
	}

	// Recursively add and make nodes!
	TreeMaker(root, end); // Maing the tree

	int checker=0;

	for (int i = 0; i < w; i++) { // Get the codedstring
		for(int j=0;j<h;j++) {
		current = node;
		checker = mat[i][j];
		String code = "";
			if (current.left.value == checker) {
				code += "0";
				break;
			} else {
				code += "1";
				if (current.right != null) {
					if (current.right.value == arl.get(freq.length - 1)) {
						break;
					}
					current = current.right;
				} else {
					break;
				}
			}
			codedString += code;
		}
		
	
	}
	float ln1=(float)codedString.length();
	float ln2=(float)(w*h*8);
	float answer=(float)(ln2/ln1);
	System.out.println("The size of uncompressed image was "+ln2+" and that of compressed image is "+ln1);
	System.out.println("The compression ratio is "+answer);
	}


public static void TreeMaker(Node root, Node end) {
	
	
    node = new Node(end.linkerBack.value+ end.value, end.linkerBack.count + end.count);
	node.left = end.linkerBack;
	node.right = end;
	end.linkerBack.linkerBack.linker = node;
	node.linkerBack = end.linkerBack.linkerBack;
	end = node;
	end.linker = null;
	Node current = root;

	while (current.linker != null) {
		current = current.linker;
	}

	if (root.linker == end) {
		node = new Node(root.value, root.count);
		node.left = root;
		node.right = end;
		node.linker = null;
		node.linkerBack = null;
		newRoot = node;
	} else {
		TreeMaker(root, end);
	}
	
} 
}
class Node {

int value;
int count;
Node left;
Node right;
Node linker;
Node linkerBack;

Node(int value, int count) {

	this.value = value;
	this.count = count;
	this.left = null;
	this.right = null;
	this.linker = null;
	this.linkerBack = null;

}


	}

echo "# HuffmanCoding" >> README.md
git init
git add README.md
git commit -m "first commit"
git remote add origin https://github.com/HetDagli/HuffmanCoding.git
git push -u origin master


