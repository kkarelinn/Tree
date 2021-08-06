
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] params) throws IOException{
        List<String> lines = Files.readAllLines(Paths.get("numbers.txt").toAbsolutePath());

      //  System.out.println(lines);

        TreeNode root = new TreeNode(' ');
        for (String line : lines) {
            root.insert(line);
        }
		
		
		  System.out.println("Вставка строк в дерево завершена успешно");
		  
		  System.out.println(root.containString("18АА0597"));
		  
		  writeTreeToFile("tree.dat", root);
		  
		  TreeNode fromFile = readFromFile("tree.dat");
		 
		 
        
        System.out.println("Извлечение строк из дерева");
        List<String> extractedFromTree = new ArrayList<>();
         root.getAllNumbers(extractedFromTree);
        // System.out.println(extractedFromTree);

		
		  for (String t: extractedFromTree) { System.out.println(t); }
		 
    }

    static class TreeNode {
        char value;
        List<TreeNode> children;

        public TreeNode(char value) {
            this.value = value;
        }

        
        //вставка строк в дерево
        public void insert(String line) {

        	if (line.length() == 0) { return; }
        	TreeNode parrent =this;
        	for (char ch : line.toCharArray()) {
        		char c = ch;
        		TreeNode child = searchChild(c, parrent); //ищем НОД "с" в детях текущего нода
        		if (child  == null) {
        			child = new TreeNode(c);
        			if (parrent.children == null) { parrent.children = new ArrayList<>(); }
        			parrent.children.add(child);
        		}
        		parrent = child;

        	}

        }

        /* Вставка строк рекурсией
           public void insert(String data) {
            if (data.length() == 0) {
                return;
            }
            if (children == null) {
                children = new ArrayList<>();
            }
            char c = data.charAt(0);
            TreeNode child = searchChild(c);
            if (child == null) {
                child = new TreeNode(c);
                children.add(child);
            }
            child.insert(data.substring(1));
        }
         
         */
      
       // поиск символа(НОДа) в детях
        private TreeNode searchChild (char c, TreeNode parrent) {
        	if (parrent.children != null) {
        		for (TreeNode child : parrent.children) {
        			if(child.value==c) {
        				return child;}
        		}
        	}
        	return null;
        }  

      /*  поиск "детей" рекурсией
        private TreeNode searchChild(char c) {
            if (children != null) {
                for(TreeNode node: children) {
                    if (node.value == c) {
                        return node;
                    }
                }
            }
            return null;
        }
        */
        
        
		
        private boolean containString(String str) { 
        	TreeNode current = this; 
        	for(char ch : str.toCharArray()) { 
        		current = current.searchChild(ch,current); 
        		if (current == null) { 
        			return false; 
        			} 
        		} 
        	return true; }
		 

        
        public void getAllNumbers(List<String> result) {
        	TreeNode nodeThis = this;
        	TreeNode prevNode=null;
        	String path = "";
        	while(true) {
        		
        		if (nodeThis.children== null && nodeThis.value==' ') return;
        		
        		if (nodeThis.children!= null) {
        			if (nodeThis.value!=' ') {path=path+nodeThis.value;}
        			prevNode = nodeThis;
        			if(nodeThis.children.size()!=0) {nodeThis = nodeThis.children.get(0);}
        			}
        		
        		
        		else {
        			if (nodeThis.value!=' ') {path=path+nodeThis.value;}
        			if(path.length()>7) {
        			result.add(path);
        			//System.out.println(path);
        			}
        			prevNode.children.remove(0);
        			if(prevNode.children.size()==0) prevNode.children=null;
        			path = "";
          		   	nodeThis=this;
          		   	prevNode = null;
        			}
        	   }
        		   
        }
       
      /*  //рекурсия ГетАллНамберс
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
        */
        
          			
        public void writeToFile(PrintWriter writer) {
            writer.write(value);
            if (children != null) {
                for (TreeNode node: children) {
                    node.writeToFile(writer);
                }
            }
            writer.write(']');
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

    public static void writeTreeToFile(String path, TreeNode root) {
        try {
            PrintWriter out = new PrintWriter(path);
            root.writeToFile(out);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
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
