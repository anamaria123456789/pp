public class pb1 {
    public static void main(String[] args){
        ExpressionTree et = new ExpressionTree();
        String expression = "* 2 / - 4 / 5 6 45 6";
        String[] tokens = expression.split(" ");
        Node root = et.buildTree(tokens);
        System.out.println("Rezultatul expresiei este: " + et.evaluate(root));
    }
    static class Node{
        String value;
        Node left, right;

        Node(String value){
            this.value = value;
            left = right = null;
        }
    }
    public static class ExpressionTree{
        private static int index = 0;
        private static boolean isNumber(String token){
            try{
                Double.parseDouble(token);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }
        public static Node buildTree(String[] tokens){
            if(index >= tokens.length)
                return null;
            String token = tokens[index++];
            Node node = new Node(token);

            if(!isNumber(token)){
                node.left = buildTree(tokens);
                node.right = buildTree(tokens);
            }
            return node;
        }

        public double evaluate(Node root){
            if(root == null) return 0;
            if(root.left == null && root.right == null) return Double.parseDouble(root.value);

            double leftVal = evaluate(root.left);
            double rightVal = evaluate(root.right);

            switch(root.value){
                case "+" : return leftVal + rightVal;
                case "-" : return leftVal - rightVal;
                case "*" : return leftVal * rightVal;
                case "/" : return leftVal / rightVal;
                default : return 0;
            }
        }


    }
}
