import java.math.BigInteger;
import java.util.HashMap;

public class Interpreter {
    HashMap<String,String> storage = new HashMap<>();

    //определяем ноду
    public String run(TopTreeNode node) {
        //Если вывод то определяем содержимое внутренней ноды
        if (node.getClass() == UnarOperationTreeNode.class) {
            if (((UnarOperationTreeNode) node).operator.type.name.equals("PRINT")) {
                System.out.println(this.run(((UnarOperationTreeNode) node).operandNode));
            }
        }
        if (node.getClass() == BinOperationTreeNode.class) {
            //сохраняем переменную и ее значение в мапе
            if (((BinOperationTreeNode) node).operator.type.name.equals("ASSIGN")) {
                String res = this.run(((BinOperationTreeNode) node).rightNode); // формула, которая присваивается переменной
                VarTreeNode varNode = (VarTreeNode) (((BinOperationTreeNode) node).leftNode);
                this.storage.put(varNode.var.text, res);
                return res;
            }
            else {
                //обычные операции между двумя нодами
                int left = Integer.parseInt(this.run(((BinOperationTreeNode) node).leftNode));
                int right = Integer.parseInt(this.run(((BinOperationTreeNode) node).rightNode));
                switch (((BinOperationTreeNode) node).operator.type.name){
                    case "PLUS":
                        return String.valueOf(left+right);
                    case "MINUS":
                        return String.valueOf(left-right);
                    case "MULTIPLY":
                        return String.valueOf(left*right);
                    case "DIVIDE":
                        return String.valueOf(left/right);
                    case "POW":
                        return String.valueOf(pow(left,right));
                    case "ASSIGN":
                }
            }
        }
        // Возвращаем переменную из мапы, так как она уже должна быть инициализирована
        if (node.getClass() == VarTreeNode.class) {
            return storage.get(((VarTreeNode) node).var.text);
        }
        // Если это нода типа числа, то просто определяем чему оно равно
        if (node.getClass() == NumberTreeNode.class) {
            return ((NumberTreeNode) node).number.text;
        }
        // В цикле while определяем условие и затем выполняем все внутренние операции(ноды) по этому условию
        if (node.getClass() == WhileTreeNode.class) {
            int left = Integer.parseInt(this.run(((WhileTreeNode) node).leftNode));
            int right = Integer.parseInt(this.run(((WhileTreeNode) node).rightNode));
            switch (((WhileTreeNode) node).operator.type.name) {
                case "LESS":
                    while (left < right) {
                        for (int i = 0; i < ((WhileTreeNode) node).operations.size(); i++)
                            this.run(((WhileTreeNode) node).operations.get(i));
                        left = Integer.parseInt(this.run(((WhileTreeNode) node).leftNode));
                        right = Integer.parseInt(this.run(((WhileTreeNode) node).rightNode));
                    }
                    break;
                case "MORE":
                    while (left > right) {
                        for (int i = 0; i < ((WhileTreeNode) node).operations.size(); i++)
                            this.run(((WhileTreeNode) node).operations.get(i));
                        left = Integer.parseInt(this.run(((WhileTreeNode) node).leftNode));
                        right = Integer.parseInt(this.run(((WhileTreeNode) node).rightNode));
                    }
                    break;
                case "EQUALS":
                    while (left == right) {
                        for (int i = 0; i < ((WhileTreeNode) node).operations.size(); i++)
                            this.run(((WhileTreeNode) node).operations.get(i));
                        left = Integer.parseInt(this.run(((WhileTreeNode) node).leftNode));
                        right = Integer.parseInt(this.run(((WhileTreeNode) node).rightNode));
                    }
                    break;
            }
        }
        //Условный оператор - определяем тип условия, затем по этому условию выполняем внутренние операции
        if (node.getClass() == IfTreeNode.class) {
            int left = Integer.parseInt(this.run(((IfTreeNode) node).leftNode));
            int right = Integer.parseInt(this.run(((IfTreeNode) node).rightNode));
            switch (((IfTreeNode) node).operator.type.name) {
                case "LESS":
                    if (left < right) {
                        for (int i = 0; i < ((IfTreeNode) node).operations.size(); i++)
                            this.run(((IfTreeNode) node).operations.get(i));
                    }
                    break;
                case "MORE":
                    if (left > right) {
                        for (int i = 0; i < ((IfTreeNode) node).operations.size(); i++)
                            this.run(((IfTreeNode) node).operations.get(i));
                    }
                    break;
                case "EQUALS":
                    if (left == right) {
                        for (int i = 0; i < ((IfTreeNode) node).operations.size(); i++)
                            this.run(((IfTreeNode) node).operations.get(i));
                    }
                    break;
            }
        }
        //Цикл с параметром - определяем условие, затем выполняем внутренние ноды и обновляем action
        if (node.getClass()== ForTreeNode.class){
            int left = Integer.parseInt(this.run(((ForTreeNode) node).leftNode));
            int right = Integer.parseInt(this.run(((ForTreeNode) node).rightNode));
            switch (((ForTreeNode) node).operator.type.name) {
                case "LESS":
                    while (left < right) {
                        for (int i = 0; i < ((ForTreeNode) node).operations.size(); i++)
                            this.run(((ForTreeNode) node).operations.get(i));
                        this.run(((ForTreeNode) node).action);
                        left = Integer.parseInt(this.run(((ForTreeNode) node).leftNode));
                        right = Integer.parseInt(this.run(((ForTreeNode) node).rightNode));
                    }
                    break;
                case "MORE":
                    while (left > right) {
                        for (int i = 0; i < ((ForTreeNode) node).operations.size(); i++)
                            this.run(((ForTreeNode) node).operations.get(i));
                        this.run(((ForTreeNode) node).action);
                        left = Integer.parseInt(this.run(((ForTreeNode) node).leftNode));
                        right = Integer.parseInt(this.run(((ForTreeNode) node).rightNode));
                    }
                    break;
                case "EQUALS":
                    while (left == right) {
                        for (int i = 0; i < ((ForTreeNode) node).operations.size(); i++)
                            this.run(((ForTreeNode) node).operations.get(i));
                        this.run(((ForTreeNode) node).action);
                        left = Integer.parseInt(this.run(((ForTreeNode) node).leftNode));
                        right = Integer.parseInt(this.run(((ForTreeNode) node).rightNode));
                    }
                    break;
            }
        }
        return "";
    }

    public static int pow(int value, int powValue) {
        BigInteger a = new BigInteger(String.valueOf(value));
        return a.pow(powValue).intValue();
    }
}
