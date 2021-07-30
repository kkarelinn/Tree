
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] params) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("numbers.txt").toAbsolutePath());

      //  System.out.println(lines);

        TreeNode root = new TreeNode(' ');
        for (String line : lines) {
            root.insert(line);
        }


        //System.out.println(root.containString("18РђРђ0603"));

      //   writeTreeToFile( root);

        //    TreeNode fromFile = readFromFile("tree.dat");

        List<String> extractedFromTree = new ArrayList<>();
         root.getAllNumbers("", extractedFromTree);
     //   System.out.println(TreeNode);

        for (String t: extractedFromTree) {
            System.out.println(t);
        }
    }

    static class TreeNode {
        char value;
        List<TreeNode> children;

        public TreeNode(char value) {
            this.value = value;
        }

        
        
        
        public void insert(String data) {

            if (data.length() == 0) { return; }
            TreeNode parrent = this;
           
            
            for(int i = 0; i < data.length();i++) {
                      
            	char c = data.charAt(i);
            	
            	TreeNode child = searchChild(c, parrent); //ищем НОД "с" в детях ткущего нода
            	if (parrent.children == null) { parrent.children = new ArrayList<>(); }
            	
            	if (child  == null) {
                    child = new TreeNode(c);
                    parrent.children.add(child);
                  //  parrent = child;
                }
            	 parrent = child;
            }}
      
        
            private TreeNode searchChild (char c, TreeNode parr) {
            	if (parr.children != null) {
            		TreeNode child=null;
            		for(int i = 0; i < parr.children.size(); i++) {
            			if(parr.children.get(i).value==c)
            				child = parr.children.get(i);
            			return child;
            		}

            	}
            	return null;
            }  
//         
//         
        public TreeNode addNewChild(TreeNode parrent, TreeNode child) {
        	if(parrent!=null && child!=null)
        	parrent.children.add(child);
        	return child;
        }

		/*
		 * private TreeNode findNodeByChar(char c) { if (children != null) {
		 * for(TreeNode tn: children) { if (node.value == c) { return tn; } } } return
		 * null; }
		 */
        
        
        //good
       
        
        

//        private boolean containString(String str) {
//            TreeNode current = this;
//            for (int i = 0; i < str.length(); i++) {
//                current = current.searchChild(str.charAt(i),);
//                if (current == null) {
//                    return false;
//                }
//            }
//            return true;
//        }

        public void getAllNumbers(String path, List<String> result) {
            if (value != ' ') {
                path = path + value;
            }
            if (children != null) {
                for(TreeNode node: children) {
                    node.getAllNumbers(path, result);
                }
            } else {
                result.add(path);
            }
        }

        public void Print() {
           System.out.print(value);

            if (children != null) {
                for (TreeNode node: children) {
                   node.Print();  ;
                }
            }
            System.out.print(", ");
        }

        public void readFromFile(FileReader reader) throws IOException {
            char ch;
            while ((ch = (char)reader.read()) != ']') {
                TreeNode treeNode = new TreeNode(ch);
                treeNode.readFromFile(reader);
                if (children == null) {
                    children = new ArrayList<>();
                }
                children.add(treeNode);
            }
        }
    }

    public static void writeTreeToFile(TreeNode root) {
        System.out.print('[');
                        root.Print();
        System.out.print(']');

    }

    public static TreeNode readFromFile(String path) {
        TreeNode root = new TreeNode(' ');
        try {
            FileReader reader = new FileReader(path);
            reader.read();
            root.readFromFile(reader);
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return root;
    }
}